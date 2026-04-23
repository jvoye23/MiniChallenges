package com.jvcodingsolutions.minichallenges.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey

data class DrawerItem(
    val label: String,
    val icon: ImageVector,
    val route: NavKey,
)
