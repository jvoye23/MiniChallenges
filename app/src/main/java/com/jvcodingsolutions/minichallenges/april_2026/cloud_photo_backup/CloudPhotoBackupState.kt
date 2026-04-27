package com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup

data class CloudPhotoBackupState(
    val uploadProgress: Float = 0f,
    val buttonState: CloudPhotoBackupButtonState = CloudPhotoBackupButtonState.Default,
    val uploadStatus: UploadStatus = UploadStatus.NotStarted,
    val totalItems: Int = 200,
    val uploadedItems: Int = 0
)

enum class CloudPhotoBackupButtonState {
    Default, Disabled, Completed
}

enum class UploadStatus {
    NotStarted, Started, Paused, Finished
}
