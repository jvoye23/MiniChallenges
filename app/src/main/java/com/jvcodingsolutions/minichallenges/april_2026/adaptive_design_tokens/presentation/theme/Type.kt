package com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.jvcodingsolutions.minichallenges.R

val HostGrotesk = FontFamily(
    // Regular weights
    Font(R.font.host_grotesk_light, FontWeight.Light),
    Font(R.font.host_grotesk_regular, FontWeight.Normal),
    Font(R.font.host_grotesk_medium, FontWeight.Medium),
    Font(R.font.host_grotesk_semi_bold, FontWeight.SemiBold),
    Font(R.font.host_grotesk_bold, FontWeight.Bold),
    Font(R.font.host_grotesk_extra_bold, FontWeight.ExtraBold),

    // Italic weights
    Font(R.font.host_grotesk_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.host_grotesk_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.host_grotesk_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.host_grotesk_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.host_grotesk_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.host_grotesk_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic)
)

val AzeretMono = FontFamily(
    Font(R.font.azeret_mono_light, FontWeight.Light)
)