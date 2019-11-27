package edu.isel.pdm.beegeesapp.bgg.userlists.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.TypeConverter
import edu.isel.pdm.beegeesapp.bgg.GameInfo
import kotlinx.android.parcel.Parcelize


private fun genRandomThumbnailImage() : String{
     val prefix = "samplegameicons"
     val randomPos = 1 + (Math.random() * ((33 - 1) + 1)).toInt()
    return prefix+randomPos
}


@Parcelize
data class CustomUserList(
    val listName : String,
    var list : MutableList<GameInfo>,
    val drawableResourceName : String
) : Parcelable {

    constructor(name : String) : this(
        name, mutableListOf<GameInfo>(), genRandomThumbnailImage())




}
