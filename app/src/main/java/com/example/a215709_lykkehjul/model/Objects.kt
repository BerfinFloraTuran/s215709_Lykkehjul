package com.example.a215709_lykkehjul.model

data class Category(
    var title: String,
    var words : List<Word>
)

data class Word(
    var word: String
)

data class States(
    var wordDrawn: String = "",
    var wordSoFar: MutableList<Char> = mutableListOf<Char>(),
    var amountOfLives: Int = 5,
    var balance: Int = 0,
    var tempBalance: Int = 0,
    var guessedLetters: MutableList<Char> = mutableListOf(),
    var gameLost: Boolean = false,
    var gameWon: Boolean = false,
    var titleList: MutableList<String> = mutableListOf(),
    var chosenCategory: String = "Animals",
    var chosenWord: String = "",
    var visibility: Float = 0f,
    var correctlyGuessedLetters: MutableList<Char> = mutableListOf()

)
