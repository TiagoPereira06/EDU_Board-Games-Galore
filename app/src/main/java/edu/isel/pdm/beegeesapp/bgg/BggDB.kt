package edu.isel.pdm.beegeesapp.bgg

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.RoomDatabase

@Dao
interface GameInfoDAO{
    @Query("Select * FROM Games")
    fun getAll() : MutableList<GameInfo>

}

@Database(
    entities = [
        GameInfo::class,
        Mechanic::class,
        Category::class,
        UserList::class,
        GameUserListJoin::class,
        GameMechanicJoin::class,
        GameCategoryJoin::class
    ], version = 1
)
abstract class BggDataBase : RoomDatabase() {
    abstract fun gameInfoDAO(): GameInfoDAO
}