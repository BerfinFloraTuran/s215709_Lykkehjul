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
fun FrontPage( viewModel: FrontpageViewModel, navController : NavController){
    val backgroundColor = "#fff8f6"
    val state = viewModel.state.value


    Scaffold(
        backgroundColor = Color(backgroundColor.toColorInt()),
        topBar = { TopBar(navController, true,viewModel,state) },
        content = {  paddingValues -> FrontPageContent(viewModel, state, modifier = Modifier.padding(paddingValues), navController) }
    )
}

@Composable
fun FrontPageContent(viewModel: FrontpageViewModel, state: States, modifier: Modifier, navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(20.dp))

        val categoryTitle = state.chosenCategory

        viewModel.categoryTitleList()

        val categoryList = state.titleList

        var expanded by remember { mutableStateOf(false) }

        Row(modifier = Modifier.padding(start = 30.dp)) {
            Text(
                text = "Category: $categoryTitle",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            )
            IconButton(onClick = {
                expanded = true
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "",
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                categoryList.forEachIndexed { itemIndex, itemValue ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        viewModel.setChosenCategory(itemValue)
                        viewModel.resetStates()
                        viewModel.randomWord(itemValue)
                        viewModel.resetGuessedLetters()
                    }, enabled = itemIndex != -1)
                    {
                        Text(text = itemValue)
                    }
                }
            }
            IconButton(onClick = {
                viewModel.resetStates()
                viewModel.randomCategory()
                viewModel.resetGuessedLetters()
            }) {
                Icon(painter = painterResource(id = R.drawable.randomicon), contentDescription = "",
                    Modifier
                        .size(40.dp)
                        .padding(bottom=15.dp)
                )
            }
        }

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
                pointText = "Spin the wheel using the button below."
            }

            Text(text = pointText, fontSize = 16.sp)

            val buttonColor = "#FFCCB8"
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
/*
            Spacer(modifier = Modifier.height(30.dp))

            val livesText = state.amountOfLives
            Text(text = "$livesText lives left", fontSize = 16.sp)

            val balanceText = state.balance
            Text(text = "Balance: $balanceText", fontSize = 16.sp)

 */
            val boxColor = "#66FFCCB8"
            val color = Color(boxColor.toColorInt())

            Spacer(modifier = Modifier.height(70.dp))
            Box(
                modifier = Modifier
                    .background(
                        color = color,
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                    )
                    .fillMaxWidth()
                    .height(250.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column() {
                    Text(
                        text = "Guessed letters",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top=20.dp)
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    var guessedList = ""

                    for (i in 0 until state.guessedLetters.size) {
                        guessedList = guessedList + state.guessedLetters[i] + "  "
                    }
                    Text(text = guessedList, fontSize = 18.sp)
                }
            }

            // Divider(startIndent = 0.dp, thickness = 1.dp, color = Color.LightGray)


            val activity = (LocalContext.current as? Activity)

            val lostDialogState = remember { mutableStateOf(state.gameLost) }
            val wonDialogState = remember { mutableStateOf(state.gameWon) }
            var textToShow = ""
            var titleText = ""
            val dialogCol = "#FFEBE3"

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
                            viewModel.resetGuessedLetters()
                            lostDialogState.value = false
                            wonDialogState.value = false
                            character = ""
                        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(buttonColor.toColorInt()))
                        ) {
                            Text(text = "New Game")
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            activity?.finish()
                            wonDialogState.value = false
                            wonDialogState.value = false
                            character = ""
                        },colors = ButtonDefaults.buttonColors(backgroundColor = Color(buttonColor.toColorInt()))) {
                            Text(text = "Exit")
                        }
                    },
                    backgroundColor = Color(dialogCol.toColorInt())
                )
            }
        }
    }
}
