package edu.isel.pdm.beegeesapp.bgg.games.model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.GamesInterface
import edu.isel.pdm.beegeesapp.bgg.Repository
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo

/**
 * Initial index, correspondent to card position, to know when to ask for more data
 */
private const val INITIAL_INDEX = 5

/**
 * Flag to check if call to API has already been made or not
 */
private var readyForMoreData = true

private var insert = 0

/**
 * Index, correspondent to card position, to know when to ask for more data
 */
private var INDEX_TO_ASK_MORE_DATA = INITIAL_INDEX

class GamesViewModel(val app: BggApplication) : AndroidViewModel(app), GamesInterface {

    lateinit var request : RequestInfo

    private val repo: Repository = app.repo

    val games : MutableLiveData<MutableList<GameInfo>> = MutableLiveData()

    fun init(req : RequestInfo): GamesViewModel {
        request = req
        return this
    }

    fun clear() {
        clearSearch()
        request.clear()
        games.value?.clear()
    }

    fun clearSearch() {
        readyForMoreData = true
        INDEX_TO_ASK_MORE_DATA = INITIAL_INDEX
    }

    fun getInsertPosition() = insert

    fun getLiveData(): MutableLiveData<MutableList<GameInfo>> = games

    fun getLiveDataSize() = games.value?.size ?: 0

    fun isValidRequest(): Boolean = (request.keyWord != null || request.searchType == SearchType.Trending)

    fun checkIfDataIsNeeded(positionReached: Int, onSuccess: () -> Unit) {
        if (readyForMoreData && positionReached >= INDEX_TO_ASK_MORE_DATA) {
            readyForMoreData = false
            INDEX_TO_ASK_MORE_DATA += request.limit
            onSuccess()
        }
    }

    fun getGames(onFail : () -> Unit) {
        repo.getGames(request, {
            //On Success
            insert = request.skip
            request.skip += it.games.size

            if (games.value == null) {
                games.value = it.games
            } else {
                games.value!!.addAll(it.games)
                games.value = games.value //trigger observe
            }
            readyForMoreData = true
        },
            {
            //On Fail
            onFail()
        })
    }

    override fun getGames(): MutableList<GameInfo> {
        return games.value!!
    }

    override fun getNumberOfGames(): Int {
        return getLiveDataSize()
    }

}


