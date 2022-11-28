package com.example.a215709_lykkehjul.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a215709_lykkehjul.data.Controller
import com.example.a215709_lykkehjul.viewModel.GameViewModel

@Composable
fun NavController(){

    val navController = rememberNavController()
    var controller = Controller()
    controller.initializeData()
    val gameViewModel = GameViewModel(controller.categoryData)

    NavHost(navController = navController, startDestination = Screen.StartGame.route) {
        composable(route = Screen.GamePage.route) {
            FrontPage(viewModel = gameViewModel, navController)
        }
        composable(route = Screen.StartGame.route){
            StartPage(viewModel = gameViewModel, navController)
        }
    }
}

val barColor = "#ffeef0"

@Composable
fun TopBar(){
    TopAppBar(backgroundColor = Color(barColor.toColorInt())) {
        Text(text = "Wheel of Fortune",
        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BottomBar(){
    BottomAppBar(
        backgroundColor = Color(barColor.toColorInt())
    ) {
    }
}
