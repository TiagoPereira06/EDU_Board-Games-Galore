package edu.isel.pdm.beegeesapp.bgg.favorites.model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.Repository

class MechanicsViewModel(app: BggApplication) : AndroidViewModel(app) {

    private val repo: Repository = app.repo

    val mechanics: MutableLiveData<List<String>> = MutableLiveData()

    fun getMechanics(onFail : () -> Unit) {
        repo.getMechanics( { mechanics.value = it } , { onFail() } )
    }

    fun getListOfMechanics() : List<String> = mechanics.value!!


}
