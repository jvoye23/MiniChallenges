package com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CloudPhotoBackupViewModel: ViewModel() {

    private val _state = MutableStateFlow(CloudPhotoBackupState())
    val state = _state.asStateFlow()

    fun startBackup() {
        if (state.value.uploadStatus == UploadStatus.Started) return

        _state.update { it.copy(
            uploadStatus = UploadStatus.Started
        ) }
        viewModelScope.launch {
            var currentProgress = state.value.uploadProgress
            while(currentProgress < 1f) {
                delay(100)
                currentProgress += 0.004f
                val progress = currentProgress.coerceAtMost(1f)
                _state.update { it.copy(
                    uploadProgress = progress,
                    uploadedItems = (progress * it.totalItems).toInt()
                ) }
            }
            _state.update { it.copy(
                uploadStatus = UploadStatus.Finished
            ) }
        }
    }
}