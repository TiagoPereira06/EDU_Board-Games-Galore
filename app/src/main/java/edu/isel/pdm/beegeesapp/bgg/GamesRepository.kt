package edu.isel.pdm.beegeesapp.bgg
import android.widget.Toast
import androidx.room.Room
import com.android.volley.Response
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesMapper
import edu.isel.pdm.beegeesapp.bgg.request.GetRequest
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import edu.isel.pdm.beegeesapp.bgg.search.Type

class GamesRepository(private val host: BggApplication) {


    private val db = Room
        .databaseBuilder(host, BggDataBase::class.java, "games-db")
        .allowMainThreadQueries()
        .build()

    private val baseURL = "https://www.boardgameatlas.com/api/search?"
    private val clientId = "&client_id=77iiYwBhLL"
    private val limitUrl = "&limit="
    private val skipUrl = "&skip="

    fun requestDataFromAPI(
        mode: RequestInfo,
        success: (GamesMapper) -> Unit
    ) {
        val typeURL:String = when(mode.mode){
            Type.Trending -> ("order_by=popularity")
            Type.Artist -> ("artist=" + mode.keyWord)
            Type.Publisher -> ("publisher=" + mode.keyWord)
            Type.Name -> ("name=" + mode.keyWord)
        }
        val url = baseURL + typeURL + skipUrl + mode.skip + limitUrl + mode.limit + clientId

        val request = GetRequest(
            url,
            Response.Listener {
                mode.skip = mode.skip + mode.limit
                success(it)
            },
            Response.ErrorListener {
                Toast.makeText(host, "That didn't work!", Toast.LENGTH_SHORT)
                    .show()
            })
        host.queue.add(request)
    }

    fun addCustomUserList(newListName :String) : Boolean{
        if (db.userListDAO().findListByName(newListName) == null) {
            db.userListDAO().insertList(CustomUserList(newListName))
            return true
        }
        return false
    }

    fun getCustomUserList(name : String) : MutableList<GameInfo>{
        return db.userListDAO().findListByName(name)!!.gamesList

    }

    fun clearAllCustomUserLists(){
        db.userListDAO().nukeTable()
    }

    fun getAllCustomUserLists(): MutableList<CustomUserList> {
        return db.userListDAO().getAllLists()

    }

    fun addGameToCustomUserList(listName: String, currentGame: GameInfo) {
        val list = db.userListDAO().findListByName(listName)
        list?.gamesList?.add(currentGame)
        if (list != null) {
            db.userListDAO().updateList(list)
        }


    }

    fun removeGameFromCustomUserList(listName: String, currentGame: GameInfo) {
        val list = db.userListDAO().findListByName(listName)
        list?.gamesList?.remove(currentGame)
        if (list != null) {
            db.userListDAO().updateList(list)
        }

    }

    fun removeCustomUserListAt(position: Int) {
       val allList = db.userListDAO().getAllLists()
        allList.removeAt(position)
        db.userListDAO().nukeTable()
        allList.forEach { db.userListDAO().insertList(it) }
    }

    fun addCustomUserListAt(userList: CustomUserList, position: Int) {
        val allList = db.userListDAO().getAllLists()
        allList.add(position,userList)
        db.userListDAO().nukeTable()
        allList.forEach { db.userListDAO().insertList(it) }
    }

}