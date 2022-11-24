package com.example.a215709_lykkehjul.View

sealed class Screen(val route: String) {
    object FrontPageScreen : Screen("frontpage")
    object WordRevealScreen : Screen("wordreveal")

}
