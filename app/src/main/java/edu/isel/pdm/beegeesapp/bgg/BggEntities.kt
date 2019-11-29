package edu.isel.pdm.beegeesapp.bgg

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Categories(
    val id: String
) : Parcelable

@Parcelize
data class Mechanics(
    val id: String
) : Parcelable


@Entity
data class Category(
    @PrimaryKey val id: String,
    val name: String
)

@Entity
data class Mechanic(
    @PrimaryKey val id: String,
    val name: String
)

@Parcelize
@Entity
data class UserList(
    @PrimaryKey val listName : String,
    val gameInfo : MutableList<GameInfo>
) : Parcelable

