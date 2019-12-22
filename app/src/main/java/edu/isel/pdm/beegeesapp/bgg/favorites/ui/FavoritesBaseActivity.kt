package edu.isel.pdm.beegeesapp.bgg.favorites.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.bgg.BaseActivity
import edu.isel.pdm.beegeesapp.bgg.favorites.model.FavoritesViewModel

abstract class FavoritesBaseActivity : BaseActivity() {

    lateinit var viewModel: FavoritesViewModel

    private fun getFavoritesViewModel(application: BggApplication) =
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return FavoritesViewModel(application) as T
            }
        }

    override fun initModel() {
        viewModel = ViewModelProviders
            .of(this, getFavoritesViewModel(application as BggApplication))
            .get(FavoritesViewModel::class.java)
    }

}