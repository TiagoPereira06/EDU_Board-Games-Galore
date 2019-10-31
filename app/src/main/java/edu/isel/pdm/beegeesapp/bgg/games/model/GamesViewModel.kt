package edu.isel.pdm.beegeesapp.bgg.games.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import edu.isel.pdm.beegeesapp.bgg.search.Type.*
import edu.isel.pdm.beegeesapp.bgg.search.model.SearchInfo
import edu.isel.pdm.beegeesapp.kotlinx.CreatorProxy
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.TypeParceler
import org.json.JSONObject


@Parcelize
@TypeParceler<MutableLiveData<List<GameInfo>>, GamesViewModel.GamesViewModelParcelizer>
class GamesViewModel(
     val content: MutableLiveData<List<GameInfo>> = MutableLiveData()
) : ViewModel(), Parcelable {
   /* init {
        content.value = mutableListOf<GameInfo>()
    }*/
    @IgnoredOnParcel
    private val mostPopularGamesURL =
        "https://www.boardgameatlas.com/api/search?order_by=popularity&"
    @IgnoredOnParcel
    private val clientId = "client_id=77iiYwBhLL"
    @IgnoredOnParcel
    private val url = mostPopularGamesURL + clientId

    fun updateGames(app: AppCompatActivity, mode: SearchInfo) {
        val queue = Volley.newRequestQueue(app.applicationContext)
        //TODO : IMPLEMENTAR MODO DE PESQUISA


        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                val games = response
                    .getJSONArray("games")

                //val list = getListOfItemsToSearch()

                val list: MutableList<GameInfo> = mutableListOf()

                for (i in 0 until games.length()) {
                    val game = games.getJSONObject(i)
                    // TODO -> DINAMIZAR ! -> LAMBDAS
                    list.add(
                        GameInfo(
                            game.getString("id"),
                            game.optInt("min_playtime", 0),
                            game.optInt("max_playtime", 0),
                            game.optInt("min_players", 0),
                            game.optInt("max_players", 0),
                            game.optString("thumb_url", ""),
                            game.optInt("min_age", 0),
                            game.optInt("num_user_ratings", 0),
                            game.optInt("year_published", 0),
                            game.optString("name", "Name not found."),
                            game.optString("description", "Description not available."),
                            game.getJSONArray("publishers").optString(0, ""),
                            game.getJSONArray("artists").optString  (0, ""),
                            game.optString("rules_url", ""),
                            game.getString("url"),
                            game.optString("price", "0.00"),
                            game.getDouble("average_user_rating")
                        )
                    )
                }

                this.content.value = list
                Toast.makeText(app.applicationContext, "List Finished", Toast.LENGTH_SHORT).show()

            },
            Response.ErrorListener {
                Toast.makeText(app.applicationContext, "That didn't work!", Toast.LENGTH_SHORT)
                    .show()
            })

        queue.add(request)
        queue.start()
    }

    private fun getListOfItemsToSearch() = listOf<(JSONObject) -> Any>(
        { jsonObject -> jsonObject.getString("id") },
        { jsonObject -> jsonObject.getInt("min_playtime") },
        { jsonObject -> jsonObject.getInt("max_playTime") },
        { jsonObject -> jsonObject.getInt("min_players") },
        { jsonObject -> jsonObject.getInt("max_players") },
        { jsonObject -> jsonObject.getString("thumb_url") },
        { jsonObject -> jsonObject.getString("bigThumb") },
        { jsonObject -> jsonObject.getInt("min_age") },
        { jsonObject -> jsonObject.getInt("num_user_ratings") },
        { jsonObject -> jsonObject.getInt("year_published") },
        { jsonObject -> jsonObject.getString("name") },
        { jsonObject -> jsonObject.getString("description") },
        { jsonObject -> jsonObject.getString("publisher") },
        { jsonObject -> jsonObject.getString("artists") },
        { jsonObject -> jsonObject.getString("rules_url") },
        { jsonObject -> jsonObject.getString("gameURL") },
        { jsonObject -> jsonObject.getString("price") },
        { jsonObject -> jsonObject.getDouble("average_user_rating") }
    )

    object GamesViewModelParcelizer : Parceler<MutableLiveData<List<GameInfo>>> {
        override fun create(parcel: Parcel): MutableLiveData<List<GameInfo>> {
            val contents = mutableListOf<GameInfo>()
            parcel.readTypedList<GameInfo>(contents, CreatorProxy.getChallengeInfoCreator())
            return MutableLiveData(contents)
        }

        override fun MutableLiveData<List<GameInfo>>.write(parcel: Parcel, flags: Int) {
            parcel.writeTypedList(value)
        }
    }

}


