package edu.isel.pdm.beegeesapp.bgg.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameInfo(val thumbnail: Int, val numberUserReviews: Int, val name: String, val publisher: String, val price: Double, val averageUserRating: Double) : Parcelable