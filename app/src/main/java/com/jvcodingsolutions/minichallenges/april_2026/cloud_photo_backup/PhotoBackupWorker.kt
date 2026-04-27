package com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PhotoBackupWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override suspend fun doWork(): Result {
        val totalPhotos = inputData.getInt(KEY_TOTAL_PHOTOS, TOTAL_PHOTOS)
        val prefs = applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var uploaded = prefs.getInt(KEY_UPLOADED_COUNT, 0)

        while (uploaded < totalPhotos) {
            if (!isNetworkAvailable()) {
                reportPaused(uploaded, totalPhotos)
                awaitNetwork()
            }

            delay(DELAY_PER_PHOTO_MS)
            uploaded++
            prefs.edit().putInt(KEY_UPLOADED_COUNT, uploaded).apply()

            setProgress(
                Data.Builder()
                    .putInt(KEY_UPLOADED_COUNT, uploaded)
                    .putInt(KEY_TOTAL_PHOTOS, totalPhotos)
                    .putBoolean(KEY_IS_PAUSED, false)
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

    private fun isNetworkAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private suspend fun reportPaused(uploaded: Int, total: Int) {
        setProgress(
            Data.Builder()
                .putInt(KEY_UPLOADED_COUNT, uploaded)
                .putInt(KEY_TOTAL_PHOTOS, total)
                .putBoolean(KEY_IS_PAUSED, true)
                .build()
        )
    }

    private suspend fun awaitNetwork() {
        if (isNetworkAvailable()) return

        suspendCancellableCoroutine { continuation ->
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    connectivityManager.unregisterNetworkCallback(this)
                    if (continuation.isActive) {
                        continuation.resume(Unit)
                    }
                }
            }

            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

            connectivityManager.registerNetworkCallback(request, callback)

            continuation.invokeOnCancellation {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }
    }

    companion object {
        const val WORK_NAME = "photo_backup_work"
        const val PREFS_NAME = "photo_backup_prefs"
        const val KEY_UPLOADED_COUNT = "uploaded_count"
        const val KEY_TOTAL_PHOTOS = "total_photos"
        const val KEY_IS_PAUSED = "is_paused"
        const val TOTAL_PHOTOS = 200
        const val DELAY_PER_PHOTO_MS = 100L
    }
}
