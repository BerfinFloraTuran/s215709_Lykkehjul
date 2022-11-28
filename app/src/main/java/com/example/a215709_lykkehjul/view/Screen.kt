package com.example.a215709_lykkehjul.view

sealed class Screen(val route: String) {
    object GamePage : Screen("gamepage")
    object StartGame : Screen("startgame")
}
