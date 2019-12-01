package edu.isel.pdm.beegeesapp.bgg.games.model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.GamesRepository
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo

/**
 * Initial index, correspondent to card position, to know when to ask for more data
 */
private const val INITIAL_INDEX = 5

/**
 * Flag to check if call to API has already been made or not
 */
private var waitingForData: Boolean = false

/**
 * Index, correspondent to card position, to know when to ask for more data
 */
private var INDEX_TO_ASK_MORE_DATA = INITIAL_INDEX

class GamesViewModel(
    private val request: RequestInfo,
    val app: BggApplication
) : AndroidViewModel(app) {

    private val repo: GamesRepository = app.repo
    private val content: MutableLiveData<MutableList<GameInfo>> = MutableLiveData()


    fun clearSearch() {
        waitingForData = false
        INDEX_TO_ASK_MORE_DATA = INITIAL_INDEX
    }

    fun clear() {
        clearSearch()
        request.clear()
        content.value?.clear()
    }

    fun getFirstInsertPosition() = request.skip - request.limit

    fun getLiveData(): MutableLiveData<MutableList<GameInfo>> = content

    fun getLiveDataSize() = content.value?.size ?: 0

    fun checkIfDataIsNeeded(positionReached: Int, onSuccess: () -> Unit) {
        if (!waitingForData && positionReached >= INDEX_TO_ASK_MORE_DATA) {
            waitingForData = true
            INDEX_TO_ASK_MORE_DATA += request.limit
            onSuccess()
        }
    }

    fun getGames(
        onUpdate: (MutableLiveData<MutableList<GameInfo>>) -> Unit
    ) {
        repo.requestDataFromAPI(request) {
            if (content.value == null) {
                content.value = it.games
            } else {
                content.value!!.addAll(it.games)
            }
            onUpdate(content)
            waitingForData = false
        }
    }

    fun getCustomUserListFromRepo(): MutableList<CustomUserList> {
        return repo.getAllCustomUserLists()
    }

}


