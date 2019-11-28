package edu.isel.pdm.beegeesapp.bgg

import androidx.room.*

@Dao
interface GameInfoDAO {

    @Query("SELECT * FROM Games")
    fun getAllGames(): MutableList<GameInfo>

    @Query("SELECT * FROM Games WHERE id = :gameId")
    fun findGameById(gameId: Int): GameInfo
}

@Dao
interface CategoriesDAO {

    @Query("SELECT * FROM Category")
    fun getAllCategories(): List<Category>

    @Query("SELECT C.name FROM Category AS C WHERE id = :categoryId")
    fun findCategoryNameById(categoryId: String): String
}

@Dao
interface MechanicsDAO {

    @Query("SELECT * FROM Mechanic")
    fun getAllMechanics(): List<Mechanic>

    @Query("SELECT M.name FROM Mechanic AS M WHERE id = :mechanicId")
    fun findMechanicNameById(mechanicId: String): String
}

@Dao
interface UserListDAO {

    @Query("SELECT * FROM UserList")
    fun getAllLists(): List<UserList>

    @Query("SELECT * FROM UserList AS U WHERE U.listName = :listName")
    fun findListByName(listName: String): UserList
}

@Dao
interface GameCategoryDAO {

    @Query("SELECT GC.gameId FROM game_category_join AS GC WHERE GC.categoryId = :categoryId")
    fun findGamesIdsByCategoryiD(categoryId: String): List<String>

    @Query("SELECT GC.categoryId FROM game_category_join AS GC WHERE GC.gameId = :gameId")
    fun findCategoryIdByGameId(gameId: String): List<String>

    @Query("SELECT * FROM game_category_join AS GC WHERE GC.categoryId = :categoryId")
    fun findGamesCategoriesByCategoryId(categoryId: String): List<GameCategoryJoin>

    @Query("SELECT * FROM game_category_join AS GC WHERE GC.gameId = :gameId")
    fun findGamesCategoriesByGameId(gameId: String): List<GameCategoryJoin>
}

@Dao
interface GameMechanicDAO {

    @Query("SELECT GM.gameId FROM game_mechanic_join AS GM WHERE GM.mechanicId = :mechanicId")
    fun findGamesIdsByMechanicID(mechanicId: String): List<String>

    @Query("SELECT GM.mechanicId FROM game_mechanic_join AS GM WHERE GM.gameId = :gameId")
    fun findMechanicIdByGameId(gameId: String): List<String>

    @Query("SELECT * FROM game_mechanic_join AS GM WHERE GM.mechanicId = :mechanicId")
    fun findGamesMechanicsByMechanicId(mechanicId: String): List<GameMechanicJoin>

    @Query("SELECT * FROM game_mechanic_join AS GM WHERE GM.gameId = :gameId")
    fun findGamesMechanicsByGameId(gameId: String): List<GameMechanicJoin>
}


@Dao
interface GameUserListDAO {

    @Query("SELECT GUL.gameId FROM game_userlist_join AS GUL WHERE GUL.listName = :listName")
    fun findGamesIdsByListName(listName: String): List<String>

    @Query("SELECT GUL.listName FROM game_userlist_join AS GUL WHERE GUL.gameId = :gameId")
    fun findListNamesByGameId(gameId: String): List<String>

    @Query("SELECT * FROM game_userlist_join AS GM WHERE GM.gameId = :gameId")
    fun findGamesUserListsByGameId(gameId: String): List<GameUserListJoin>

    @Query("SELECT * FROM game_userlist_join AS GM WHERE GM.listName = :listName")
    fun findGamesUserListsByListName(listName: String): List<GameUserListJoin>
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
@TypeConverters(StringConverter::class, MechanicConverter::class, CategoriesConverter::class)
abstract class BggDataBase : RoomDatabase() {
    abstract fun gameInfoDAO(): GameInfoDAO
    abstract fun categoriesDAO(): CategoriesDAO
    abstract fun mechanicsDAO(): MechanicsDAO
    abstract fun userListDAO(): UserListDAO
    abstract fun gameCategoryDAO(): GameCategoryDAO
    abstract fun gameMechanicDAO(): GameMechanicDAO
    abstract fun gameUserListDAO(): GameUserListDAO

}