package edu.isel.pdm.beegeesapp.bgg.userLists.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.userLists.UserListsActivity
import edu.isel.pdm.beegeesapp.bgg.userLists.view.AddGameToCollectionAdapter

class AddGameToCollection : UserListsActivity() {

    private lateinit var currentGame: GameInfo

    private lateinit var adapter: AddGameToCollectionAdapter

    override fun initView() {
        super.initView()

        if (intent.hasExtra("GAME_INFO")) {
            currentGame = intent.getParcelableExtra("GAME_INFO") as GameInfo
        } else {
            throw Exception("Something went wrong!")
        }

        val actionButton = findViewById<FloatingActionButton>(R.id.addListButton)
        actionButton.hide()

        recyclerView.adapter = getAdapter()
        adapter = recyclerView.adapter as AddGameToCollectionAdapter
    }

    override fun initBehavior(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.getLists {
                if (it.size == 0) {
                    //TODO -> TRADUÇÃO
                    Toast.makeText(this, "Your Collection is empty!", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.initCheckedList(currentGame)
                    viewModel.initListsToAddAndRemove()
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getAdapter(): AddGameToCollectionAdapter {
        return AddGameToCollectionAdapter(viewModel, this)
        { isChecked, listChecked, listPosition ->
            if (isChecked) {
                viewModel.addListToAddGame(listChecked, listPosition)
            } else {
                viewModel.addListToRemoveGame(listChecked, listPosition)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_addgametolist, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (viewModel.anyListsChanges()) {
            viewModel.updateCheckedLists(currentGame) {
                refreshUiAndClearLists()
            }
        }
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun refreshUiAndClearLists() {
        adapter.notifyDataSetChanged()
        viewModel.initListsToAddAndRemove()
    }

}