package com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asFlow
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class CloudPhotoBackupViewModel(application: Application) : AndroidViewModel(application) {

    private val workManager = WorkManager.getInstance(application)
    private val prefs = application.getSharedPreferences(
        PhotoBackupWorker.PREFS_NAME, Context.MODE_PRIVATE
    )

    private val _state = MutableStateFlow(CloudPhotoBackupState())
    val state = _state.asStateFlow()

    val workInfoFlow: Flow<List<WorkInfo>> = workManager
        .getWorkInfosForUniqueWorkLiveData(PhotoBackupWorker.WORK_NAME)
        .asFlow()

    fun observeWorkInfo(workInfos: List<WorkInfo>) {
        val workInfo = workInfos.firstOrNull()
        if (workInfo == null) {
            _state.update { CloudPhotoBackupState() }
            return
        }

        when (workInfo.state) {
            WorkInfo.State.ENQUEUED -> {
                val uploaded = prefs.getInt(PhotoBackupWorker.KEY_UPLOADED_COUNT, 0)
                if (uploaded > 0) {
                    _state.update {
                        it.copy(
                            uploadStatus = UploadStatus.Paused,
                            uploadedItems = uploaded,
                            uploadProgress = uploaded.toFloat() / it.totalItems,
                            buttonState = CloudPhotoBackupButtonState.Disabled,
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            uploadStatus = UploadStatus.Paused,
                            buttonState = CloudPhotoBackupButtonState.Disabled,
                        )
                    }
                }
            }

            WorkInfo.State.RUNNING -> {
                val progress = workInfo.progress
                val uploaded = progress.getInt(PhotoBackupWorker.KEY_UPLOADED_COUNT, 0)
                val total = progress.getInt(
                    PhotoBackupWorker.KEY_TOTAL_PHOTOS, PhotoBackupWorker.TOTAL_PHOTOS
                )
                _state.update {
                    it.copy(
                        uploadStatus = UploadStatus.Started,
                        uploadedItems = uploaded,
                        uploadProgress = if (total > 0) uploaded.toFloat() / total else 0f,
                        buttonState = CloudPhotoBackupButtonState.Disabled,
                    )
                }
            }

            WorkInfo.State.SUCCEEDED -> {
                val output = workInfo.outputData
                val total = output.getInt(
                    PhotoBackupWorker.KEY_TOTAL_PHOTOS, PhotoBackupWorker.TOTAL_PHOTOS
                )
                _state.update {
                    it.copy(
                        uploadStatus = UploadStatus.Finished,
                        uploadedItems = total,
                        uploadProgress = 1f,
                        buttonState = CloudPhotoBackupButtonState.Completed,
                    )
                }
            }

            else -> {}
        }
    }

    fun startBackup() {
        if (_state.value.uploadStatus == UploadStatus.Started) return

        prefs.edit().putInt(PhotoBackupWorker.KEY_UPLOADED_COUNT, 0).apply()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<PhotoBackupWorker>()
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            PhotoBackupWorker.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            workRequest,
        )

        _state.update {
            it.copy(
                uploadStatus = UploadStatus.Started,
                buttonState = CloudPhotoBackupButtonState.Disabled,
                uploadProgress = 0f,
                uploadedItems = 0,
            )
        }
    }

    fun resetBackup() {
        workManager.cancelUniqueWork(PhotoBackupWorker.WORK_NAME)
        prefs.edit().putInt(PhotoBackupWorker.KEY_UPLOADED_COUNT, 0).apply()
        _state.update { CloudPhotoBackupState() }
    }
}
