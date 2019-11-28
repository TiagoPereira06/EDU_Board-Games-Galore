package edu.isel.pdm.beegeesapp.bgg

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class StringConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun stringToList(data: String?): List<String> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<List<String>>() {

            }.type

            return Gson().fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun ListToString(someObjects: List<String>): String {
            return Gson().toJson(someObjects)
        }
    }
}

class MechanicConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun stringToMechanicsList(data: String?): List<Mechanics> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<List<Mechanics>>() {

            }.type

            return Gson().fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun mechanicsListToString(mechanics: List<Mechanics>): String {
            return Gson().toJson(mechanics)
        }
    }
}

class CategoriesConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun stringToCategoriesList(data: String?): List<Categories> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<List<Categories>>() {

            }.type

            return Gson().fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun CategoriesListToString(categories: List<Categories>): String {
            return Gson().toJson(categories)
        }
    }
}