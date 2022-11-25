package com.example.a215709_lykkehjul.View

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.a215709_lykkehjul.Model.States
import com.example.a215709_lykkehjul.ViewModel.FrontpageViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun FrontPage(navController: NavController, viewModel: FrontpageViewModel){
    val state = viewModel.uiState.collectAsState()
    viewModel.updateWordDrawn("Ali HM")
    val backgroundColor = "#fff6f7"
    Scaffold(
        backgroundColor = Color(backgroundColor.toColorInt()),
        topBar = { TopBar() },
        bottomBar = { BottomBar() },
        content = { FrontPageContent(navController = navController, viewModel, state.value) }
    )
}


//TODO(Sikre, at spillet slutter ved 0 liv.)

//TODO(Sikre, at man kan vælge en category og der herefter bliver trukket et random ord.)



@Composable
fun FrontPageContent(navController: NavController, viewModel: FrontpageViewModel, states: States) {
    var guessEnabled by remember { mutableStateOf(false) }
    var spinEnabled by remember { mutableStateOf(true) }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {

        var character by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Category: Phrases", fontSize = 25.sp)

        Spacer(modifier = Modifier.height(20.dp))

        val pointText = viewModel.uiState.value.tempBalance
        Text(text = "$pointText")

        val buttonColor = "#ffe3e6"
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = {
            viewModel.spinTheWheel()
            guessEnabled = true
            spinEnabled = false
        }, enabled = spinEnabled, colors = ButtonDefaults.buttonColors(backgroundColor = Color(buttonColor.toColorInt()))) {
            Text(text = "Spin the wheel")

        }

        Spacer(modifier = Modifier.height(50.dp))

        var lineWord = ""

        for (i in 0 until viewModel.uiState.value.wordSoFar.size){
          lineWord= lineWord+viewModel.uiState.value.wordSoFar[i]  + " "
        }

            //val displayText = viewModel.uiState.value.wordSoFar
            Text(text = lineWord, fontSize = 25.sp)

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = character,
                label = { Text(text = "Letter") },
                modifier = Modifier
                    .width(90.dp),
                onValueChange = { character = it },
                enabled = guessEnabled
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(onClick = {
                viewModel.updateWordSoFar(character)
                spinEnabled = true
                guessEnabled = false
            }, enabled = guessEnabled, colors = ButtonDefaults.buttonColors(backgroundColor = Color(buttonColor.toColorInt()))) {
                Text(text = "Guess")
            }

        Spacer(modifier = Modifier.height(30.dp))

        val livesText = viewModel.uiState.value.amountOfLives
        Text(text = "$livesText lives left", fontSize = 16.sp)

        val balanceText = viewModel.uiState.value.balance
        Text(text = "Balance: $balanceText", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(70.dp))

        Divider(startIndent = 0.dp, thickness = 1.dp, color = Color.LightGray)
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Guessed letters: ", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(25.dp))

        var guessedList = ""

        for (i in 0 until viewModel.uiState.value.guessedLetters.size){
            guessedList= guessedList+viewModel.uiState.value.guessedLetters[i]  + "  "
        }
        Text(text = guessedList, fontSize = 18.sp)

    }
}
