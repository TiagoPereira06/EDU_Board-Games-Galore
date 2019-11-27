package edu.isel.pdm.beegeesapp.bgg
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.request.GetRequest
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import edu.isel.pdm.beegeesapp.bgg.search.Type

class GamesRepository(private val host: BggApplication) {

    private val baseURL = "https://www.boardgameatlas.com/api/search?"
    private val clientId = "&client_id=77iiYwBhLL"
    private val limitUrl = "&limit="
    private val skipUrl = "&skip="
    private val queue = Volley.newRequestQueue(host)

    //fun getUserContainer(success : ){}

    fun updateGames (
        mode: RequestInfo,
        success: (MutableList<GameInfo>) -> Unit
    ) {
        val typeURL:String = when(mode.mode){
            Type.Trending -> ("order_by=popularity")
            Type.Artist -> ("artist=" + mode.keyWord)
            Type.Publisher -> ("publisher=" + mode.keyWord)
            Type.Name -> ("name=" + mode.keyWord)
        }
        val url = baseURL + typeURL + skipUrl + mode.skip + limitUrl + mode.limit + clientId

        mode.skip = mode.skip + mode.limit

        val request = GetRequest(
            url,
            Response.Listener {
                success(it.games)
            },
            Response.ErrorListener {
                Toast.makeText(host, "That didn't work!", Toast.LENGTH_SHORT)
                    .show()
            })

        queue.add(request)
    }



}