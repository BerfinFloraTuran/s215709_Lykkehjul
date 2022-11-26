package com.example.a215709_lykkehjul.viewModel

import androidx.lifecycle.ViewModel
import com.example.a215709_lykkehjul.data.CategoryData
import com.example.a215709_lykkehjul.model.Category
import com.example.a215709_lykkehjul.model.States
import com.example.a215709_lykkehjul.model.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class FrontpageViewModel(private var categoryData: CategoryData) : ViewModel() {
    private val _uiState = MutableStateFlow(States())
    val uiState = _uiState.asStateFlow()

    fun randomWord(title : String){
        var wordList = mutableListOf<Word>()
        for (category : Category in categoryData.categories){
            if (title.equals(category.title))
                wordList = category.words.toMutableList()
        }
        val randomInt = Random.nextInt(wordList.size)
        val randomWord = wordList[randomInt].word

        _uiState.update { it.copy(chosenWord = randomWord) }
        updateWordDrawn(uiState.value.chosenWord)
    }


    fun categoryTitleList(){
        val titleList = mutableListOf<String>()
        for (category : Category in categoryData.categories) {
            titleList.add(category.title)
        }
        _uiState.update { it.copy(titleList=titleList) }
    }

    fun resetStates(){
        val emptyList = mutableListOf<Char>()

        _uiState.update { it.copy(
            wordDrawn = "",
            wordSoFar = emptyList,
            amountOfLives = 5,
            balance = 0,
            tempBalance = -1,
            guessedLetters = emptyList,
            gameLost = false,
            gameWon = false,
            chosenWord = "",
            visibility = 0f,
            errorMessageVisibility = 0f,
            correctlyGuessedLetters = emptyList,
            validInput = false,
            guessEnabled = false,
            spinEnabled = true
        ) }
    }

    fun resetGuessedLetters(){
        val emptyList = mutableListOf<Char>()

        _uiState.update { it.copy(guessedLetters = emptyList) }
    }


    fun checkLost(){
        if (uiState.value.amountOfLives == 0){
            _uiState.update { it.copy(gameLost = true) }
        }
    }

    fun checkInput(character : String){
            if (character.length==1 && character.single() in 'A'..'Z') {
                _uiState.update { it.copy(errorMessageVisibility = 0f, validInput = true) }
            }
        else {
            _uiState.update { it.copy(errorMessageVisibility = 100f, validInput = false) }
        }
    }

    fun checkWin(){
        var guessedCorrectly = false
        var correctlyGuessed = 0
        var lettersToGuess = 0
        for (i in 0 until uiState.value.wordDrawn.length){
            if(!uiState.value.wordDrawn[i].equals(" ")){
                lettersToGuess = lettersToGuess + 1
            }
           if (!uiState.value.wordDrawn[i].equals(" ") && uiState.value.wordSoFar[i].equals(uiState.value.wordDrawn[i])){
               correctlyGuessed = correctlyGuessed + 1
           }
            if (!uiState.value.wordDrawn[i].equals(" ") && !uiState.value.wordSoFar[i].equals(uiState.value.wordDrawn[i])){
                guessedCorrectly = false
            }
        }
        if (correctlyGuessed == lettersToGuess){
            guessedCorrectly = true
        }

        _uiState.update { it.copy(gameWon = guessedCorrectly) }
    }

    fun spinTheWheel(){
        val listOfPoint = listOf(1000,900,800,700,600,500,400,300,200,100,-1)
        val randomIndex = Random.nextInt(listOfPoint.size)
        val randomValue = listOfPoint[randomIndex]

        if (randomValue == -1){
            _uiState.update { it.copy(balance = 0, isBankrupt = true, tempBalance = 0) }
            return
        } else {
            var newBalance = randomValue
            _uiState.update { it.copy(tempBalance = newBalance, guessEnabled = true, spinEnabled = false) }
        }
    }

    fun updateWordSoFar(char: String) {
        val character = char.toUpperCase()
        val newList = mutableListOf<Char>()
        var numOfLives = uiState.value.amountOfLives
        val newNumOfLives: Int
        val tempBalance = uiState.value.tempBalance
        var balance = uiState.value.balance
        val guessedLettersList = uiState.value.guessedLetters
        val correctlyGuessedLetters = uiState.value.correctlyGuessedLetters
        var numOfLetters = 0

        checkInput(character)
        if (uiState.value.validInput) {
            if (!guessedLettersList.contains(char.single())) {

                for (i in 0 until uiState.value.wordDrawn.length) {
                    newList.add(i, uiState.value.wordSoFar[i])
                    if (uiState.value.wordDrawn[i].equals(character.single())) {
                        newList.set(i, character.single())
                        numOfLetters = numOfLetters + 1

                        correctlyGuessedLetters.add(character.single())
                    }
                }
                balance += tempBalance * numOfLetters
                if (!uiState.value.wordDrawn.contains(character)) {
                    newNumOfLives = numOfLives - 1
                    numOfLives = newNumOfLives
                }
                guessedLettersList.add(char.single())
                _uiState.update {
                    it.copy(
                        wordSoFar = newList,
                        amountOfLives = numOfLives,
                        balance = balance,
                        tempBalance = -1,
                        guessedLetters = guessedLettersList,
                        errorMessageVisibility = 0f,
                        validInput = true,
                        spinEnabled = true,
                        guessEnabled = false
                    )
                }
            } else {
                return
            }
        }
    }

    private fun updateWordDrawn(word : String){
       var drawWord = word.toUpperCase()
        uiState.value.wordDrawn = drawWord
        for (char : Char in uiState.value.wordDrawn){
            if (char == ' '){
                uiState.value.wordSoFar.add('-')
            }
            else {
                uiState.value.wordSoFar.add('_')
            }
        }
    }

}
