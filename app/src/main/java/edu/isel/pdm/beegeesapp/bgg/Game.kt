package edu.isel.pdm.beegeesapp.bgg

import android.os.Parcel
import android.os.Parcelable

data class Game(
    //val id: Int,
    //val minPlayTime: Int,
    //val maxPlayTime: Int,
    //val minPlayers: Int,
    //val maxPlayers: Int,
    val thumb: Int,
    //val bigThumb : Drawable,
    //val minAge: Int,
    val numberUserReviews: Int,
    //val yearPublished: Int,
    val name: String,
    //val description: String,
    val publisher: String,
    //val developers: String,
    //val rulesURL: String,
    //val gameURL: String,
    val price: Double,
    val averageUserRating: Double
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt(),
        source.readString()!!,
        source.readString()!!,
        source.readDouble(),
        source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(thumb)
        writeInt(numberUserReviews)
        writeString(name)
        writeString(publisher)
        writeDouble(price)
        writeDouble(averageUserRating)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Game> = object : Parcelable.Creator<Game> {
            override fun createFromParcel(source: Parcel): Game =
                Game(source)
            override fun newArray(size: Int): Array<Game?> = arrayOfNulls(size)
        }
    }
}