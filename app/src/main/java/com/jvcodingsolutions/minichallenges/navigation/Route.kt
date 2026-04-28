package com.jvcodingsolutions.minichallenges.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object AdaptiveDesignRoute : NavKey

@Serializable
data object ReadyToTypeRoute : NavKey

@Serializable
data object EditingStatusRoute : NavKey

@Serializable
data object CloudPhotoBackupRoute: NavKey

@Serializable
data object GuidedFeatureTourRoute: NavKey