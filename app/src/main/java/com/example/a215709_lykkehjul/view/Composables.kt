package com.example.a215709_lykkehjul.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a215709_lykkehjul.R
import com.example.a215709_lykkehjul.data.Controller
import com.example.a215709_lykkehjul.model.States
import com.example.a215709_lykkehjul.viewModel.FrontpageViewModel


//Navigation controller to navigate between screens.
@Composable
fun NavController(){

    val navController = rememberNavController()

    //Initializes data (predefines categories and words)
    val controller = Controller()
    controller.initializeData()

    //Saves viewmodel in variable for easy access
    val frontpageViewModel = FrontpageViewModel(controller.categoryData)

    //Routes are collected from Screen class.
    NavHost(navController = navController, startDestination = Screen.NewGamePage.route) {
        composable(route = Screen.GamePage.route) {
            FrontPage(viewModel = frontpageViewModel, navController)
        }
        composable(route = Screen.NewGamePage.route) {
            StartPage(viewModel = frontpageViewModel, navController)
        }
        composable(route = Screen.RulePage.route) {
            RulePage(viewModel = frontpageViewModel, navController)
        }
    }
}

const val barColor = "#FFCCB8"

@Composable
fun TopBar(navController: NavController, inGame : Boolean, state: States) {
    //sets up variables to use
    var alpha = 0f
    var enabled = false

    //values from state saved in variable
    val livesText = state.life
    val balanceText = state.balance

    //updates variables
    if (inGame) {
        alpha = 100f
        enabled = true
    }

    /*
    Top bar with multiple icons.
    If inGame is true, the top bar will have a point and life field and an exit iconbutton.
    If inGame is false, only an informationIcon is showed.
     */
    TopAppBar(
        backgroundColor = Color(barColor.toColorInt()),
        elevation = 5.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(inGame) {
                IconButton(onClick = {
                    navController.navigate(Screen.NewGamePage.route)
                }, modifier = Modifier.alpha(alpha), enabled = enabled) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
            } else{
                IconButton(onClick = {
                    navController.navigate(Screen.RulePage.route)
                }) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "", tint = Color.Black)
                }
            }


            Box(modifier = Modifier.alpha(alpha).padding(end=5.dp)) {
                Row() {
                    Box(
                        Modifier
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .width(50.dp)
                            .height(30.dp), contentAlignment = Alignment.CenterEnd){
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

                    Box(
                        Modifier
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .width(80.dp)
                            .height(30.dp)
                            , contentAlignment = Alignment.CenterEnd){
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
