package edu.isel.pdm.beegeesapp.bgg

import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo

interface GamesInterface {
    fun getGames() : MutableList<GameInfo>
    fun getNumberOfGames() : Int
}