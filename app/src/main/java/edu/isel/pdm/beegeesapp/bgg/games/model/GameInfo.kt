package edu.isel.pdm.beegeesapp.bgg.games.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameInfo(
    val id: String,
    val minPlayTime: Int,
    val maxPlayTime: Int,
    val minPlayers: Int,
    val maxPlayers: Int,
    val thumb: String,
    val minAge: Int,
    val numberUserRatings: Int,
    val yearPublished: Int,
    val name: String,
    val description: String,
    val publisher: String,
    val artists: String,
    val rulesURL:String,
    val gameURL: String,
    val price: String,
    val averageUserRating: Double
) : Parcelable {

    constructor(name: String) : this(
        "", 0, 0, 0, 0, "", 0, 0, 0,
        name, "", "", "", "", "", "0.00", 0.0
    )
}
