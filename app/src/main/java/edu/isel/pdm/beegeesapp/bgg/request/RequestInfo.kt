package edu.isel.pdm.beegeesapp.bgg.request

import android.os.Parcelable
import edu.isel.pdm.beegeesapp.bgg.search.Type
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestInfo(var mode: Type, var keyWord: String?) : Parcelable {
    constructor() : this(Type.Trending, null)
}
