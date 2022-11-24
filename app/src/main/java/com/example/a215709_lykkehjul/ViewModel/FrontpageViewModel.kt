package com.example.a215709_lykkehjul.ViewModel

import androidx.lifecycle.ViewModel
import com.example.a215709_lykkehjul.Model.States
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FrontpageViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(States())
    val uiState = _uiState.asStateFlow()

    fun updateWordSoFar(char: String){
        val character = char.toUpperCase()
        val newList = mutableListOf<Char>()
        var numOfLives = uiState.value.amountOfLives
        val newNumOfLives : Int

        for (i in 0 until uiState.value.wordDrawn.length){
            newList.add(i,uiState.value.wordSoFar[i])
           if (uiState.value.wordDrawn[i].equals(character.single())){
               newList.set(i,character.single())
           }
        }
        if (!uiState.value.wordDrawn.contains(character)){
            newNumOfLives = numOfLives-1
            numOfLives = newNumOfLives
        }
        _uiState.update { it.copy(wordSoFar = newList, amountOfLives = numOfLives) }
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
