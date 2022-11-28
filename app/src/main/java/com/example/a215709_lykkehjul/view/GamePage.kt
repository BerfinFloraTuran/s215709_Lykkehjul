package com.example.a215709_lykkehjul.view

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import com.example.a215709_lykkehjul.model.States
import com.example.a215709_lykkehjul.viewModel.FrontpageViewModel

@Composable
fun FrontPage( viewModel: FrontpageViewModel){
    val backgroundColor = "#fff6f7"
    val state = viewModel.state.value

    Scaffold(
        backgroundColor = Color(backgroundColor.toColorInt()),
        topBar = { TopBar() },
        content = {  paddingValues -> FrontPageContent(viewModel, state, modifier = Modifier.padding(paddingValues)) }
    )
}

@Composable
fun FrontPageContent(viewModel: FrontpageViewModel, state: States, modifier: Modifier) {
    var dropdownEnabled by remember { mutableStateOf(true) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(20.dp))

        val categoryTitle = state.chosenCategory

        viewModel.categoryTitleList()

        val categoryList = state.titleList

        var expanded by remember { mutableStateOf(false) }

        Row(modifier = Modifier.padding(start = 30.dp)) {
            Text(text = "Category: $categoryTitle", fontSize = 25.sp, fontStyle = FontStyle.Italic)
            Box(contentAlignment = Alignment.Center) {}
            IconButton(onClick = {
                expanded = true
            }, enabled = dropdownEnabled) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "",
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                categoryList.forEachIndexed { itemIndex, itemValue ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        state.chosenCategory = itemValue
                        dropdownEnabled = false
                        state.visibility = 100f
                        viewModel.randomWord(itemValue)
                        viewModel.resetGuessedLetters()
                    }, enabled = itemIndex != -1)
                    {
                        Text(text = itemValue)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.alpha(state.visibility),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val point = state.tempBalance
            var pointText = "$point"
            if (point == 0){
                pointText = "BANKRUPT. Try again."
            }
            if (point == -1){
                pointText = "Spin the wheel using the button below."
            }

            Text(text = pointText, fontSize = 16.sp)

            val buttonColor = "#ffe3e6"
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    viewModel.spinTheWheel()
                },
                enabled = state.spinEnabled,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(buttonColor.toColorInt()))
            ) {
                Text(text = "Spin the wheel")

            }

            Spacer(modifier = Modifier.height(50.dp))

            var lineWord = ""

            for (i in 0 until state.wordSoFar.size) {
                lineWord = lineWord + state.wordSoFar[i] + " "
            }

            Text(text = lineWord, fontSize = 25.sp)
            var character by remember { mutableStateOf("") }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = character,
                label = { Text(text = "Letter", textAlign = TextAlign.Center) },
                modifier = Modifier
                    .width(130.dp),
                onValueChange = {
                    character = it
                },
                enabled = state.guessEnabled,
                singleLine = true
            )
            Row(modifier = Modifier.alpha(state.errorMessageVisibility)) {
                Text(text = "Please input one letter", color = Color.Red)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                        viewModel.updateWordSoFar(character)
                        viewModel.checkLost()
                        viewModel.checkWin()
                        character = ""
                },
                enabled = state.guessEnabled,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(buttonColor.toColorInt()))
            ) {
                Text(text = "Guess")
            }

            Spacer(modifier = Modifier.height(30.dp))

            val livesText = state.amountOfLives
            Text(text = "$livesText lives left", fontSize = 16.sp)

            val balanceText = state.balance
            Text(text = "Balance: $balanceText", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(70.dp))

            Divider(startIndent = 0.dp, thickness = 1.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Guessed letters: ", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(25.dp))

            var guessedList = ""

            for (i in 0 until state.guessedLetters.size) {
                guessedList = guessedList + state.guessedLetters[i] + "  "
            }
            Text(text = guessedList, fontSize = 18.sp)

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
                            dropdownEnabled = true
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
}
