package edu.isel.pdm.beegeesapp.bgg

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize
import java.util.Collections.emptyList


@Parcelize
data class GamesMapper(
    val games: MutableList<GameInfo>
) : Parcelable


@Parcelize
@Entity(tableName = "Games")
data class GameInfo(
    @PrimaryKey val id: String = "",
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
    /*@TypeConverters(StringConverter::class)*/val publishers: List<String> = emptyList(),
    /*@TypeConverters(StringConverter::class)*/val artists: List<String> = emptyList(),
    val rules_url: String? = "",
    @JsonProperty("url") val gameUrl: String = "",
    /*@TypeConverters(MechanicConverter::class)*/val mechanics: List<Mechanics> = emptyList(),
    /*@TypeConverters(CategoriesConverter::class)*/val categories: List<Categories> = emptyList(),
    val price: String = "",
    val average_user_rating: Double = 0.0
) : Parcelable{
    constructor(name : String) : this("",0,0,0,0,
        "https://d2k4q26owzy373.cloudfront.net/40x40/games/uploaded/1559254941010-61PJxjjnbfL.jpg",0,
        0,0,name,"","", emptyList(), emptyList(),"","",
        listOf(Mechanics("")), listOf(
            Categories("")
        ),"",0.0)

}

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
    val drawableResourceName : String
) : Parcelable

@Entity(
    indices = arrayOf(Index("categoryId")), tableName = "game_category_join",
    primaryKeys = arrayOf("gameId","categoryId"),
    foreignKeys = arrayOf(
        ForeignKey(entity = GameInfo::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("gameId")),
        ForeignKey(entity = Category::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"))
    )
)

data class GameCategoryJoin(
    val gameId : String,
    val categoryId : String
)

@Entity(
    indices = arrayOf(Index("mechanicId")), tableName = "game_mechanic_join",
    primaryKeys = arrayOf("gameId","mechanicId"),
    foreignKeys = arrayOf(
        ForeignKey(entity = GameInfo::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("gameId")),
        ForeignKey(entity = Mechanic::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("mechanicId"))
    )
)

data class GameMechanicJoin(
    val gameId : String,
    val mechanicId : String
)

@Entity(
    indices = arrayOf(Index("listName")), tableName = "game_userlist_join",
    primaryKeys = arrayOf("gameId","listName"),
    foreignKeys = arrayOf(
        ForeignKey(entity = GameInfo::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("gameId")),
        ForeignKey(entity = UserList::class,
            parentColumns = arrayOf("listName"),
            childColumns = arrayOf("listName"))
    )
)

data class GameUserListJoin(
    val gameId : String,
    val listName : String
)

