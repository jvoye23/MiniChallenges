package com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jvcodingsolutions.minichallenges.R
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme.AdaptiveDesignTokensBg
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme.AdaptiveDesignTokensOnPrimary
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme.AdaptiveDesignTokensPrimary
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme.AdaptiveDesignTokensSurface
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme.AdaptiveDesignTokensSurfaceLower
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme.AdaptiveDesignTokensTextPrimary
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme.AdaptiveDesignTokensTextSecondary
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.theme.HostGrotesk

// ==========================================
// 1. Define UI Modes
// ==========================================
enum class UiMode {
    COMPACT, COMFORTABLE, EXPANDED
}

// ==========================================
// 2. Define Token Data Classes
// ==========================================
data class SpacingTokens(
    val padding: Dp,
    val gapVertical1: Dp,
    val gapVertical2: Dp,
    val gapHorizontal: Dp,
    val avatar: Dp,
    val btn: Dp
)

data class TypographyTokens(
    val title: TextStyle,
    val body: TextStyle,
    val stats: TextStyle,
    val label: TextStyle
)

data class ColorTokens(
    val background: Color,
    val surface: Color,
    val surfaceLower: Color,
    val primary: Color,
    val onPrimary: Color,
    val textPrimary: Color,
    val textSecondary: Color
)

// ==========================================
// 3. Define Token Values for Each Mode
// ==========================================
val CompactSpacing = SpacingTokens(
    padding = 16.dp, gapVertical1 = 16.dp, gapVertical2 = 4.dp,
    gapHorizontal = 12.dp, avatar = 56.dp, btn = 42.dp
)
val CompactTypography = TypographyTokens(
    title = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = AdaptiveDesignTokensTextPrimary, fontFamily = HostGrotesk),
    body = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal, color = AdaptiveDesignTokensTextPrimary, fontFamily = HostGrotesk),
    stats = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AdaptiveDesignTokensTextPrimary, fontFamily = HostGrotesk),
    label = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, color = AdaptiveDesignTokensTextSecondary, fontFamily = HostGrotesk)
)

val DefaultColorTokens = ColorTokens(
    background = AdaptiveDesignTokensBg,
    surface = AdaptiveDesignTokensSurface,
    surfaceLower = AdaptiveDesignTokensSurfaceLower,
    primary = AdaptiveDesignTokensPrimary,
    onPrimary = AdaptiveDesignTokensOnPrimary,
    textPrimary = AdaptiveDesignTokensTextPrimary,
    textSecondary = AdaptiveDesignTokensTextSecondary
)

val ComfortableSpacing = SpacingTokens(
    padding = 20.dp, gapVertical1 = 20.dp, gapVertical2 = 4.dp,
    gapHorizontal = 16.dp, avatar = 64.dp, btn = 44.dp
)
val ComfortableTypography = TypographyTokens(
    title = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold, color = AdaptiveDesignTokensTextPrimary, fontFamily = HostGrotesk),
    body = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Normal, color = AdaptiveDesignTokensTextPrimary, fontFamily = HostGrotesk),
    stats = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = AdaptiveDesignTokensTextPrimary, fontFamily = HostGrotesk),
    label = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, color = AdaptiveDesignTokensTextSecondary, fontFamily = HostGrotesk)
)

val ExpandedSpacing = SpacingTokens(
    padding = 24.dp, gapVertical1 = 20.dp, gapVertical2 = 6.dp,
    gapHorizontal = 20.dp, avatar = 72.dp, btn = 48.dp
)
val ExpandedTypography = TypographyTokens(
    title = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, color = AdaptiveDesignTokensTextPrimary, fontFamily = HostGrotesk),
    body = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal, color = AdaptiveDesignTokensTextPrimary, fontFamily = HostGrotesk),
    stats = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold, color = AdaptiveDesignTokensTextPrimary, fontFamily = HostGrotesk),
    label = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = AdaptiveDesignTokensTextSecondary, fontFamily = HostGrotesk)
)

// ==========================================
// 4. Create CompositionLocals
// ==========================================
// We use staticCompositionLocalOf because the entire UI structure will recompose when these change.
val LocalSpacingTokens = staticCompositionLocalOf<SpacingTokens> {
    error("No SpacingTokens provided")
}
val LocalTypographyTokens = staticCompositionLocalOf<TypographyTokens> {
    error("No TypographyTokens provided")
}
val LocalColorTokens = staticCompositionLocalOf<ColorTokens> {
    error("No ColorTokens provided")
}

