package edu.isel.pdm.beegeesapp.bgg.favorites.model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.Repository

class FavoritesViewModel(
    val app: BggApplication
) : AndroidViewModel(app) {
    private val repo: Repository = app.repo

    val favorites : MutableLiveData<MutableList<GameProfile>> = MutableLiveData()

    val categories: MutableLiveData<List<String>> = MutableLiveData()

    val mechanics: MutableLiveData<List<String>> = MutableLiveData()


    fun getListOfMechanics() : List<String> = mechanics.value!!

    fun getFavorites() {
        repo.getGameProfiles {
            favorites.value = it
        }
    }

    fun getCategories(onFail : () -> Unit) {
        repo.getCategories( {
            categories.value = it
        },{
            onFail()
        })
    }


    fun getMechanics(onFail : () -> Unit) {
        repo.getMechanics( { mechanics.value = it },{ onFail() })
    }


    fun addNewGameProfile(newGameProfile: GameProfile, onSuccess : () -> Unit, onFail: () -> Unit) {
        repo.insertGameProfile( newGameProfile,
            {
            favorites.value!!.add(newGameProfile)
            onSuccess()
            },
            { onFail() } )
    }
}