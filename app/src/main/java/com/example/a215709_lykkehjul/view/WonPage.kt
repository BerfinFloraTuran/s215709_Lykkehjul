package com.example.a215709_lykkehjul.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.a215709_lykkehjul.R
import com.example.a215709_lykkehjul.model.States
import com.example.a215709_lykkehjul.viewModel.GameViewModel
import kotlinx.coroutines.launch
import java.lang.Math.abs
import kotlin.math.roundToInt


@Composable
fun WonPage(viewModel: GameViewModel, navController: NavHostController){
    val backgroundColor = "#fff6f7"
    val state = viewModel.state.value

    Scaffold(
        backgroundColor = Color(backgroundColor.toColorInt()),
        topBar = { TopBar(navController, true, viewModel, state) },
        content = {  paddingValues -> WonPageContent(viewModel, state, modifier = Modifier.padding(paddingValues), navController) }
    )
}



@Composable
fun WonPageContent(viewModel: GameViewModel, state: States, modifier: Modifier, navController: NavController) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = "YOU WON!")

        }
    }
}


