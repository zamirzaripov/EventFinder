package com.example.eventfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.eventfinder.ui.MainScreen
import com.example.eventfinder.ui.navigation.Navigation
import com.example.eventfinder.ui.theme.EventFinderTheme
import com.example.eventfinder.ui.viewmodels.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventFinderTheme {
                val navController = rememberNavController()

                Navigation(navController = navController)
            }
        }
    }
}
