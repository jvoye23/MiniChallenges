package com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupBg
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupBody
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupOnPrimary
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupPrimary
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupSubTitle
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupSuccess
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupSurface
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupSurfaceHigher
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupTextPrimary
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupTextSecondary
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupTextTertiary
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme.cloudPhotoBackupTitle

@Composable
fun CloudPhotoBackupScreenRoot(
    viewModel: CloudPhotoBackupViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val workInfos by viewModel.workInfoFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    LaunchedEffect(workInfos) {
        viewModel.observeWorkInfo(workInfos)
    }

    CloudPhotoBackupStateScreen(
        onBackupButtonClick = {
            if (state.uploadStatus == UploadStatus.Finished) {
                viewModel.resetBackup()
            } else {
                viewModel.startBackup()
            }
        },
        state = state
    )
}

@Composable
private fun CloudPhotoBackupStateScreen(
    onBackupButtonClick: () -> Unit,
    state: CloudPhotoBackupState
) {
    val animatedProgress by animateFloatAsState(
        targetValue = state.uploadProgress,
        animationSpec = tween(durationMillis = 100, easing = LinearEasing),
        label = "progress_animation"
    )

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.cloudPhotoBackupBg)
            .fillMaxSize()
            .padding(top = 120.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cloud Photo Backup",
            style = MaterialTheme.typography.cloudPhotoBackupTitle,
            color = MaterialTheme.colorScheme.cloudPhotoBackupTextPrimary
        )
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    color = MaterialTheme.colorScheme.cloudPhotoBackupSurface,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(state.uploadStatus) {
                UploadStatus.NotStarted -> BackupNotStartedContent()
                UploadStatus.Started, UploadStatus.Paused -> BackupInProgressContent(
                    uploadStatus = state.uploadStatus ,
                    totalItems = state.totalItems,
                    uploadedItems = state.uploadedItems,
                    currentProgress = animatedProgress
                )
                UploadStatus.Finished -> BackupFinishedContent(
                    numberOfItems = state.totalItems
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            onClick = onBackupButtonClick,
            enabled = state.uploadStatus == UploadStatus.NotStarted || state.uploadStatus == UploadStatus.Finished,
            colors = ButtonDefaults.buttonColors(
                containerColor = when(state.uploadStatus) {
                    UploadStatus.Finished -> MaterialTheme.colorScheme.cloudPhotoBackupSuccess
                    else -> MaterialTheme.colorScheme.cloudPhotoBackupPrimary
                },
                disabledContainerColor = MaterialTheme.colorScheme.cloudPhotoBackupSurfaceHigher,
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                if(state.uploadStatus.name == "Finished") {
                    Icon(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(16.dp),
                        imageVector = Icons.Default.Check,
                        tint = MaterialTheme.colorScheme.cloudPhotoBackupOnPrimary,
                        contentDescription = "Completed"
                    )
                }
                Text(
                    text = when(state.uploadStatus) {
                        UploadStatus.NotStarted -> "Start Backup"
                        UploadStatus.Started, UploadStatus.Paused -> "Backup in progress"
                        UploadStatus.Finished -> "Backup completed"
                    },
                    style = MaterialTheme.typography.cloudPhotoBackupBody,
                    color = when(state.uploadStatus) {
                        UploadStatus.NotStarted, UploadStatus.Finished -> MaterialTheme.colorScheme.cloudPhotoBackupOnPrimary
                        UploadStatus.Started, UploadStatus.Paused -> MaterialTheme.colorScheme.cloudPhotoBackupTextTertiary
                    }
                )
            }
        }
    }
}

@Composable
private fun BackupNotStartedContent() {
    Text(
        modifier = Modifier
            .padding(vertical = 30.dp),
        text = "Ready to backup 200 photos",
        style = MaterialTheme.typography.cloudPhotoBackupSubTitle,
        color = MaterialTheme.colorScheme.cloudPhotoBackupTextPrimary
    )
}

@Composable
private fun BackupInProgressContent(
    uploadStatus: UploadStatus,
    currentProgress: Float,
    totalItems: Int,
    uploadedItems: Int
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (uploadStatus.name == "Started") "Backing up photos..." else "Backup paused",
            style = MaterialTheme.typography.cloudPhotoBackupBody,
            color = MaterialTheme.colorScheme.cloudPhotoBackupTextSecondary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = if (uploadStatus.name == "Started") "$uploadedItems / $totalItems photos uploaded" else "Waiting on Internet connection",
            style = MaterialTheme.typography.cloudPhotoBackupSubTitle,
            color = MaterialTheme.colorScheme.cloudPhotoBackupTextPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(12.dp)),
            progress = { currentProgress },
            strokeCap = StrokeCap.Butt,
            gapSize = 0.dp,
            drawStopIndicator = {},
            trackColor = MaterialTheme.colorScheme.cloudPhotoBackupSurfaceHigher,
            color = MaterialTheme.colorScheme.cloudPhotoBackupPrimary
        )
    }
}

@Composable
private fun BackupFinishedContent(
    numberOfItems: Int
) {
    Text(
        modifier = Modifier,
        text = "Backup completed",
        style = MaterialTheme.typography.cloudPhotoBackupSubTitle,
        color = MaterialTheme.colorScheme.cloudPhotoBackupTextPrimary
    )
    Spacer(modifier = Modifier.height(6.dp))
    Text(
        modifier = Modifier,
        text = "$numberOfItems photos successfully uploaded",
        style = MaterialTheme.typography.cloudPhotoBackupBody,
        color = MaterialTheme.colorScheme.cloudPhotoBackupTextPrimary
    )
}

@Preview
@Composable
private fun CloudPhotoBackupScreenPreview() {
    CloudPhotoBackupStateScreen(
        onBackupButtonClick = {},
        state = CloudPhotoBackupState(
            uploadStatus = UploadStatus.NotStarted,
            uploadProgress = 0.5f
        )
    )
}