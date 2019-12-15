package edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.isel.pdm.beegeesapp.bgg.databaseUtils.genRandomThumbnailImage
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
    val artist : String = "",
    val gamesList : MutableList<GameInfo> = mutableListOf()
) : Parcelable{constructor(name: String) : this(
    genRandomThumbnailImage(),name,
    "CategoryTest","MechanicTest","Ar Games","James Bond") }