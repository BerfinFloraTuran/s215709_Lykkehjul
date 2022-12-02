package com.example.a215709_lykkehjul.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.a215709_lykkehjul.data.CategoryData
import com.example.a215709_lykkehjul.model.Category
import com.example.a215709_lykkehjul.model.States

class FrontpageViewModel(private var categoryData: CategoryData) : ViewModel() {

    //To access states. One is mutable, the other is not.
    private val _uiState = mutableStateOf(States())
    val state: State<States> = _uiState

    //function to pick out random category
    fun randomCategory() {
        val randomCategoryTitle = categoryData.categories.random().title

        _uiState.value = _uiState.value.copy(chosenCategory = randomCategoryTitle)
        randomWord(randomCategoryTitle)
    }

    //function to pick out random word from categoryTitle
    fun randomWord(title: String) {
        var word = ""

        for (category: Category in categoryData.categories) {
            if (title.equals(category.title)){
                word = category.words.random().word.toUpperCase()
            }
        }

        _uiState.value = _uiState.value.copy(chosenWord = word)
        updateChosenWord()
    }

    //Iterates through all categories and adds their titles to a list for the dropdown menu
    fun categoryTitleList() {
        val titleList = mutableListOf<String>()
        for (category: Category in categoryData.categories) {
            titleList.add(category.title)
        }
        _uiState.value = _uiState.value.copy(categoryTitleList = titleList)
    }

    //Resets all states for new game
    fun resetStates(){
        val emptyList = mutableListOf<Char>()
        resetGuessedLetters()
        randomCategory()

        _uiState.value = _uiState.value.copy(
            life = 5,
            balance = 0,
            wheelResult = -1,
            hasLost = false,
            hasWon = false,
            errorMessageVisibility = 0f,
            correctlyGuessedLetters = emptyList,
            isValidInput = false,
            guessEnabled = false,
            spinEnabled = true
        )
    }

    fun setChosenCategory(title: String) {
        _uiState.value = _uiState.value.copy(chosenCategory = title)
    }

    fun resetGuessedLetters() {
        val emptyList = mutableListOf<Char>()

        _uiState.value = _uiState.value.copy(guessedLetters = emptyList)
    }

    //Checks if player has 0 lives. Sets hasLost to true, if life is = 0
    fun checkLost() {
        if (state.value.life == 0) {
            _uiState.value = _uiState.value.copy(hasLost = true)
        }
    }

    //Checks if input is valid
    fun checkInput(character: String) {
        if (character.length == 1 && character.single() in 'A'..'Z') {
            _uiState.value = _uiState.value.copy(errorMessageVisibility = 0f, isValidInput = true)
        } else {
            _uiState.value = _uiState.value.copy(errorMessageVisibility = 100f, isValidInput = false)
        }
    }

    //Checks if player has won by iterating through chosen word and compares every character to charGuessList
    fun checkWin() {
        var guessedCorrectly = false
        var correctlyGuessed = 0
        var lettersToGuess = 0
        for (i in 0 until state.value.chosenWord.length) {
            if (!state.value.chosenWord[i].equals(" ")) {
                lettersToGuess = lettersToGuess + 1
            }
            if (!state.value.chosenWord[i].equals(" ") && state.value.charGuessList[i].equals(state.value.chosenWord[i])) {
                correctlyGuessed = correctlyGuessed + 1
            }
            if (!state.value.chosenWord[i].equals(" ") && !state.value.charGuessList[i].equals(state.value.chosenWord[i])) {
                guessedCorrectly = false
            }
        }
        if (correctlyGuessed == lettersToGuess) {
            guessedCorrectly = true
        }

        _uiState.value = _uiState.value.copy(hasWon = guessedCorrectly)
    }

    //Spins the wheel and sets player balance to 0 if result is bankrupt (-1)

    fun spinTheWheel() {
        val listOfPoint = listOf(1000, 900, 800, 700, 600, 500, 400, 300, 200, 100, -1)
        val randomValue = listOfPoint.random()

        if (randomValue == -1) {
            _uiState.value = _uiState.value.copy(balance = 0, isBankrupt = true, wheelResult = 0)
            return
        } else {
            var newBalance = randomValue
            _uiState.value = _uiState.value.copy(
                wheelResult = newBalance,
                guessEnabled = true,
                spinEnabled = false
            )
        }
    }

    //Updates the charGuessList with new updated list. Has responsibilty of updating life, balance and charGuessList.
    fun updateWordSoFar(char: String) {
        val character = char.toUpperCase()
        val newList = mutableListOf<Char>()
        var numOfLives = state.value.life
        val newNumOfLives: Int
        val tempBalance = state.value.wheelResult
        var balance = state.value.balance
        val guessedLettersList = state.value.guessedLetters
        val correctlyGuessedLetters = state.value.correctlyGuessedLetters
        var numOfLetters = 0

        checkInput(character)
        if (state.value.isValidInput) {
            if (!guessedLettersList.contains(character.single())) {

                for (i in 0 until state.value.chosenWord.length) {
                    newList.add(i, state.value.charGuessList[i])
                    if (state.value.chosenWord[i].equals(character.single())) {
                        newList.set(i, character.single())
                        numOfLetters = numOfLetters + 1

                        correctlyGuessedLetters.add(character.single())
                    }
                }

                balance += tempBalance * numOfLetters
                if (!state.value.chosenWord.contains(character)) {
                    newNumOfLives = numOfLives - 1
                    numOfLives = newNumOfLives
                }

                guessedLettersList.add(character.single())
                _uiState.value = _uiState.value.copy(
                    charGuessList = newList,
                    life = numOfLives,
                    balance = balance,
                    wheelResult = -1,
                    guessedLetters = guessedLettersList,
                    errorMessageVisibility = 0f,
                    isValidInput = true,
                    spinEnabled = true,
                    guessEnabled = false
                )
            }
        } else {
            return
        }
        }


    private fun updateChosenWord(){
        _uiState.value = _uiState.value.copy(charGuessList = mutableListOf())
        for (char : Char in state.value.chosenWord){
            if (char == ' '){
                state.value.charGuessList.add('-')
            }
            else {
                state.value.charGuessList.add('_')
            }
        }
    }

}
