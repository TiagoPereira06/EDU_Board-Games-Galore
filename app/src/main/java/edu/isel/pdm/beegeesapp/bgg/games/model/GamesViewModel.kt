package edu.isel.pdm.beegeesapp.bgg.games.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import edu.isel.pdm.beegeesapp.bgg.request.GetRequest
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import edu.isel.pdm.beegeesapp.bgg.search.Type
import edu.isel.pdm.beegeesapp.kotlinx.CreatorProxy
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.TypeParceler

@Parcelize
@TypeParceler<MutableLiveData<MutableList<GameInfo>>, GamesViewModel.GamesViewModelParcelizer>
class GamesViewModel(
    val content: MutableLiveData<MutableList<GameInfo>> = MutableLiveData()
) : ViewModel(), Parcelable {

    @IgnoredOnParcel
    private val baseURL =
        "https://www.boardgameatlas.com/api/search?"
    @IgnoredOnParcel
    private val clientId = "&client_id=77iiYwBhLL"

    @IgnoredOnParcel
    private val limitUrl = "&limit="

    @IgnoredOnParcel
    private val skipUrl = "&skip="

    @IgnoredOnParcel
    lateinit var currentGamesList: MutableList<GameInfo>

    fun updateGames(app: AppCompatActivity, mode: RequestInfo) {
        val queue = Volley.newRequestQueue(app.application)

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
                currentGamesList = it.games.toMutableList()
                if (mode.skip == mode.limit) { //Activity or GameViewModel ??
                    content.value = currentGamesList
                }
            },
            Response.ErrorListener {
                Toast.makeText(app.applicationContext, "That didn't work!", Toast.LENGTH_SHORT)
                    .show()
            })

        queue.add(request)
    }

    fun updateModel() {
        content.value!!.addAll(currentGamesList)
    }

    object GamesViewModelParcelizer : Parceler<MutableLiveData<MutableList<GameInfo>>> {
        override fun create(parcel: Parcel): MutableLiveData<MutableList<GameInfo>> {
            val contents = mutableListOf<GameInfo>()
            parcel.readTypedList<GameInfo>(contents, CreatorProxy.getChallengeInfoCreator())
            return MutableLiveData(contents)
        }

        override fun MutableLiveData<MutableList<GameInfo>>.write(parcel: Parcel, flags: Int) {
            parcel.writeTypedList(value)
        }
    }

}


