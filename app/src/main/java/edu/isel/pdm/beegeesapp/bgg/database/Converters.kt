package edu.isel.pdm.beegeesapp.bgg.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.isel.pdm.beegeesapp.bgg.games.model.GameCategory
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.model.GameMechanic
import java.util.*

class StringConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun stringToList(data: String?): MutableList<String> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<List<String>>() {

            }.type

            return Gson().fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun listToString(someObjects: MutableList<String>): String {
            return Gson().toJson(someObjects)
        }
    }
}

class MechanicConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun stringToMechanicsList(data: String?): MutableList<GameMechanic> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<MutableList<GameMechanic>>() {

            }.type

            return Gson().fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun mechanicsListToString(mechanics: MutableList<GameMechanic>): String {
            return Gson().toJson(mechanics)
        }
    }
}

class CategoriesConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun stringToCategoriesList(data: String?): MutableList<GameCategory> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<MutableList<GameCategory>>() {

            }.type

            return Gson().fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun categoriesListToString(categories: MutableList<GameCategory>): String {
            return Gson().toJson(categories)
        }
    }
}
class GameInfoConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun stringToGameInfoList(data: String?): MutableList<GameInfo> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<MutableList<GameInfo>>() {

            }.type

            return Gson().fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun gameInfoListToString(someObjects: MutableList<GameInfo>): String {
            return Gson().toJson(someObjects)
        }
    }
}