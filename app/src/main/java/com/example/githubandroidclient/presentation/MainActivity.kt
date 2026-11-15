package com.example.githubandroidclient.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubandroidclient.ui.theme.GitHubAndroidClientTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubAndroidClientTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                NavHost(navController = navController, startDestination = "AuthScreen") {
                    composable("AuthScreen") {
                        AuthScreen(
                            viewModel = koinViewModel(),
                            navController = navController,
                        )
                    }
                    composable("RepositoriesListScreen") {
                        RepositoriesListScreen(
                            viewModel = koinViewModel(),
                            navController = navController
                        )
                    }
                    composable(
                        "DetailInfoScreen/{repoId}",
                        arguments = listOf(navArgument("repoId") {
                            type =
                                NavType.StringType
                        })
                    ) {entry ->
                        DetailInfoScreen(viewModel = koinViewModel(), repoId = entry.arguments?.getString("repoId")?: "0")
                    }
                }
            }
        }
    }
}
