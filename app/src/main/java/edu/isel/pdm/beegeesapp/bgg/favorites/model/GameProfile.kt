package edu.isel.pdm.beegeesapp.bgg.favorites.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.isel.pdm.beegeesapp.bgg.GamesInterface
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo

import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class GameProfile(
    val drawableResourceName: String = "",
    @PrimaryKey val name : String = "",
    val categoryName : String = "",
    val mechanicName : String = "",
    val publisher : String = "",
    val designer : String = "",
    var gamesList : MutableList<GameInfo> = mutableListOf()
) : Parcelable, GamesInterface {

    override fun getGames(): MutableList<GameInfo> {
        return gamesList
    }

    override fun getNumberOfGames(): Int {
        return gamesList.size
    }


}