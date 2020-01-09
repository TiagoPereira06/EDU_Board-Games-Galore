package edu.isel.pdm.beegeesapp.bgg.favorites.model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.Repository

class GameDetailsViewModel(app: BggApplication) : AndroidViewModel(app) {

    private val repo: Repository = app.repo

    private val categories: MutableLiveData<List<String>> = MutableLiveData()

    private val mechanics: MutableLiveData<List<String>> = MutableLiveData()

    private var category: String? = null

    private var mechanic: String? = null

    fun searchCategories(onFail: () -> Unit) {
        repo.getCategories(
            { categories.value = it },
            { onFail() }
        )
    }

    fun searchMechanics(onFail: () -> Unit) {
        repo.getMechanics(
            { mechanics.value = it },
            { onFail() }
        )
    }

    fun getMechanics() = mechanics

    fun getCategories() = categories


    fun getSelectedMechanic() = mechanic

    fun getSelectedCategory() = category

    fun setSelectedCategory(category: String) {
        this.category = category
    }

    fun setSelectedMechanic(mechanic: String) {
        this.mechanic = mechanic
    }
}