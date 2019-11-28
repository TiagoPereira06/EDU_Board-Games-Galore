package edu.isel.pdm.beegeesapp.bgg.request

import android.os.Parcelable
import edu.isel.pdm.beegeesapp.bgg.search.Type
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestInfo(
    var mode: Type,
    var keyWord: String?,
    var limit: Int = 10,
    var skip: Int = 0
) : Parcelable {
    //TODO - tirar o Trending do construtor..
    constructor() : this(Type.Trending, null, 10, 0)

    fun clear() {
        limit = 10
        skip = 0
    }
}


