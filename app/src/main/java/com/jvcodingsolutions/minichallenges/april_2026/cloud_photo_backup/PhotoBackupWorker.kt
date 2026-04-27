package com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class PhotoBackupWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val totalPhotos = inputData.getInt(KEY_TOTAL_PHOTOS, TOTAL_PHOTOS)
        val prefs = applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var uploaded = prefs.getInt(KEY_UPLOADED_COUNT, 0)

        while (uploaded < totalPhotos) {
            delay(DELAY_PER_PHOTO_MS)
            uploaded++
            prefs.edit().putInt(KEY_UPLOADED_COUNT, uploaded).apply()

            setProgress(
                Data.Builder()
                    .putInt(KEY_UPLOADED_COUNT, uploaded)
                    .putInt(KEY_TOTAL_PHOTOS, totalPhotos)
                    .build()
            )
        }

        return Result.success(
            Data.Builder()
                .putInt(KEY_UPLOADED_COUNT, uploaded)
                .putInt(KEY_TOTAL_PHOTOS, totalPhotos)
                .build()
        )
    }

    companion object {
        const val WORK_NAME = "photo_backup_work"
        const val PREFS_NAME = "photo_backup_prefs"
        const val KEY_UPLOADED_COUNT = "uploaded_count"
        const val KEY_TOTAL_PHOTOS = "total_photos"
        const val TOTAL_PHOTOS = 200
        const val DELAY_PER_PHOTO_MS = 100L
    }
}
