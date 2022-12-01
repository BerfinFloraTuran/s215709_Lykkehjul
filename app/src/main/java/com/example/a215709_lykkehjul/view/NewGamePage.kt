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
import androidx.compose.ui.text.font.FontFamily
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
fun StartPage( viewModel: FrontpageViewModel, navController : NavController){
    val backgroundColor = "#fff8f6"
    val state = viewModel.state.value
   // viewModel.randomCategory()


    Scaffold(
        backgroundColor = Color(backgroundColor.toColorInt()),
        topBar = { TopBar(navController, false, viewModel,state) },
        content = {  paddingValues -> StartPageContent(viewModel, state, modifier = Modifier.padding(paddingValues), navController) }
    )
}

@Composable
fun StartPageContent(viewModel: FrontpageViewModel, state: States, modifier: Modifier, navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(20.dp))
        Text("Wheel of Fortune",
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Click the button below to start the game or click the information icon to read the game guide.",
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
        fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center)
        Text("Good luck! :)",
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center)

        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)) {
            Image(painter = painterResource(id = R.drawable.palmillustration), contentDescription = "", Modifier.size(300.dp))
        }
        Spacer(modifier = Modifier.height(15.dp))
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
                .padding(start = 90.dp)
                .size(170.dp, 40.dp)) {
                Text(text = "Start Game!",
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace)
            }
        }
    }
}
