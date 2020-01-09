package edu.isel.pdm.beegeesapp.bgg.favorites.model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.Repository
import edu.isel.pdm.beegeesapp.bgg.favorites.ui.NotificationSettings

class FavoritesViewModel(
    val app: BggApplication
) : AndroidViewModel(app) {

    private val repo: Repository = app.repo

    var cat: CharSequence? = null
    var mec: CharSequence? = null


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

    fun getNotificationSettings(): NotificationSettings? {
        val settings = repo.getCurrentNotificationSettings()
        if(settings==null){
            repo.initSettingsConfig()
            return repo.getCurrentNotificationSettings()
        }
        return settings
    }

    fun saveSpinnersSelection(freq: Int, bat: Int, met: Int) {
        repo.updateCurrentNotificationSettings(NotificationSettings(freq,bat,met))
    }

    fun addNewGameProfile(newGameProfile: GameProfile, onSuccess : () -> Unit, onFail: () -> Unit) {
        repo.insertGameProfile( newGameProfile,
            {
                favorites.value!!.add(newGameProfile)
                onSuccess()
            },
            { onFail() } )
    }

    fun removeGameProfile(gameProfile : GameProfile, onSuccess: () -> Unit, onFail: () -> Unit) {
        repo.removeGameProfile(
            gameProfile,
            {
                favorites.value?.remove(gameProfile)
                onSuccess()
            },
            onFail
        )
    }

    fun checkConstraints(
        profileName: String,
        categoryName: String,
        mechanicName: String
    ): Boolean {
        return (profileName.isNotEmpty() && (categoryName.isNotEmpty() || mechanicName.isNotEmpty()))
    }

    fun addGameProfile(profileRemoved: GameProfile, oldPosition: Int, onSuccess: () -> Unit) {
        repo.insertGameProfile(profileRemoved, onSuccess) {
            favorites.value?.add(oldPosition, profileRemoved)
            onSuccess()
        }

    }
}