package edu.isel.pdm.beegeesapp.bgg.database

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*


@Parcelize
data class CategoriesMapper(
    val categories: MutableList<Category>
) : Parcelable

@Parcelize
data class MechanicsMapper(
    val mechanics: MutableList<Mechanic>
) : Parcelable

@Parcelize
@Entity
data class Category(
    @PrimaryKey val id: String,
    val name: String
) : Parcelable

@Parcelize
@Entity
data class Mechanic(
    @PrimaryKey val id: String,
    val name: String
) : Parcelable

@Parcelize
@Entity
data class CustomUserList(
    @PrimaryKey val listName : String,
    var gamesList : MutableList<GameInfo>,
    val drawableResourceName : String,
    val creationDate : String
) : Parcelable{
    constructor(name : String) : this(name, mutableListOf<GameInfo>(),
        genRandomThumbnailImage(), getTime()
    )
}

@SuppressLint("SimpleDateFormat")
fun getTime(): String  = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())

fun genRandomThumbnailImage() : String{
    val prefix = "samplegameicons"
    val randomPos = 1 + (Math.random() * ((33 - 1) + 1)).toInt()
    return prefix+randomPos
}