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
        val programming_languages = Category("Coding", listOf(Word("Java"), Word("Kotlin"), Word("SQL"), Word("Python")))
        val instruments = Category("Instruments", listOf(Word("Piano"), Word("Guitar"), Word("Ukulele"), Word("Triangle")))


        val categories = listOf(animals,countries,programming_languages,instruments)

        for (category in categories){
            categoryData.categories.add(category)
        }
    }
}