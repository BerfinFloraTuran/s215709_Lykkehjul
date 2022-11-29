package com.example.a215709_lykkehjul.view

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.a215709_lykkehjul.model.States
import com.example.a215709_lykkehjul.viewModel.GameViewModel

@Composable
fun FrontPage(viewModel: GameViewModel, navController : NavController){
    val backgroundColor = "#fff6f7"
    val state = viewModel.state.value

    Scaffold(
        backgroundColor = Color(backgroundColor.toColorInt()),
        topBar = { TopBar(navController, true, viewModel, state) },
        content = {  paddingValues -> FrontPageContent(viewModel, state, modifier = Modifier.padding(paddingValues), navController) }
    )
}

@Composable
fun FrontPageContent(viewModel: GameViewModel, state: States, modifier: Modifier, navController: NavController) {


    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(20.dp))

        val categoryTitle = state.chosenCategory
        Text(text = "Category: $categoryTitle", fontSize = 18.sp, fontStyle = FontStyle.Italic)

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val point = state.tempBalance
            var pointText = "$point"
            if (point == 0) {
                pointText = "BANKRUPT. Try again."
            }
            if (point == -1) {
                pointText = ""
            }


            Text(text = pointText, fontSize = 16.sp)

            val buttonColor = "#ffe3e6"


            Spacer(modifier = Modifier.height(30.dp))

            var lineWord = ""

            for (i in 0 until state.wordSoFar.size) {
                lineWord = lineWord + state.wordSoFar[i] + " "
            }

            Text(text = lineWord, fontSize = 25.sp)

            Spacer(modifier = Modifier.height(20.dp))
            var character by remember { mutableStateOf("") }

            Row() {
                Column(modifier = Modifier.padding(start=69.dp, end = 7.dp)) {
                    OutlinedTextField(
                        value = character,
                        label = { Text(text = "Letter", textAlign = TextAlign.Center) },
                        modifier = Modifier
                            .width(80.dp),
                        onValueChange = {
                            character = it
                        },
                        singleLine = true
                    )
                        Text(text = "Invalid input", color = Color.Red, modifier = Modifier.alpha(state.errorMessageVisibility))
                }

                Button(
                    onClick = {
                        viewModel.updateWordSoFar(character)
                        character = ""
                        viewModel.checkWin()
                        viewModel.checkLost()
                        if (state.gameWon){
                            navController.navigate(Screen.WonPage.route)
                        } else if (state.gameLost) {
                            navController.navigate(Screen.LostPage.route)
                        } else {
                            navController.navigate(Screen.WheelPage.route)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(buttonColor.toColorInt())),
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "", modifier = Modifier.size(25.dp))
                }
            }

            Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.height(300.dp))
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Divider(startIndent = 0.dp, thickness = 1.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(text = "Guessed letters: ")
                    Spacer(modifier = Modifier.height(10.dp))

                    var guessedList = ""

                    for (i in 0 until state.guessedLetters.size) {
                        guessedList = guessedList + state.guessedLetters[i] + "  "
                    }
                    Text(text = guessedList, fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            /*

            val activity = (LocalContext.current as? Activity)

            val lostDialogState = remember { mutableStateOf(state.gameLost) }
            val wonDialogState = remember { mutableStateOf(state.gameWon) }
            var textToShow = ""
            var titleText = ""

            if (state.gameLost || state.gameWon) {
                if (state.gameLost) {
                    textToShow = "You have used all of your lives and lost the game." +
                            " Please restart or exit the game."
                    titleText = "You lost."
                }
                if (state.gameWon) {
                    textToShow = "You guessed the word and won the game!" +
                            " Please restart or exit the game. "
                    titleText = "You won!"
                }
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text(text = titleText) },
                    text = { Text(text = textToShow) },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.resetStates()
                            lostDialogState.value = false
                            wonDialogState.value = false
                            character = ""
                            navController.navigate(Screen.StartGame.route)
                            /* dropdownEnabled = true*/
                        }) {
                            Text(text = "New Game")
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            activity?.finish()
                        }) {
                            Text(text = "Exit")
                        }
                    }

                )
                }*/
        }
    }
}

