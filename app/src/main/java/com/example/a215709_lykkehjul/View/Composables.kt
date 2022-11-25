package com.example.a215709_lykkehjul.View

import android.text.Layout.Alignment
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a215709_lykkehjul.ViewModel.FrontpageViewModel

@Composable
fun NavController(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.FrontPageScreen.route) {
        composable(route = Screen.FrontPageScreen.route) {
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