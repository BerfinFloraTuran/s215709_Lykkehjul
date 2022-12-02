package com.example.a215709_lykkehjul.model

//Information needed to create a category
data class Category(
    var title: String,
    var words : List<Word>
)

//Information needed to create a word
data class Word(
    var word: String
)

/*
States that can be changed during the game are saved here.
 */
data class States(
    var chosenWord: String = "",
    var charGuessList: MutableList<Char> = mutableListOf<Char>(),
    var life: Int = 5,
    var balance: Int = 0,
    var wheelResult: Int = -1,
    var guessedLetters: MutableList<Char> = mutableListOf(),
    var hasLost: Boolean = false,
    var hasWon: Boolean = false,
    var categoryTitleList: MutableList<String> = mutableListOf(),
    var chosenCategory: String = "",
    var errorMessageVisibility: Float = 0f,
    var correctlyGuessedLetters: MutableList<Char> = mutableListOf(),
    var isValidInput: Boolean = false,
    var guessEnabled: Boolean = false,
    var spinEnabled: Boolean = true,
    var isBankrupt: Boolean = false,
    var dropDownEnabled: Boolean = true,
)
