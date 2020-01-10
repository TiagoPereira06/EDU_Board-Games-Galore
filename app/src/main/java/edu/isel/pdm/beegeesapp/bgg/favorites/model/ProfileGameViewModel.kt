package edu.isel.pdm.beegeesapp.bgg.favorites.model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.GamesInterface
import edu.isel.pdm.beegeesapp.bgg.Repository
import edu.isel.pdm.beegeesapp.bgg.database.GameProfile
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo


class ProfileGameViewModel(val app: BggApplication, private val gameProfile: GameProfile): AndroidViewModel(app),GamesInterface {

    private val repo: Repository = app.repo
     val games: MutableLiveData<MutableList<GameInfo>> = MutableLiveData(gameProfile.gamesList)

    fun updateGames(onFail: () -> Unit, onFinish: () -> Unit) {
        repo.getGames(gameProfile, {
            //On Success
            if (gameProfile.gamesList.size != it.games.size) {
                games.value = it.games
                gameProfile.gamesList = it.games
                repo.updateGameProfile(gameProfile)
            }
            onFinish()
        },
            {
                //On Fail
                onFail()
                onFinish()
            })
    }

    private fun getLiveDataSize() = games.value?.size ?: 0


    override fun getGames(): MutableList<GameInfo> {
        return games.value!!
    }

    override fun getNumberOfGames(): Int {
        return getLiveDataSize()
    }
}