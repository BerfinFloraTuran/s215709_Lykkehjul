package com.example.a215709_lykkehjul.View

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.a215709_lykkehjul.Model.States
import com.example.a215709_lykkehjul.ViewModel.FrontpageViewModel

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
//TODO(Tjek for hvad der bliver inputtet. SKAL være mellem a-z.)
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
                viewModel.checkLost()
                viewModel.checkWin()
                spinEnabled = true
                guessEnabled = false
                character = ""
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

        val activity = (LocalContext.current as? Activity)

        val lostDialogState = remember{mutableStateOf(viewModel.uiState.value.gameLost)}
        val wonDialogState = remember{mutableStateOf(viewModel.uiState.value.gameWon)}
        var textToShow = ""
        var titleText = ""

        if (viewModel.uiState.value.gameLost || viewModel.uiState.value.gameWon) {
           if (viewModel.uiState.value.gameLost){
               textToShow = "You have used all of your lives and lost the game." +
                       " Please restart or exit the game."
               titleText = "You lost."
           }
            if(viewModel.uiState.value.gameWon){
               textToShow = "You guessed the word and won the game!" +
                       " Please restart or exit the game. "
                titleText = "You won!"
           }
            AlertDialog(
                onDismissRequest = {  },
                title = { Text(text = titleText)},
                text = { Text(text = textToShow)},
                confirmButton = {
                    Button(onClick = {
                        viewModel.resetStates()
                        lostDialogState.value = false
                        wonDialogState.value = false
                        character = ""
                    }) {
                        Text(text = "Restart")
                    }
                                },
                dismissButton = {
                    Button(onClick = {
                        activity?.finish()
                        wonDialogState.value = false
                        wonDialogState.value = false
                        character = ""
                    }) {
                        Text(text = "Exit")
                    }
                }
            )
        }
    }
}
