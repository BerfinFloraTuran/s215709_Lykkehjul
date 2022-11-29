package com.example.a215709_lykkehjul.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        topBar = { TopBar(navController, false, viewModel, state) },
        content = {  paddingValues -> startGame(viewModel, state, modifier = Modifier.padding(paddingValues), navController) }
    )
}


@Composable
fun startGame(viewModel: GameViewModel, state: States, modifier: Modifier, navController: NavController) {

    var dropdownEnabled by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            val categoryTitle = state.chosenCategory

            viewModel.categoryTitleList()

            val categoryList = state.titleList

            var expanded by remember { mutableStateOf(false) }

            Row(modifier = Modifier.padding(start = 30.dp)) {
                Text(
                    text = "Category: $categoryTitle",
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic
                )
                Box(contentAlignment = Alignment.CenterStart) {}
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
                            viewModel.randomWord(itemValue)
                            viewModel.resetGuessedLetters()
                        }, enabled = itemIndex != -1)
                        {
                            Text(text = itemValue)
                        }
                    }
                }
            }

            val buttonColor = "#ffe3e6"
            Button(
                onClick = {
                    navController.navigate(Screen.WheelPage.route)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(buttonColor.toColorInt()))
            ) {
                Text(text = "Start game!")

            }


        }
    }




}


