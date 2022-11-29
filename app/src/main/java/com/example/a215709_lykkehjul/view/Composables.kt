package com.example.a215709_lykkehjul.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a215709_lykkehjul.R
import com.example.a215709_lykkehjul.data.Controller
import com.example.a215709_lykkehjul.model.States
import com.example.a215709_lykkehjul.viewModel.GameViewModel

@Composable
fun NavController(){

    val navController = rememberNavController()
    var controller = Controller()
    controller.initializeData()
    val gameViewModel = GameViewModel(controller.categoryData)

    NavHost(navController = navController, startDestination = Screen.StartGame.route) {
        composable(route = Screen.GamePage.route) {
            FrontPage(viewModel = gameViewModel, navController)
        }
        composable(route = Screen.StartGame.route){
            StartPage(viewModel = gameViewModel, navController)
        }
        composable(route = Screen.WheelPage.route){
            WheelSpinPage(viewModel = gameViewModel, navController)
        }
        composable(route = Screen.WonPage.route){
            WonPage(viewModel = gameViewModel, navController)
        }
        composable(route = Screen.LostPage.route){
            LostPage(viewModel = gameViewModel, navController)
        }
    }
}

val barColor = "#ffeef0"

@Composable
fun TopBar(navController: NavController, hasExit : Boolean, viewModel: GameViewModel, state: States) {
    var alpha = 0f
    var enabled = false

    val livesText = state.amountOfLives
    val balanceText = state.balance

    if (hasExit) {
        alpha = 100f
        enabled = true
    }

    TopAppBar(
        backgroundColor = Color(barColor.toColorInt()),
        elevation = 5.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {
                navController.navigate(Screen.StartGame.route)
                viewModel.resetStates()
            }, modifier = Modifier.alpha(alpha), enabled = enabled) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "")
            }


            Box(modifier = Modifier.alpha(alpha)) {
                Row() {
                    Box(Modifier.background(Color.White, shape = RoundedCornerShape(10.dp)).width(50.dp).height(30.dp), contentAlignment = Alignment.CenterEnd){
                        Row() {
                            Text(
                                text = "$livesText",
                                modifier = Modifier.padding(top = 2.dp, end = 5.dp)
                            )
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = "",
                                tint = Color.Red, modifier = Modifier
                                    .padding(end = 3.dp)
                            )
                        }
                    }


                    Spacer(Modifier.width(10.dp))

                    Box(Modifier.background(Color.White, shape = RoundedCornerShape(10.dp)).width(80.dp).height(30.dp), contentAlignment = Alignment.CenterEnd){
                        Row() {
                            Text(
                                text = "$balanceText",
                                modifier = Modifier.padding(top = 5.dp, end = 2.dp)
                            )
                            Image(painter = painterResource(id = R.drawable.pointicon), contentDescription = "",
                                Modifier
                                    .size(32.dp)
                                .padding(end = 3.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(){
    BottomAppBar(
        backgroundColor = Color(barColor.toColorInt())
    ) {
    }
}
