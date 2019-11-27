package edu.isel.pdm.beegeesapp.bgg

import androidx.room.*

@Dao
interface GameInfoDAO{
    @Query("Select * FROM Games")
    fun getAll() : MutableList<GameInfo>

}