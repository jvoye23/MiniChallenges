package com.jvcodingsolutions.minichallenges.april_2026.guided_feature_tour.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme.HostGrotesk

val Typography.guidedFeatureTourTitle: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 32.sp
    )

val Typography.guidedFeatureTourSubTitle: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 28.sp
    )

val Typography.guidedFeatureTourButton: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 24.sp
    )

val Typography.guidedFeatureTourBody: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp
    )