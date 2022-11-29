package com.example.a215709_lykkehjul.view

sealed class Screen(val route: String) {
    object GamePage : Screen("gamepage")
    object StartGame : Screen("startgame")
    object WheelPage : Screen("wheelSpin")
    object WonPage : Screen("gameWon")
    object LostPage : Screen("gameLost")
}
