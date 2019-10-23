package edu.isel.pdm.beegeesapp.bgg.search.model

import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.toolbox.Volley
import edu.isel.pdm.beegeesapp.kotlinx.CreatorProxy
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.TypeParceler


@Parcelize
@TypeParceler<MutableLiveData<List<GameInfo>>, GamesViewModel.GamesViewModelParcelizer>
class GamesViewModel(val content: MutableLiveData<List<GameInfo>> = MutableLiveData()
) : ViewModel(), Parcelable {


    fun updateGames(app : AppCompatActivity, mode: SearchInfo){
        val mostPopularGamesURL="https://www.boardgameatlas.com/api/search?order_by=popularity&"
        val clientId = "client_id=77iiYwBhLL"
        val queue = Volley.newRequestQueue(app.applicationContext)
        val url = mostPopularGamesURL+clientId



        //TODO:OBTER DADOS API
    }


    object GamesViewModelParcelizer : Parceler<MutableLiveData<List<GameInfo>>>{
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

