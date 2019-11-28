package edu.isel.pdm.beegeesapp.bgg.games.model

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.GameInfo
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import edu.isel.pdm.beegeesapp.kotlinx.CreatorProxy
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.TypeParceler

@Parcelize
@TypeParceler<MutableLiveData<MutableList<GameInfo>>, GamesViewModel.GamesViewModelParcelizer>
class GamesViewModel(
    val content: MutableLiveData<MutableList<GameInfo>> = MutableLiveData()
) : ViewModel(), Parcelable {

    fun getLiveData(): MutableLiveData<MutableList<GameInfo>> = content

    fun getGames(
        app: BggApplication,
        mode: RequestInfo,
        onUpdate: (MutableLiveData<MutableList<GameInfo>>) -> Unit
    ) {
        app.repo.requestDataFromAPI(mode) {
            if (content.value == null) {
                content.value = it
            } else {
                content.value!!.addAll(it)
            }
            onUpdate(content)
        }
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


