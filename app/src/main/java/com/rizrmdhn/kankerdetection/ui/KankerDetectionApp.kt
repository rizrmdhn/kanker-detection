package com.rizrmdhn.kankerdetection.ui

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rizrmdhn.kankerdetection.components.TopBar
import com.rizrmdhn.kankerdetection.ui.navigation.Screen
import com.rizrmdhn.kankerdetection.ui.screen.article.ArticleScreen
import com.rizrmdhn.kankerdetection.ui.screen.history.HistoryScreen
import com.rizrmdhn.kankerdetection.ui.screen.home.HomeScreen
import com.rizrmdhn.kankerdetection.ui.screen.result.ResultScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun KankerDetectionApp(
    navController: NavHostController = rememberNavController(),
    viewModel: KankerDetectionAppViewModel = koinViewModel(),
    context: Context = LocalContext.current
) {
    val uri by viewModel.uri.collectAsState()
    val analyzingResult by viewModel.analyzingResult.collectAsState()
    val classifications by viewModel.classifications.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        topBar = {
            TopBar(
                isInHome = currentRoute == Screen.Home.route,
                navigateToHistory = {
                    navController.navigate(Screen.History.route)
                },
                navigateToArticle = {
                    navController.navigate(Screen.Article.route)
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = Screen.Home.route
            ) {
                HomeScreen(
                    context = context,
                    uri = uri,
                    analyzingResult = analyzingResult,
                    setImageUri = viewModel::setUri,
                    analyzeImage = {
                        viewModel.analyzeImage(context) {
                            navController.navigate(Screen.Result.route)
                        }
                    }
                )
            }

            composable(
                Screen.Article.route,
            ) {
                ArticleScreen()
            }

            composable(
                Screen.Result.route,
            ) {
                ResultScreen(
                    uri = uri,
                    analyzingResult = classifications
                )
            }

            composable(
                Screen.History.route,
            ) {
                HistoryScreen()
            }
        }
    }
}