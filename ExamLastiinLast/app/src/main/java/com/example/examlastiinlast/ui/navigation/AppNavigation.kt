package com.example.examlastiinlast.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examlastiinlast.ui.screens.AddEditWordScreen
import com.example.examlastiinlast.ui.screens.MainScreen
import com.example.examlastiinlast.ui.screens.SettingsScreen

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object AddEditWord : Screen("add_edit_word?wordId={wordId}") {
        fun createRoute(wordId: Int? = null) = "add_edit_word${wordId?.let { "?wordId=$it" } ?: ""}"
    }
    object Settings : Screen("settings")
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToAddWord = { navController.navigate(Screen.AddEditWord.createRoute()) },
                onNavigateToEditWord = { wordId -> 
                    navController.navigate(Screen.AddEditWord.createRoute(wordId))
                },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(
            route = Screen.AddEditWord.route
        ) { backStackEntry ->
            val wordId = backStackEntry.arguments?.getString("wordId")?.toIntOrNull()
            AddEditWordScreen(
                wordId = wordId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
} 