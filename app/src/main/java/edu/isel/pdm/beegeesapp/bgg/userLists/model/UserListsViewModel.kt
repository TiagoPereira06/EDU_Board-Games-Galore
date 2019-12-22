package edu.isel.pdm.beegeesapp.bgg.userLists.model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.Repository
import edu.isel.pdm.beegeesapp.bgg.database.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo

class UserListsViewModel(
    val app: BggApplication
) : AndroidViewModel(app) {

    private val repo: Repository = app.repo

    private val userLists : MutableLiveData<MutableList<CustomUserList>> = MutableLiveData()

    private val listsToAddGame : MutableLiveData<MutableList<CustomUserList>> = MutableLiveData()

    private val listsToRemoveGame : MutableLiveData<MutableList<CustomUserList>> = MutableLiveData()

    private lateinit var checkedList : BooleanArray

    fun getUserLists() : MutableLiveData<MutableList<CustomUserList>> = userLists

    fun getListsToAddGame() : MutableList<CustomUserList> = listsToAddGame.value!!

    fun getListsToRemoveGame() : MutableList<CustomUserList> = listsToRemoveGame.value!!

    fun getCheckedLists() = checkedList

    fun initCheckedList(currentGame : GameInfo) {
        checkedList = BooleanArray(getUserListsSize()) {
                idx -> userLists.value!![idx].gamesList.contains(currentGame)
        }
    }

    fun initListsToAddAndRemove() {
        listsToAddGame.value = mutableListOf()
        listsToRemoveGame.value = mutableListOf()
    }

    fun getUserListsSize() = userLists.value?.size ?: 0

    fun getLists(onSuccess: (MutableList<CustomUserList>) -> Unit) {
        repo.getAllCustomUserLists {
            userLists.value = it
            onSuccess(it)
        }
    }

    fun addCustomUserListByName(name: String, onSuccess: () -> Unit, onFail: () -> Unit) {
        repo.addCustomUserListByName(name,
            {
                userLists.value?.add(it)
                onSuccess()
            },
            onFail
        )
    }

    fun addCustomUserList(userList : CustomUserList, position : Int, onSuccess: () -> Unit) {
        repo.addCustomUserList(userList) {
                userLists.value?.add(position, userList)
                onSuccess()
            }
    }

    fun removeCustomUserList(userList : CustomUserList, onSuccess: () -> Unit, onFail: () -> Unit) {
        repo.removeCustomUserList(
            userList,
            {
                userLists.value?.remove(userList)
                onSuccess()
            },
            onFail
        )
    }

    fun updateCheckedLists(game: GameInfo, onFinish: () -> Unit) {
        val list = mutableListOf<CustomUserList>()

        listsToAddGame.value!!.forEach{
            if (!it.gamesList.contains(game)) {
                it.gamesList.add(game)
                list.add(it)
            }
        }

        listsToRemoveGame.value!!.forEach{
            if (it.gamesList.contains(game)) {
                it.gamesList.remove(game)
                list.add(it)
            }
        }

        repo.updateCustomUserLists(list) { onFinish() }
    }


    fun updateUserList(updatedList: CustomUserList, onSuccess: () -> Unit) {
        repo.updateCustomUserList(updatedList, onSuccess)
    }

    fun addListToAddGame(listChecked: CustomUserList, listPosition: Int) {
        if (!getListsToAddGame().contains(listChecked)) {
            checkedList[listPosition] = true
            getListsToAddGame().add(listChecked)
            getListsToRemoveGame().remove(listChecked)
        }
    }

    fun addListToRemoveGame(listChecked: CustomUserList, listPosition: Int) {
        if (!getListsToRemoveGame().contains(listChecked)) {
            checkedList[listPosition] = false
            getListsToRemoveGame().add(listChecked)
            getListsToAddGame().remove(listChecked)
        }
    }

    fun anyListsChanges() : Boolean {
        return getListsToAddGame().isNotEmpty() || getListsToRemoveGame().isNotEmpty()
    }

}
