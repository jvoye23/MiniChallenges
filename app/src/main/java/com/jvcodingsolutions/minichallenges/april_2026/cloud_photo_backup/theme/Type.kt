package com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme.HostGrotesk

val Typography.cloudPhotoBackupTitle: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 34.sp
    )

val Typography.cloudPhotoBackupSubTitle: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 22.sp
    )

val Typography.cloudPhotoBackupBody: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 18.sp
    )