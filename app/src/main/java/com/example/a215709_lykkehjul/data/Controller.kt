package com.example.a215709_lykkehjul.data

import com.example.a215709_lykkehjul.model.Category
import com.example.a215709_lykkehjul.model.Word

class Controller {
    var categoryData = CategoryData()

    fun initializeData(){
        /* TODO */
        initCategoryData()
    }

    fun initCategoryData(){
        val animals = Category("Animals", listOf(Word("Tiger"), Word("Horse"), Word("Koala"), Word("Dog")))
        val countries = Category("Countries", listOf(Word("Turkey"), Word("Denmark"), Word("Kurdistan"), Word("Palestine")))
        val boybands = Category("Boy-Bands", listOf(Word("One Direction"), Word("NSYNC"), Word("Backstreet Boys"), Word("BTS")))
        val instruments = Category("Instruments", listOf(Word("Piano"), Word("Guitar"), Word("Ukulele"), Word("Triangle")))


        val categories = listOf(animals,countries,boybands,instruments)

        for (category in categories){
            categoryData.categories.add(category)
        }
    }
}