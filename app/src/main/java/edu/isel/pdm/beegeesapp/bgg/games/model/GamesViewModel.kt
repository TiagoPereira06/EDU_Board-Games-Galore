package edu.isel.pdm.beegeesapp.bgg.games.model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.GamesRepository
import edu.isel.pdm.beegeesapp.bgg.databaseUtils.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.primaryActivities.search.SearchType
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo

/**
 * Initial index, correspondent to card position, to know when to ask for more data
 */
private const val INITIAL_INDEX = 5

/**
 * Flag to check if call to API has already been made or not
 */
private var readyForMoreData: Boolean = true

private var insert = 0

/**
 * Index, correspondent to card position, to know when to ask for more data
 */
private var INDEX_TO_ASK_MORE_DATA = INITIAL_INDEX

class GamesViewModel(
    private val request: RequestInfo,
    val app: BggApplication
) : AndroidViewModel(app) {

    private val repo: GamesRepository = app.repo
    val content: MutableLiveData<MutableList<GameInfo>> = MutableLiveData()

    fun clear() {
        readyForMoreData = true
        INDEX_TO_ASK_MORE_DATA = INITIAL_INDEX
        request.clear()
        content.value?.clear()
    }

    fun getInsertPosition() = insert

    fun getLiveData(): MutableLiveData<MutableList<GameInfo>> = content

    fun getLiveDataSize() = content.value?.size ?: 0

    fun isValidRequest(): Boolean = (request.keyWord != null || request.mode == SearchType.Trending)

    fun checkIfDataIsNeeded(positionReached: Int, onSuccess: () -> Unit) {
        if (readyForMoreData && positionReached >= INDEX_TO_ASK_MORE_DATA) {
            readyForMoreData = false
            INDEX_TO_ASK_MORE_DATA += request.limit
            onSuccess()
        }
    }

    fun getGames(onUpdate: (MutableLiveData<MutableList<GameInfo>>) -> Unit) {
        repo.requestDataFromAPI(request) {
            insert = request.skip
            request.skip += it.games.size
            if (content.value == null) {
                content.value = it.games
            } else {
                content.value!!.addAll(it.games)
                content.value = content.value //trigger observe
            }
            onUpdate(content)
            readyForMoreData = true
        }
    }

    fun getCustomUserListFromRepo(): MutableList<CustomUserList> {
        return repo.getAllCustomUserLists()
    }

    fun addGameToCustomUserList(listNameToAddGame: String, lastGameClicked: GameInfo) {
        repo.addGameToCustomUserList(listNameToAddGame, lastGameClicked)
    }

    fun removeGameFromCustomUserList(listNameToRemoveGame: String, lastGameClicked: GameInfo) {
        repo.removeGameFromCustomUserList(listNameToRemoveGame, lastGameClicked)

    }

}


