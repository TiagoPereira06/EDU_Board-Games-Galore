package edu.isel.pdm.beegeesapp.bgg.favorites.ui


import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.BaseActivity
import edu.isel.pdm.beegeesapp.bgg.favorites.model.GameProfile
import edu.isel.pdm.beegeesapp.bgg.favorites.model.ProfileGameViewModel
import edu.isel.pdm.beegeesapp.bgg.games.model.GameCardListeners
import edu.isel.pdm.beegeesapp.bgg.games.view.GamesListAdapter
import kotlinx.android.synthetic.main.recycler_view.*
class ProfileGameActivity : BaseActivity() {

    companion object {
        const val GAME_PROFILE = "GAME_PROFILE_OBJECT"

        fun createIntent(origin: Context, gameProfile: GameProfile) =
            Intent(origin, ProfileGameActivity::class.java).apply { putExtra(GAME_PROFILE, gameProfile) }
    }

    private lateinit var viewModel : ProfileGameViewModel

    private lateinit var profile : GameProfile

    private fun getGamesViewModelFactory(application: BggApplication) = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileGameViewModel(application,profile) as T
        }
    }

    override fun setContentView() {
        setContentView(R.layout.recycler_view)
    }

    override fun initTitle() {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        supportActionBar?.title = profile.name
    }


    override fun initView() {
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = GamesListAdapter(viewModel, GameCardListeners(this))
    }

    override fun initModel() {
        profile = intent.getParcelableExtra(GAME_PROFILE) as GameProfile
        viewModel = ViewModelProviders
            .of(this, getGamesViewModelFactory(application as BggApplication))
            .get(ProfileGameViewModel::class.java)



    }
    override fun initBehavior(savedInstanceState: Bundle?) {
        observeViewModel()
        searchSwipeLayout.setOnRefreshListener {
            viewModel.updateGames ({
                Toast.makeText(this,"Check your Internet Connection!",Toast.LENGTH_SHORT).show()
            },{
                searchSwipeLayout.isRefreshing = false
            })
            searchSwipeLayout.isRefreshing = true
        }
    }

    private fun observeViewModel() {
        viewModel.games.observe(this, Observer {
            recycler_view.adapter = GamesListAdapter(viewModel, GameCardListeners(this))
            searchSwipeLayout.isRefreshing = false
        })
    }


}