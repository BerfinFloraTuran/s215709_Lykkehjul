package com.example.a215709_lykkehjul.View

import androidx.compose.material.BottomAppBar
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a215709_lykkehjul.ViewModel.FrontpageViewModel

@Composable
fun NavController(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.GamePage.route) {
        composable(route = Screen.GamePage.route) {
            FrontPage(navController = navController, viewModel = FrontpageViewModel())
        }
    }
}

val barColor = "#ffeef0"

@Composable
fun TopBar(){
    TopAppBar(backgroundColor = Color(barColor.toColorInt())) {
        Text(text = "Wheel of Fortune", textAlign = TextAlign.Center)
    }
}

@Composable
fun BottomBar(){
    BottomAppBar(
        backgroundColor = Color(barColor.toColorInt())
    ) {

    }
}
