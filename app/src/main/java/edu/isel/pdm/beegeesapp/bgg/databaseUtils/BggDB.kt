package edu.isel.pdm.beegeesapp.bgg.databaseUtils

import androidx.room.*
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.NotificationSettings
import edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.model.GameProfile

@Dao
interface CategoriesDAO {

    @Query("SELECT * FROM Category")
    fun getAllCategories(): List<Category>

    @Query("SELECT C.name FROM Category AS C WHERE id = :categoryId")
    fun findCategoryNameById(categoryId: String): String

    @Insert
    fun insertCategory(category: Category)


}

@Dao
interface MechanicsDAO {

    @Query("SELECT * FROM Mechanic")
    fun getAllMechanics(): MutableList<Mechanic>

    @Query("SELECT M.name FROM Mechanic AS M WHERE id = :mechanicId")
    fun findMechanicNameById(mechanicId: String): String
}

@Dao
interface UserListDAO {

    @Query("DELETE FROM CustomUserList")
    fun nukeTable()

    @Query("SELECT * FROM CustomUserList")
    fun getAllLists(): MutableList<CustomUserList>

    @Query("SELECT * FROM CustomUserList AS U WHERE U.listName = :listName")
    fun findListByName(listName: String): CustomUserList?

    @Insert
    fun insertList(customUserList: CustomUserList)

    @Update
    fun updateList(customUserList: CustomUserList)

}

@Dao
interface GameProfileDAO{
    @Query("DELETE FROM GameProfile")
    fun nukeTable()

    @Query("SELECT * FROM GameProfile")
    fun getAllGameProfiles() : MutableList<GameProfile>

    @Insert
    fun insertGameProfile(profile: GameProfile)
}

@Dao
interface NotificationSettingsDAO{
    @Query("SELECT * FROM NotificationSettings")
    fun getNotificationSetings() : List<NotificationSettings>
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