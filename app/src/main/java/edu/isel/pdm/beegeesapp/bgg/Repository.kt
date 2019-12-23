package edu.isel.pdm.beegeesapp.bgg

import android.util.Log
import androidx.room.Room
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.database.BggDataBase
import edu.isel.pdm.beegeesapp.bgg.database.Category
import edu.isel.pdm.beegeesapp.bgg.database.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.database.Mechanic
import edu.isel.pdm.beegeesapp.bgg.favorites.model.GameProfile
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesMapper
import edu.isel.pdm.beegeesapp.bgg.games.model.SearchType
import edu.isel.pdm.beegeesapp.bgg.request.GetCategoriesRequest
import edu.isel.pdm.beegeesapp.bgg.request.GetMechanicsRequest
import edu.isel.pdm.beegeesapp.bgg.request.GetRequest
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import edu.isel.pdm.beegeesapp.kotlinx.runAsync

class Repository(private val host: BggApplication) {

    private val db = Room
        .databaseBuilder(host, BggDataBase::class.java, "games-db")
        .allowMainThreadQueries()
        .build()

    private val baseUrl = "https://www.boardgameatlas.com/api"
    private val searchUrl = "/search?"
    private val categoriesUrl = "/game/categories?pretty=true"
    private val trending = "order_by=popularity"
    private val mechanicsUrl = "/game/mechanics?pretty=true"
    private val clientId = "&client_id=77iiYwBhLL"
    private val limitUrl = "&limit="
    private val skipUrl = "&skip="

    fun getGames(
        mode: RequestInfo,
        onSuccess: (GamesMapper) -> Unit,
        onFail: () -> Unit
        ) {
        val typeURL : String = when (mode.searchType) {
            SearchType.Trending -> (trending)
            SearchType.Artist -> ("artist=" + mode.keyWord)
            SearchType.Publisher -> ("publisher=" + mode.keyWord)
            SearchType.Name -> ("name=" + mode.keyWord)
        }

        val url =
            baseUrl + searchUrl + typeURL + skipUrl + mode.skip + limitUrl + mode.limit + clientId

        val request = GetRequest(
            url,
            Response.Listener {
                onSuccess(it)
            },
            Response.ErrorListener {
                onFail()
            })
        host.queue.add(request)
    }

    private fun requestMechanicsFromApi(onSuccess: (MutableList<Mechanic>) -> Unit, onFail: () -> Unit) {
        val url = baseUrl + mechanicsUrl + clientId

        val request = GetMechanicsRequest(url,
            Response.Listener {
                onSuccess(it.mechanics)
            },
            Response.ErrorListener {
                onFail()
            })

        host.queue.add(request)
    }


    private fun requestCategoriesFromApi(onSuccess: (MutableList<Category>) -> Unit, onFail: () -> Unit) {
        val url = baseUrl + categoriesUrl + clientId

        val request = GetCategoriesRequest(url,
            Response.Listener {
                onSuccess(it.categories)
            },
            Response.ErrorListener {
                onFail()
            })

        host.queue.add(request)
    }

    fun addCustomUserListByName(newListName: String, onSuccess : (CustomUserList) -> Unit, onFail : () -> Unit) {
        runAsync {
            db.userListDAO().findListByName(newListName)
        }.andThen {
            if (it == null) {
                val list = CustomUserList(newListName)
                runAsync {
                    db.userListDAO().insert(list)
                }.andThen {
                    onSuccess(list)
                }
            } else onFail()
        }
    }

    fun addCustomUserList(newList: CustomUserList, onInsert: () -> Unit) {
        runAsync {
            db.userListDAO().insert(newList)
        }.andThen { onInsert() }
    }

    fun removeCustomUserList(userList : CustomUserList, onSuccess : () -> Unit, onFail : () -> Unit) {
        runAsync {
            db.userListDAO().delete(userList)
        }.andThen { rowsAffected ->
            if (rowsAffected == 1) {
                onSuccess()
            }
            else {
                onFail()
            }
        }
    }

    fun getAllCustomUserLists(success : (MutableList<CustomUserList>) -> Unit) {
        runAsync {
            db.userListDAO().getAllLists()
        }.andThen {
            success(it)
        }
    }

