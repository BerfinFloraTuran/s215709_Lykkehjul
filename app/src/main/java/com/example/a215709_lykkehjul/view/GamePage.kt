package com.example.a215709_lykkehjul.view

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        //mutableStateOf in one place
        var expanded by remember { mutableStateOf(false) }
        var character by remember { mutableStateOf("") }
        val lostDialogState = remember { mutableStateOf(state.hasLost) }
        val wonDialogState = remember { mutableStateOf(state.hasWon) }

        //empty text for alertdialog
        var textToShow = ""
        var titleText = ""

        //values from state saved in easily accessible variables
        val categoryTitle = state.chosenCategory
        val categoryList = state.categoryTitleList
        val point = state.wheelResult

        //colors saved as strings
        val dialogCol = "#FFEBE3"
        val buttonColor = "#FFCCB8"
        val boxColor = "#66FFCCB8"
        val color = Color(boxColor.toColorInt())

        //Text to show chosen category
        Spacer(modifier = Modifier.height(20.dp))
        viewModel.categoryTitleList()
        val category = stringResource(id = R.string.category)

        Row(modifier = Modifier.padding(start = 30.dp)) {
            Text( text = "$category $categoryTitle",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            )

            //Icon to expand dropdown menu
            IconButton(onClick = {
                expanded = true
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "",
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }

            //Dropdown menu to pick a category
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                categoryList.forEachIndexed { itemIndex, itemValue ->
                    DropdownMenuItem(onClick = {

                        //closes dropdown menu, reset states and picks out a random word from the chosen category
                        expanded = false
                        viewModel.resetStates()
                        viewModel.setChosenCategory(itemValue)
                        viewModel.randomWord(itemValue)
                        viewModel.resetGuessedLetters()
                    }, enabled = itemIndex != -1)
                    {
                        Text(text = itemValue)
                    }
                }
            }

            //Shuffle icon to randomize chosen category/word
            IconButton(onClick = {
                viewModel.resetStates()
                viewModel.randomCategory()
                viewModel.resetGuessedLetters()
            }) {
                Icon(painter = painterResource(id = R.drawable.shuffleicon), contentDescription = "",
                    Modifier
                        .size(40.dp)
                        .padding(bottom = 15.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //result of wheel spin
            var pointText = "$point"
            if (point == 0) {
                pointText = stringResource(id = R.string.bankrupt)
            }
            if (point == -1) {
                pointText = stringResource(id = R.string.spin_wheel)
            }

            Text(text = pointText, fontSize = 16.sp)


            Spacer(modifier = Modifier.height(15.dp))

            //Button to spin the wheel
            Button(
                onClick = {
                    viewModel.spinTheWheel()
                },
                enabled = state.spinEnabled,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(buttonColor.toColorInt()))
            ) {
                Text(stringResource(id = R.string.spin_wheel_btn))
            }

            Spacer(modifier = Modifier.height(50.dp))

            //Adds a underscore (_) and space for every character to guess.
            var lineWord = ""
            for (i in 0 until state.charGuessList.size) {
                lineWord = lineWord + state.charGuessList[i] + " "
            }

            Text(text = lineWord, fontSize = 25.sp)

            Spacer(modifier = Modifier.height(20.dp))

            //Input field to guess a letter
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

            //Error message that turns visible if condition is met
            Row(modifier = Modifier.alpha(state.errorMessageVisibility)) {
                Text(text = stringResource(id = R.string.input_error_msg), color = Color.Red)
            }

            Spacer(modifier = Modifier.height(10.dp))

            //Button to guess letter
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


            //Space for guessed letters to show
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
                        stringResource(id = R.string.guessed_letters_cpt),
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

            //Makes user able to exit app
            val activity = (LocalContext.current as? Activity)

            /*
            Checks if game is lost or won.
            Updates the dialog-text accordingly.
            Shows an alert dialog with two buttons: Exit and New Game
             */
            if (state.hasLost || state.hasWon) {
                if (state.hasLost) {
                    textToShow = stringResource(id = R.string.game_lost_dialog)
                    titleText = stringResource(id = R.string.game_lost_dialog)
                }
                if (state.hasWon) {
                    textToShow = stringResource(id = R.string.game_won_dialog)
                    titleText = stringResource(id = R.string.game_won_title)
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
