package edu.isel.pdm.beegeesapp.bgg.favorites.model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.Repository

class CategoriesViewModel(app: BggApplication) : AndroidViewModel(app) {

    private val repo: Repository = app.repo

    val categories: MutableLiveData<List<String>> = MutableLiveData()

    fun getCategories(onFail : () -> Unit) {
        repo.getCategories (
            { categories.value = it },
            { onFail() }
        )
    }
}
