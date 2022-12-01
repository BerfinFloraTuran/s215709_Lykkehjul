package com.example.a215709_lykkehjul.view

sealed class Screen(val route: String) {
    object GamePage : Screen("gamepage")
    object NewGamePage : Screen("NewGame")
    object RulePage : Screen("Rules")
}
