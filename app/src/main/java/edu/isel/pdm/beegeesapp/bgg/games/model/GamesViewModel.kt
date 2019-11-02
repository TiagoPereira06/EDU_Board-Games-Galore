package edu.isel.pdm.beegeesapp.bgg.games.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import edu.isel.pdm.beegeesapp.bgg.Request.GetRequest
import edu.isel.pdm.beegeesapp.bgg.search.model.SearchInfo
import edu.isel.pdm.beegeesapp.kotlinx.CreatorProxy
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.TypeParceler

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

        val request = GetRequest(
            url,
            Response.Listener {
                Log.v("BeeGeesApp", "Success handler on thread ${Thread.currentThread().name}")
                content.value = it.games
            },
            Response.ErrorListener {
                Toast.makeText(app.applicationContext, "That didn't work!", Toast.LENGTH_SHORT)
                    .show()
            })

        queue.add(request)
        queue.start()
    }

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


