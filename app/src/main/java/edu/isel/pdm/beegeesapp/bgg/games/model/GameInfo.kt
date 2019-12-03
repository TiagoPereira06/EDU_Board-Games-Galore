package edu.isel.pdm.beegeesapp.bgg.games.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import edu.isel.pdm.beegeesapp.bgg.databaseUtils.Categories
import edu.isel.pdm.beegeesapp.bgg.databaseUtils.Mechanics
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class GamesMapper(
    val games: MutableList<GameInfo>
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
    val primary_publisher: String? = "",
    val publishers: List<String> = Collections.emptyList(),
    val artists: List<String> = Collections.emptyList(),
    val rules_url: String? = "",
    @JsonProperty("url") val gameUrl: String = "",
    val mechanics: List<Mechanics> = Collections.emptyList(),
    val categories: List<Categories> = Collections.emptyList(),
    val price: String = "",
    val average_user_rating: Double = 0.0
) : Parcelable {
    constructor(name : String) : this("",0,0,0,0,
        "https://d2k4q26owzy373.cloudfront.net/40x40/games/uploaded/1559254941010-61PJxjjnbfL.jpg",0,
        0,0,name,"","", Collections.emptyList(), Collections.emptyList(),"","",
        listOf(Mechanics("")), listOf(
            Categories("")
        ),"",0.0)

}