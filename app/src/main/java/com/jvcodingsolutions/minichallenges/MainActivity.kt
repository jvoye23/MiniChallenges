package com.jvcodingsolutions.minichallenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jvcodingsolutions.minichallenges.navigation.NavigationRoot
import com.jvcodingsolutions.minichallenges.ui.theme.MiniChallengesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniChallengesTheme {
                NavigationRoot()
            }
        }
    }
}
