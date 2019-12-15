package edu.isel.pdm.beegeesapp.bgg.databaseUtils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.isel.pdm.beegeesapp.bgg.games.model.GameCategories
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.model.GameMechanics
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
        fun stringToMechanicsList(data: String?): MutableList<GameMechanics> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<MutableList<GameMechanics>>() {

            }.type

            return Gson().fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun mechanicsListToString(mechanics: MutableList<GameMechanics>): String {
            return Gson().toJson(mechanics)
        }
    }
}

class CategoriesConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun stringToCategoriesList(data: String?): MutableList<GameCategories> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<MutableList<GameCategories>>() {

            }.type

            return Gson().fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun CategoriesListToString(categories: MutableList<GameCategories>): String {
            return Gson().toJson(categories)
        }
    }
}

class GameInfoConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun stringToGameInfo(data: String?): MutableList<GameInfo> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<MutableList<GameInfo>>() {

            }.type

            return Gson().fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun GameInfoToString(someObjects: MutableList<GameInfo>): String {
            return Gson().toJson(someObjects)
        }
    }
}