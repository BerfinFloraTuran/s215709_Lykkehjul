package com.example.a215709_lykkehjul.ViewModel

import androidx.lifecycle.ViewModel
import com.example.a215709_lykkehjul.Model.States
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class FrontpageViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(States())
    val uiState = _uiState.asStateFlow()


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

        for (i in 0 until uiState.value.wordDrawn.length){
            newList.add(i,uiState.value.wordSoFar[i])
           if (uiState.value.wordDrawn[i].equals(character.single())){
               newList.set(i,character.single())
               balance += tempBalance
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
        val word = word.toUpperCase()
        uiState.value.wordDrawn = word
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
