package edu.isel.pdm.beegeesapp.bgg.search.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameInfo(
    val id: Int,
    val minPlayTime: Int,
    val maxPlayTime: Int,
    val minPlayers: Int,
    val maxPlayers: Int,
    val thumb: Int,
    val bigThumb : String,
    val minAge: Int,
    val numberUserReviews: Int,
    val yearPublished: Int,
    val name: String,
    val description: String,
    val publisher: String,
    val developers: String,
    val rulesURL: String,
    val gameURL: String,
    val price: Double,
    val averageUserRating: Double) : Parcelable{
    constructor(name: String) : this(0,0,0,0,0,0,"",0,0,0,
        name,"","","","","",0.0,0.0)
}
