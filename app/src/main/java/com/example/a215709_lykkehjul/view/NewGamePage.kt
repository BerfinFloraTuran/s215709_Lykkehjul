package com.example.a215709_lykkehjul.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.a215709_lykkehjul.R
import com.example.a215709_lykkehjul.viewModel.FrontpageViewModel

@Composable
fun StartPage( viewModel: FrontpageViewModel, navController : NavController){
    val backgroundColor = "#fff8f6"
    val state = viewModel.state.value

    Scaffold(
        backgroundColor = Color(backgroundColor.toColorInt()),
        topBar = { TopBar(navController, false,state) },
        content = {  paddingValues -> StartPageContent(viewModel, modifier = Modifier.padding(paddingValues), navController) }
    )
}

@Composable
fun StartPageContent(viewModel: FrontpageViewModel, modifier: Modifier, navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Spacer (modifier = Modifier.height(20.dp))

        //Logo
        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "", Modifier.size(300.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        //Intro text
        Text(
            stringResource(id = R.string.intro),
            fontWeight = FontWeight.Medium,
            fontSize = 25.sp,
        fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center)
        Text(stringResource(id = R.string.good_luck),
            fontWeight = FontWeight.Medium,
            fontSize = 25.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center)


        Spacer(modifier = Modifier.height(30.dp))


        //Button to start game
        val buttonColor = "#FFCCB8"

        Box(contentAlignment = Alignment.Center) {
            Button(onClick = {
                viewModel.resetStates()
                viewModel.randomCategory()
                viewModel.resetGuessedLetters()
                navController.navigate(Screen.GamePage.route)
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(buttonColor.toColorInt())),
                modifier = Modifier
                .size(170.dp, 40.dp)) {
                Text(text = stringResource(id = R.string.start_game),
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif)
            }
        }
    }
}
