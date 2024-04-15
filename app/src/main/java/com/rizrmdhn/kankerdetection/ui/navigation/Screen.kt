package com.rizrmdhn.kankerdetection.ui.navigation

import org.tensorflow.lite.task.vision.classifier.Classifications

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Result : Screen("result")
    data object Article : Screen("article")
    data object History : Screen("history")
}