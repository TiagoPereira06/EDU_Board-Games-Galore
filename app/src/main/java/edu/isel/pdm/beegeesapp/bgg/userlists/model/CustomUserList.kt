package edu.isel.pdm.beegeesapp.bgg.userlists.model

import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo


private fun getName() : String{
     val prefix = "samplegameicons"
     val randomPos = 1 + (Math.random() * ((33 - 1) + 1)).toInt()
    return prefix+randomPos
}


data class CustomUserList(
    val listName : String,
    val list : MutableList<GameInfo>,
    val drawableResourceName : String

){

    constructor(name : String) : this(
        name, mutableListOf<GameInfo>(), getName())




}
