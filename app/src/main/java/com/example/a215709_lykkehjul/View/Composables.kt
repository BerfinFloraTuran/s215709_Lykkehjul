package com.example.a215709_lykkehjul.View

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
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

@Composable
fun TopBar(){
    TopAppBar() {
        
    }
}

@Composable
fun BottomBar(){
    BottomAppBar(

    ) {
        BottomNavigation {

        }
    }
}