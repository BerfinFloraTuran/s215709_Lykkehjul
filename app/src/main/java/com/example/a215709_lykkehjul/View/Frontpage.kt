package com.example.a215709_lykkehjul.View

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a215709_lykkehjul.Model.States
import com.example.a215709_lykkehjul.ViewModel.FrontpageViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun FrontPage(navController: NavController, viewModel: FrontpageViewModel){
    val state = viewModel.uiState.collectAsState()
    viewModel.updateWordDrawn("Hej med dig")
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar() },
        content = { FrontPageContent(navController = navController, viewModel, state.value) }
    )
}

@Composable
fun FrontPageContent(navController: NavController, viewModel: FrontpageViewModel, states: States) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(10.dp))

        var character by remember { mutableStateOf("") }
        Text(text = "Lykkehjul", fontSize = 40.sp, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Category: Phrases",fontSize = 25.sp)

        Spacer(modifier = Modifier.height(20.dp))

        val pointText = viewModel.uiState.value.tempBalance
        Text(text = "$pointText")

        TextButton(onClick = {
            viewModel.spinTheWheel()
        }) {
            Text(text = "Drej hjulet")

        }

        Spacer(modifier = Modifier.height(50.dp))

        val displayText = viewModel.uiState.value.wordSoFar
        Text(text = displayText.toString())

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = character,
            label = { Text(text = "Bogstav") },
            modifier = Modifier
                .width(90.dp),
            onValueChange = { character = it }
        )

        Spacer(modifier = Modifier.width(50.dp))

        TextButton(onClick = {
            viewModel.updateWordSoFar(character)
        }) {
            Text(text = "Gæt")

        }

        Spacer(modifier = Modifier.height(30.dp))

        val livesText = viewModel.uiState.value.amountOfLives
        Text(text = "$livesText liv tilbage")

        val balanceText = viewModel.uiState.value.balance
        Text(text = "Balance: $balanceText")


    }
}
