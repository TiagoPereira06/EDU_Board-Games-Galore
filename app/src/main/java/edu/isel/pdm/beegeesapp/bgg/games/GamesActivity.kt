package edu.isel.pdm.beegeesapp.bgg.games

import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.BaseActivity
import edu.isel.pdm.beegeesapp.bgg.games.model.GameCardListeners
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesViewModel
import edu.isel.pdm.beegeesapp.bgg.games.view.GamesListAdapter
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import kotlinx.android.synthetic.main.recycler_view.*
import java.lang.Exception

abstract class GamesActivity : BaseActivity() {

    lateinit var viewModel : GamesViewModel

    lateinit var layoutManager: LinearLayoutManager

    private fun getGamesViewModelFactory(application: BggApplication) = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GamesViewModel(application) as T
        }
    }

    override fun setContentView() {
        setContentView(R.layout.recycler_view)
    }

    override fun initView() {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)

        layoutManager = recycler_view.layoutManager as LinearLayoutManager

        recycler_view.adapter = getGamesAdapter()
    }

    override fun initModel() {
        viewModel = ViewModelProviders
            .of(this, getGamesViewModelFactory(application as BggApplication))
            .get(GamesViewModel::class.java)
            .init(getRequest())
    }

    private fun getRequest() : RequestInfo {
        if (intent.hasExtra("REQUEST_INFO")) {
            return intent.getParcelableExtra("REQUEST_INFO") as RequestInfo
        }
        throw Exception("Something unexpected happened!")
    }

    private fun getGamesAdapter(): GamesListAdapter =
        GamesListAdapter(viewModel,GameCardListeners(this))



}