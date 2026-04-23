package com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme.AzeretMono
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme.HostGrotesk

val ColorScheme.readyToTypeBg: Color
    get() = ReadyToTypeBg

val ColorScheme.readyToTypeTextPrimary: Color
    get() = ReadyToTypeTextPrimary

val ColorScheme.readyToTypeTextSecondary: Color
    get() = ReadyToTypeTextSecondary

val ColorScheme.readyToTypeTextDisabled: Color
    get() = ReadyToTypeTextDisabled

val ColorScheme.readyToTypeTextSuccess: Color
    get() = ReadyToTypeTextSuccess

val ColorScheme.readyToTypeTextError: Color
    get() = ReadyToTypeTextError

val ColorScheme.readyToTypeInputInactive: Color
    get() = ReadyToTypeInputInactive

val ColorScheme.readyToTypeInputActive: Color
    get() = ReadyToTypeInputActive

val androidx.compose.material3.Typography.readyToTypeTitle: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 38.sp
    )


val androidx.compose.material3.Typography.readyToTypeBody: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = HostGrotesk,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp
    )

val Typography.readyToTypeInput: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = AzeretMono,
        fontWeight = FontWeight.Light,
        fontSize = 32.sp,
        lineHeight = 32.sp
    )




@Composable
fun ReadyToTypeTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
