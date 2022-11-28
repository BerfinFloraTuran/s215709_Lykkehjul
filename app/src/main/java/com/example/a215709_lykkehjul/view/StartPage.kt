package com.example.a215709_lykkehjul.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.a215709_lykkehjul.model.States
import com.example.a215709_lykkehjul.viewModel.GameViewModel


@Composable
fun StartPage(viewModel: GameViewModel, navController: NavHostController){
    val backgroundColor = "#fff6f7"
    val state = viewModel.state.value

    Scaffold(
        backgroundColor = Color(backgroundColor.toColorInt()),
        topBar = { TopBar() },
        content = {  paddingValues -> startGame(viewModel, state, modifier = Modifier.padding(paddingValues), navController) }
    )
}

@Composable
fun startGame(viewModel: GameViewModel, state: States, modifier: Modifier, navController: NavController) {
    Button(onClick = {
        navController.navigate(Screen.GamePage.route)
    }) {
        Text(text = "Midlertidig filler knap")
    }
}