// ==========================================
// 5. Theme Wrapper Provider
// ==========================================
@Composable
fun AdaptiveDesignTheme(
    mode: UiMode,
    content: @Composable () -> Unit
) {
    val spacing = when (mode) {
        UiMode.COMPACT -> CompactSpacing
        UiMode.COMFORTABLE -> ComfortableSpacing
        UiMode.EXPANDED -> ExpandedSpacing
    }

    val typography = when (mode) {
        UiMode.COMPACT -> CompactTypography
        UiMode.COMFORTABLE -> ComfortableTypography
        UiMode.EXPANDED -> ExpandedTypography
    }

    val colors = DefaultColorTokens

    CompositionLocalProvider(
        LocalSpacingTokens provides spacing,
        LocalTypographyTokens provides typography,
        LocalColorTokens provides colors,
        content = content
    )
}

// ==========================================
// 6. Main Screen
// ==========================================
@Composable
fun AdaptiveDesignScreen() {
    // Hoist the state for the current UI Mode
    var currentMode by remember { mutableStateOf(UiMode.COMFORTABLE) }

    // Wrap the screen content in our Adaptive Theme Provider
    AdaptiveDesignTheme(mode = currentMode) {
        // We can access spacing here for the top-level padding
        val spacing = LocalSpacingTokens.current
        val colors = LocalColorTokens.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(spacing.padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ModeSelector(
                selectedMode = currentMode,
                onModeSelected = { currentMode = it }
            )

            Spacer(modifier = Modifier.height(spacing.gapVertical1))

            CreatorCard()
        }
    }
}

// ==========================================
// 7. UI Components (Consuming Locals)
// ==========================================

@Composable
fun ModeSelector(
    selectedMode: UiMode,
    onModeSelected: (UiMode) -> Unit
) {
    // The ModeSelector should not change its layout
    AdaptiveDesignTheme(mode = UiMode.COMPACT) {
        // Accessing tokens via CompositionLocal (No Prop Drilling)
        val typography = LocalTypographyTokens.current
        val spacing = LocalSpacingTokens.current
        val colors = LocalColorTokens.current
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(colors.surfaceLower)
                .padding(spacing.gapVertical2),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UiMode.entries.forEach { mode ->
                val isSelected = mode == selectedMode
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(spacing.btn)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) colors.surface else Color.Transparent)
                        .clickable { onModeSelected(mode) }
                        .padding(horizontal = spacing.gapHorizontal),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = mode.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = typography.body.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) colors.textPrimary else colors.textSecondary
                        )
                    )
                }
            }
        }
    }


}

@Composable
fun CreatorCard() {
    // Accessing tokens via CompositionLocal
    val spacing = LocalSpacingTokens.current
    val typography = LocalTypographyTokens.current
    val colors = LocalColorTokens.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(spacing.padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header: Avatar, Name, Role
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User Avatar from resources
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(spacing.avatar)
                        .clip(CircleShape)
                        .background(colors.surfaceLower)
                        .border(2.dp, colors.surface, CircleShape)
                )

                Spacer(modifier = Modifier.width(spacing.gapHorizontal))

                Column {
                    Text(
                        text = "Alex Morgan",
                        style = typography.title
                    )
                    Spacer(modifier = Modifier.height(spacing.gapVertical2))
                    Text(
                        text = "Android Developer",
                        style = typography.label
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing.gapVertical1))
            HorizontalDivider(color = colors.surfaceLower)
            Spacer(modifier = Modifier.height(spacing.gapVertical1))

            // Stats Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(label = "FOLLOWERS", value = "1.2K")

                // Vertical divider
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(32.dp)
                        .background(colors.surfaceLower)
                )

                StatItem(label = "POSTS", value = "120")
            }

            Spacer(modifier = Modifier.height(spacing.gapVertical1))

            // Follow Button
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(spacing.btn),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Follow",
                    style = typography.body.copy(color = colors.onPrimary, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    // Accessing tokens via CompositionLocal
    val spacing = LocalSpacingTokens.current
    val typography = LocalTypographyTokens.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = typography.label.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        )
        Spacer(modifier = Modifier.height(spacing.gapVertical2))
        Text(
            text = value,
            style = typography.stats
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewAdaptiveDesignScreen() {
    AdaptiveDesignScreen()
}