package com.example.a215709_lykkehjul.View

sealed class Screen(val route: String) {
    object GamePage : Screen("gamepage")
}
