package com.example.a215709_lykkehjul.view

sealed class Screen(val route: String) {

    //Routes to use in navigation controller
    object GamePage : Screen("gamepage")
    object NewGamePage : Screen("NewGame")
    object RulePage : Screen("Rules")
}
