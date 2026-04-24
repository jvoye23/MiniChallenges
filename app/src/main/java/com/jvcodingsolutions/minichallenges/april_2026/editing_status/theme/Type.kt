package com.jvcodingsolutions.minichallenges.april_2026.editing_status.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme.HostGrotesk

val Typography.editingStatusTitle: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 32.sp
    )

val Typography.editingStatusBody: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 24.sp
    )

val Typography.editingStatusSubText: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )