package com.example.a215709_lykkehjul.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.a215709_lykkehjul.data.CategoryData
import com.example.a215709_lykkehjul.model.Category
import com.example.a215709_lykkehjul.model.States
import com.example.a215709_lykkehjul.model.Word
import kotlin.random.Random

class GameViewModel(private var categoryData: CategoryData) : ViewModel() {
    private val _uiState = mutableStateOf(States())
    val state: State<States> = _uiState


    fun spin(){
        _uiState.value = _uiState.value.copy(isSpinning = true)
    }

    fun stopSpin(){
        _uiState.value = _uiState.value.copy(isSpinning = false)
    }

    fun randomWord(title : String){
        var wordList = mutableListOf<Word>()
        for (category : Category in categoryData.categories){
            if (title.equals(category.title))
                wordList = category.words.toMutableList()
        }
        val randomInt = Random.nextInt(wordList.size)
        val randomWord = wordList[randomInt].word

        var word = randomWord.toUpperCase()

        _uiState.value = _uiState.value.copy(chosenWord = word)
        _uiState.value = _uiState.value.copy(wordDrawn = word)
        updateWordDrawn()
    }

    fun categoryTitleList(){
        val titleList = mutableListOf<String>()
        for (category : Category in categoryData.categories) {
            titleList.add(category.title)
        }
        _uiState.value = _uiState.value.copy(titleList=titleList)
    }

    fun resetStates(){
        val emptyList = mutableListOf<Char>()

        _uiState.value = _uiState.value.copy(
            wordDrawn = "",
            wordSoFar = emptyList,
            amountOfLives = 5,
            balance = 0,
            tempBalance = -1,
            guessedLetters = emptyList,
            gameLost = false,
            gameWon = false,
            chosenWord = "",
            errorMessageVisibility = 0f,
            correctlyGuessedLetters = emptyList,
            validInput = false
        )
    }

    fun resetGuessedLetters(){
        val emptyList = mutableListOf<Char>()

        _uiState.value = _uiState.value.copy(guessedLetters = emptyList)
    }


    fun checkLost(){
        if (state.value.amountOfLives-1 == 0){
            _uiState.value = _uiState.value.copy(gameLost = true)
        }
    }

    fun checkInput(character : String){
            if (character.length==1 && character.single() in 'A'..'Z') {
                _uiState.value = _uiState.value.copy(errorMessageVisibility = 0f, validInput = true)
            }
        else {
                _uiState.value = _uiState.value.copy(errorMessageVisibility = 100f, validInput = false)
        }
    }

    fun checkWin(){
        var guessedCorrectly = false
        var correctlyGuessed = 0
        var lettersToGuess = 0
        for (i in 0 until state.value.wordDrawn.length){
            if(!state.value.wordDrawn[i].equals(" ")){
                lettersToGuess = lettersToGuess + 1
            }
           if (!state.value.wordDrawn[i].equals(" ") && state.value.wordSoFar[i].equals(state.value.wordDrawn[i])){
               correctlyGuessed = correctlyGuessed + 1
           }
            if (!state.value.wordDrawn[i].equals(" ") && !state.value.wordSoFar[i].equals(state.value.wordDrawn[i])){
                guessedCorrectly = false
            }
        }
        if (correctlyGuessed == lettersToGuess){
            guessedCorrectly = true
        }

        _uiState.value = _uiState.value.copy(gameWon = guessedCorrectly)
    }

    fun spinTheWheel(){
        val listOfPoint = listOf(1000,900,800,700,600,500,400,300,200,100,-1)
        val randomIndex = Random.nextInt(listOfPoint.size)
        val randomValue = listOfPoint[randomIndex]

        if (randomValue == -1){
            _uiState.value = _uiState.value.copy(balance = 0, isBankrupt = true, tempBalance = 0)
            return
        } else {
            var newBalance = randomValue
            _uiState.value = _uiState.value.copy(tempBalance = newBalance
                //, guessEnabled = true, spinEnabled = false
        )
        }
    }

    fun updateWordSoFar(char: String) {
        val character = char.toUpperCase()
        val newList = mutableListOf<Char>()
        var numOfLives = state.value.amountOfLives
        val newNumOfLives: Int
        val tempBalance = state.value.tempBalance
        var balance = state.value.balance
        val guessedLettersList = state.value.guessedLetters
        val correctlyGuessedLetters = state.value.correctlyGuessedLetters
        var numOfLetters = 0

        checkInput(character)
        if (state.value.validInput) {
            if (!guessedLettersList.contains(char.single())) {

                for (i in 0 until state.value.wordDrawn.length) {
                    newList.add(i, state.value.wordSoFar[i])
                    if (state.value.wordDrawn[i].equals(character.single())) {
                        newList.set(i, character.single())
                        numOfLetters = numOfLetters + 1

                        correctlyGuessedLetters.add(character.single())
                    }
                }
                balance += tempBalance * numOfLetters
                if (!state.value.wordDrawn.contains(character)) {
                    newNumOfLives = numOfLives - 1
                    numOfLives = newNumOfLives
                }
                guessedLettersList.add(char.single())
                _uiState.value = _uiState.value.copy(
                        wordSoFar = newList,
                        amountOfLives = numOfLives,
                        balance = balance,
                        tempBalance = -1,
                        guessedLetters = guessedLettersList,
                        errorMessageVisibility = 0f,
                        validInput = true
                    )
                }
            } else {
                return
            }
        }

    private fun updateWordDrawn(/*word : String*/){
    var list = mutableListOf<Char>()
        for (char : Char in state.value.wordDrawn){
            if (char == ' '){
                list.add('-')
            }
            else {
                list.add('_')
            }
        }
        _uiState.value = _uiState.value.copy(wordSoFar = list)
    }


}
