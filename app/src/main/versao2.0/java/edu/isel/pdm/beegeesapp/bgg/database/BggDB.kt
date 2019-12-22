package edu.isel.pdm.beegeesapp.bgg.database

import androidx.room.*
import edu.isel.pdm.beegeesapp.bgg.NotificationSettings
import edu.isel.pdm.beegeesapp.bgg.favorites.model.GameProfile


@Dao
interface CategoriesDAO {

    @Query("SELECT C.name FROM Category AS C")
    fun getAllCategoriesName(): List<String>

    @Query("SELECT C.name FROM Category AS C WHERE id = :categoryId")
    fun findCategoryNameById(categoryId: String): String

    @Insert
    fun insertAll(vararg categories : Category)


}

@Dao
interface MechanicsDAO {

    @Query("SELECT M.name FROM Mechanic AS M")
    fun getAllMechanicsName(): MutableList<String>

    @Query("SELECT M.name FROM Mechanic AS M WHERE id = :mechanicId")
    fun findMechanicNameById(mechanicId: String): String

    @Insert
    fun insertAll(vararg mechanics : Mechanic)
}

@Dao
interface UserListDAO {
    @Query("SELECT * FROM CustomUserList as CU ORDER BY datetime(CU.creationDate)")
    fun getAllLists(): MutableList<CustomUserList>

    @Query("SELECT * FROM CustomUserList AS CU WHERE CU.listName = :listName")
    fun findListByName(listName: String): CustomUserList?

    @Insert
    fun insert(customUserList: CustomUserList)

    @Delete
    fun delete(customUserList: CustomUserList) : Int

    @Update
    fun updateList(customUserList: CustomUserList) : Int

    @Update
    fun updateLists(vararg customUserList: CustomUserList)
}

@Dao
interface GameProfileDAO{
    @Query("DELETE FROM GameProfile")
    fun nukeTable()

    @Query("SELECT * FROM GameProfile AS G WHERE G.name = :profileName")
    fun findProfileByName(profileName : String) : GameProfile?

    @Update
    fun updateGameProfile(gameProfile: GameProfile)

    @Query("SELECT * FROM GameProfile")
    fun getAllGameProfiles() : MutableList<GameProfile>

    @Insert
    fun insertGameProfile(profile: GameProfile)
}

@Dao
interface NotificationSettingsDAO{
    @Query("SELECT * FROM NotificationSettings")
    fun getNotificationSettings() : NotificationSettings?

    @Insert
    fun updateSettings(newSettings: NotificationSettings)

    @Query("DELETE FROM NotificationSettings")
    fun deleteSettings()
}


@Database(
    entities = [
        Mechanic::class,
        Category::class,
        CustomUserList::class,
        GameProfile::class,
        NotificationSettings::class
    ], version = 1
)
@TypeConverters(
    StringConverter::class, MechanicConverter::class, CategoriesConverter::class,
    GameInfoConverter::class)
abstract class BggDataBase : RoomDatabase() {
    abstract fun categoriesDAO(): CategoriesDAO
    abstract fun mechanicsDAO(): MechanicsDAO
    abstract fun userListDAO(): UserListDAO
    abstract fun gameProfileDAO(): GameProfileDAO
    abstract fun notificationSettingsDAO(): NotificationSettingsDAO
}