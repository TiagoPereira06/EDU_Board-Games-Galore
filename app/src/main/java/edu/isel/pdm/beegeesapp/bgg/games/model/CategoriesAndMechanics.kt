package edu.isel.pdm.beegeesapp.bgg.games.model

import android.os.Parcelable
import edu.isel.pdm.beegeesapp.bgg.databaseUtils.Category
import edu.isel.pdm.beegeesapp.bgg.databaseUtils.Mechanic
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoriesMapper(
    val categories: MutableList<Category>
) : Parcelable

@Parcelize
data class MechanicsMapper(
    val mechanics: MutableList<Mechanic>
) : Parcelable
