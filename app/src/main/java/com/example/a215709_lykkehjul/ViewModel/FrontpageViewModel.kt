package com.example.a215709_lykkehjul.ViewModel

import androidx.lifecycle.ViewModel
import com.example.a215709_lykkehjul.Data.CategoryData
import com.example.a215709_lykkehjul.Model.Category
import com.example.a215709_lykkehjul.Model.States
import com.example.a215709_lykkehjul.Model.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class FrontpageViewModel(var categoryData: CategoryData) : ViewModel() {
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
        val emptyString = ""
        val emptyList = mutableListOf<Char>()

        _uiState.update { it.copy("", wordSoFar = emptyList,5,0,0, guessedLetters = emptyList,false,false  ) }
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
        var newBalance = 0
        if (randomValue == -1){
            println("lol")
        } else {
            newBalance = randomValue
        }
        _uiState.update { it.copy(tempBalance = newBalance) }
    }

    fun updateWordSoFar(char: String){
        val character = char.toUpperCase()
        val newList = mutableListOf<Char>()
        var numOfLives = uiState.value.amountOfLives
        val newNumOfLives : Int
        var tempBalance = uiState.value.tempBalance
        var balance = uiState.value.balance
        var guessedLettersList = uiState.value.guessedLetters
        var correctlyGuessedLetters = uiState.value.correctlyGuessedLetters

        for (i in 0 until uiState.value.wordDrawn.length){
            newList.add(i,uiState.value.wordSoFar[i])
           if (uiState.value.wordDrawn[i].equals(character.single())){
               newList.set(i,character.single())
               balance += tempBalance
               correctlyGuessedLetters.add(character.single())
           }
        }
        if (!uiState.value.wordDrawn.contains(character)){
            newNumOfLives = numOfLives-1
            numOfLives = newNumOfLives
        }
        guessedLettersList.add(char.single())
        _uiState.update { it.copy(wordSoFar = newList, amountOfLives = numOfLives, balance = balance, tempBalance = 0, guessedLetters = guessedLettersList) }
    }
    fun updateWordDrawn(word : String){
       var DrawWord = word.toUpperCase()
        uiState.value.wordDrawn = DrawWord
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
