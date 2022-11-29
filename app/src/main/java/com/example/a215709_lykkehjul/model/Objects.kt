package com.example.a215709_lykkehjul.model

data class Category(
    var title: String,
    var words : List<Word>
)

data class Word(
    var word: String
)

data class States(
    var wordDrawn: String = "DOG",
    var wordSoFar: MutableList<Char> = mutableListOf<Char>('_','_','_'),
    var amountOfLives: Int = 5,
    var balance: Int = 0,
    var tempBalance: Int = -1,
    var guessedLetters: MutableList<Char> = mutableListOf(),
    var gameLost: Boolean = false,
    var gameWon: Boolean = false,
    var titleList: MutableList<String> = mutableListOf(),
    var chosenCategory: String = "Animals",
    var chosenWord: String = "DOG",
    var errorMessageVisibility: Float = 0f,
    var correctlyGuessedLetters: MutableList<Char> = mutableListOf(),
    var validInput: Boolean = false,
    var guessEnabled: Boolean = false,
    var spinEnabled: Boolean = true,
    var isBankrupt: Boolean = false,
    var dropDownEnabled: Boolean = true,
    var lettersToReveal: MutableList<Char> = mutableListOf()
)
