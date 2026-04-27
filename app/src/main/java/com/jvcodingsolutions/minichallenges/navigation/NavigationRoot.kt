package com.jvcodingsolutions.minichallenges.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.jvcodingsolutions.minichallenges.april_2026.adaptive_design_tokens.presentation.screens.AdaptiveDesignScreen
import com.jvcodingsolutions.minichallenges.april_2026.cloud_photo_backup.CloudPhotoBackupScreenRoot
import com.jvcodingsolutions.minichallenges.april_2026.editing_status.EditingStatusScreenRoot
import com.jvcodingsolutions.minichallenges.april_2026.ready_to_type.ReadyToTypeScreenRoot
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(CloudPhotoBackupRoute)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentRoute = backStack.lastOrNull()

    val drawerItems = remember {
        listOf(
            DrawerItem("Adaptive Design Tokens", Icons.Default.Palette, AdaptiveDesignRoute),
            DrawerItem("Ready To Type", Icons.Default.Keyboard, ReadyToTypeRoute),
            DrawerItem("Editing Status", Icons.Default.Edit, EditingStatusRoute),
            DrawerItem("Cloud Photo Backup", Icons.Default.CloudSync, CloudPhotoBackupRoute),
        )
    }

    val currentTitle = when (currentRoute) {
        is AdaptiveDesignRoute -> "Adaptive Design Tokens"
        is ReadyToTypeRoute -> "Ready To Type"
        is EditingStatusRoute -> "Editing Status"
        is CloudPhotoBackupRoute -> "Cloud Photo Backup"
        else -> "Mini Challenges April 2026"
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Mini Challenges April 2026",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                )
                HorizontalDivider()
                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.label) },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        selected = currentRoute == item.route,
                        onClick = {
                            backStack.clear()
                            backStack.add(item.route)
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentTitle) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Open drawer")
                        }
                    },
                )
            },
        ) { innerPadding ->
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                modifier = Modifier.padding(innerPadding),
                entryProvider = entryProvider<NavKey> {
                    entry<AdaptiveDesignRoute> {
                        AdaptiveDesignScreen()
                    }
                    entry<ReadyToTypeRoute> {
                        ReadyToTypeScreenRoot()
                    }
                    entry<EditingStatusRoute> {
                        EditingStatusScreenRoot()
                    }
                    entry<CloudPhotoBackupRoute> {
                        CloudPhotoBackupScreenRoot()
                    }
                },

            )
        }
    }
}
