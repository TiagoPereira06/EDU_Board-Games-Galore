package edu.isel.pdm.beegeesapp.bgg.userLists

import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.BaseActivity
import edu.isel.pdm.beegeesapp.bgg.userLists.model.UserListsViewModel

abstract class UserListsActivity : BaseActivity() {

    lateinit var viewModel : UserListsViewModel

    lateinit var recyclerView : RecyclerView

    private fun getListsViewModelFactory(application: BggApplication) = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserListsViewModel(
                application
            ) as T
        }
    }

    override fun setContentView() {
        setContentView(R.layout.recycler_view_floating_button)
    }

    override fun initTitle() {
        supportActionBar?.title = getString(R.string.dash_userListsInfo)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
    }

    override fun initView() {
        recyclerView = findViewById(R.id.lists_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun initModel() {
        viewModel = ViewModelProviders
            .of(this, getListsViewModelFactory(application as BggApplication))
            .get(UserListsViewModel::class.java)
    }

}