    fun updateCustomUserList(listToUpdate: CustomUserList, onFinish: () -> Unit) {
        runAsync {
            db.userListDAO().updateList(listToUpdate)
        }.andThen { onFinish() }
    }

    fun updateCustomUserLists(lists : List<CustomUserList>, onFinish: () -> Unit) {
        runAsync {
            db.userListDAO().updateLists(*lists.toTypedArray())
        }.andThen {
            onFinish()
        }
    }

    fun getMechanics(success: (List<String>) -> Unit, onFail: () -> Unit) {
        runAsync {
            db.mechanicsDAO().getAllMechanicsName()
        }.andThen { mechanicsFromDb ->
            if (mechanicsFromDb.isEmpty()) {
                requestMechanicsFromApi({
                    val mechanics = it.map { mechanic -> mechanic.name }
                    runAsync {
                        db.mechanicsDAO().insertAll(*it.toTypedArray())
                    }.andThen {
                        success(mechanics)
                    }
                },
                    {
                        onFail()
                    })
            } else {
                success(mechanicsFromDb)
            }
        }
    }

    fun getCategories(success: (List<String>) -> Unit, onFail: () -> Unit) {
        runAsync {
            db.categoriesDAO().getAllCategoriesName()
        }.andThen { categoriesFromDb ->
            if (categoriesFromDb.isEmpty()) {
                requestCategoriesFromApi({
                    val categories = it.map { cat -> cat.name }
                    runAsync {
                        db.categoriesDAO().insertAll(*it.toTypedArray())
                    }.andThen {
                        success(categories)
                    }
                },
                    {
                        onFail()
                    })
            } else {
                success(categoriesFromDb)
            }
        }
    }

    fun getGameProfiles(success : (MutableList<GameProfile>) -> Unit ) {
        runAsync {
            db.gameProfileDAO().getAllGameProfiles()
        }.andThen {
            success(it)
        }
    }

    fun insertGameProfile(gameProfile : GameProfile, onSuccess: () -> Unit, onFail: () -> Unit) {
        runAsync {
            db.gameProfileDAO().findProfileByName(gameProfile.name)
        }.andThen {
            if (it == null) {
                runAsync {
                    db.gameProfileDAO().insertGameProfile(gameProfile)
                }.andThen {
                    onSuccess()
                }
            }
            else onFail()
        }
    }

    fun syncGetProfiles(): MutableList<GameProfile> {
        return db.gameProfileDAO().getAllGameProfiles()
    }

    fun getGameProfileChanges(gameProfile: GameProfile): MutableList<GameInfo> {
        val future: RequestFuture<GamesMapper> = RequestFuture.newFuture()
        var searchQuery = "&"
        if (gameProfile.designer != "") searchQuery += "designer=$gameProfile.designer&"
        if (gameProfile.categoryName != "") searchQuery += "categories=${getCategoryId(gameProfile.categoryName)}&"
        if (gameProfile.mechanicName != "") searchQuery += "mechanics=${getMechanicId(gameProfile.mechanicName)}&"
        if (gameProfile.publisher != "") searchQuery += "publisher=${gameProfile.publisher}&"

        val url = baseUrl + searchUrl + trending
        val query = url + searchQuery.substring(0, searchQuery.length - 1) + clientId
        Log.v("DEBUG", query)
        host.queue.add(GetRequest(query, future, future))
        return future.get().games
    }

    private fun getMechanicId(mechanicName: String): String {
        return db.mechanicsDAO().findMechanicIdByName(mechanicName)
    }

    private fun getCategoryId(categoryName: String): String {
        return db.categoriesDAO().findCategoryIdByName(categoryName)
    }

    fun updateGameProfile(gameProfile: GameProfile) {
        db.gameProfileDAO().updateGameProfile(gameProfile)
    }
}

    /*
    fun getCurrentNotificationSettings(): NotificationSettings? {
        return db.
    }


    fun updateCurrentNotificationSettings(newSettings: NotificationSettings){
        db.notificationSettingsDAO().deleteSettings()
        db.notificationSettingsDAO().updateSettings(newSettings)
    }

    fun initSettingsConfig() {
        if(db.notificationSettingsDAO().getNotificationSettings().isEmpty()){
            db.notificationSettingsDAO().updateSettings(NotificationSettings(1,0,1))
        }
    }

     */

