package com.example.a215709_lykkehjul.view

import android.app.Activity
import android.graphics.Color.alpha
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.a215709_lykkehjul.R
import com.example.a215709_lykkehjul.model.States
import com.example.a215709_lykkehjul.viewModel.FrontpageViewModel

@Composable
fun StartPage( viewModel: FrontpageViewModel, navController : NavController){
    val backgroundColor = "#fff8f6"
    val state = viewModel.state.value
    viewModel.randomCategory()


    Scaffold(
        backgroundColor = Color(backgroundColor.toColorInt()),
        topBar = { TopBar(navController, false, viewModel,state) },
        content = {  paddingValues -> StartPageContent(viewModel, state, modifier = Modifier.padding(paddingValues), navController) }
    )
}

@Composable
fun StartPageContent(viewModel: FrontpageViewModel, state: States, modifier: Modifier, navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            viewModel.resetStates()
            viewModel.randomCategory()
            viewModel.resetGuessedLetters()
            navController.navigate(Screen.GamePage.route)
        }) {
            Text(text = "Start Game!")
        }
    }
}
