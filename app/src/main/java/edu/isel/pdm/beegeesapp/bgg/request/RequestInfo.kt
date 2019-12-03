package edu.isel.pdm.beegeesapp.bgg.request

import android.os.Parcelable
import edu.isel.pdm.beegeesapp.bgg.mainActivities.search.Type
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestInfo(
    var mode: Type,
    var keyWord: String? = null,
    var limit: Int = 10,
    var skip: Int = 0
) : Parcelable {

    fun clear() {
        limit = 10
        skip = 0
    }
}


