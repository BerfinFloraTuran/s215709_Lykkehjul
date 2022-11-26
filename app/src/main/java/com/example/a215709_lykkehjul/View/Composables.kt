package com.example.a215709_lykkehjul.View

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a215709_lykkehjul.Data.Controller
import com.example.a215709_lykkehjul.ViewModel.FrontpageViewModel

@Composable
fun NavController(){

    val navController = rememberNavController()
    var controller = Controller()
    controller.initializeData()
    val frontpageViewModel = FrontpageViewModel(controller.categoryData)

    NavHost(navController = navController, startDestination = Screen.GamePage.route) {
        composable(route = Screen.GamePage.route) {
            FrontPage(navController = navController, viewModel = frontpageViewModel)
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
