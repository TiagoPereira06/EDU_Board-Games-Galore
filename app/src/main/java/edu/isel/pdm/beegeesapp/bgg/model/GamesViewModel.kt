package edu.isel.pdm.beegeesapp.bgg.model

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import edu.isel.pdm.beegeesapp.R
import kotlinx.android.parcel.Parcelize

@Parcelize
class GamesViewModel : ViewModel(), Parcelable {

    val games : List<GameInfo> = listOf(
        GameInfo(
            R.drawable.thumb,
            95,
            "Spirit Island",
            "Greater Than Games",
            53.45,
            4.97754
        ),
        GameInfo(
            R.drawable.thumb,
            125,
            "GVgv HUUvwd",
            "Hbbfih DIUHUh IGGIg",
            10.45,
            1.97754
        ),
        GameInfo(
            R.drawable.thumb,
            100,
            "KJBjib IBhgd",
            "Greater Than Games",
            22.45,
            2.97754
        ),
        GameInfo(
            R.drawable.thumb,
            15,
            "SDJHwhd AJdihd",
            "Greater Than Games",
            20.45,
            2.97754
        ),
        GameInfo(
            R.drawable.thumb,
            22,
            "Spirit Island",
            "Greater Than Games",
            41.45,
            1.97754
        ),
        GameInfo(
            R.drawable.thumb,
            105,
            "Spirit Island",
            "Greater Than Games",
            124.45,
            0.97754
        )
    )


}