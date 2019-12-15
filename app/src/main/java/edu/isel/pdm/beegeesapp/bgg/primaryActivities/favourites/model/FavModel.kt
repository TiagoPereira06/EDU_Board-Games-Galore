package edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.model

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameProfile(
    val iconString: String = "",
    val name : String = "",
    val categoryName : String = "",
    val mechanicName : String = "",
    val publisher : String = "",
    val artist : String = ""
) : Parcelable{constructor( name: String) : this("samplegameicons30",name,
    "CategoryTest","MechanicTest","Ar Games","James Bond") }