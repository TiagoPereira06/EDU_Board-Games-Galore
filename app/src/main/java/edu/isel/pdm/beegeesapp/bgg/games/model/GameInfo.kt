package edu.isel.pdm.beegeesapp.bgg.games.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize
import java.util.Collections.emptyList

@Parcelize
data class GamesMapper(
    val games: List<GameInfo> = emptyList()
) : Parcelable


@Parcelize
data class GameInfo(
    val id: String = "",
    val min_playtime: Int = 0,
    val max_playtime: Int = 0,
    val min_players: Int = 0,
    val max_players: Int = 0,
    val thumb_url: String = "",
    val min_age: Int = 0,
    val num_user_ratings: Int = 0,
    val year_published: Int = 0,
    val name: String = "",
    val description: String = "",
    val publishers: List<String> = emptyList(),
    val artists: List<String> = emptyList(),
    val rules_url: String? = "",
    @JsonProperty("url") val gameUrl: String = "",
    val price: String = "",
    val average_user_rating: Double = 0.0
) : Parcelable {

    constructor(name: String) : this(
        "", 0, 0, 0, 0, "", 0, 0, 0,
        name, "", emptyList(), emptyList(), "", "", "0.00", 0.0
    )
}
