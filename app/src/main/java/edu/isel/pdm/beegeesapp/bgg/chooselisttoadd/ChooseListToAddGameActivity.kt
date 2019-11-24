package edu.isel.pdm.beegeesapp.bgg.chooselisttoadd

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.userlists.UserListsActivity
import edu.isel.pdm.beegeesapp.bgg.userlists.model.UserListContainer

class ChooseListToAddGameActivity: AppCompatActivity() {

    private lateinit var listRv: RecyclerView
    private lateinit var listAdapter: ChooseListAdapter
    private lateinit var currentGame : GameInfo
    private lateinit var listsContainer : UserListContainer


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.title = "Your Collection"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        setContentView(R.layout.activity_chooselisttoadd)

        if (intent.hasExtra("GAME_INFO")) {
            currentGame = intent.getParcelableExtra("GAME_INFO") as GameInfo
        }

        //else SAI DA ACTIVITY EXCEPÇÃO

        //--MUDAR?--
        listsContainer = UserListsActivity.listContainer


        listRv = findViewById(R.id.listRecyclerView)
        listRv.layoutManager = LinearLayoutManager(this)
        listAdapter =
            ChooseListAdapter(
                listsContainer,
                this,
                currentGame
            )
        listRv.adapter = listAdapter


        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_addgametolist, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        listAdapter.listsNamesToAddTheGame.forEach { listAdd ->
            val aux = listsContainer.userLists.find { it.listName == listAdd }
            aux!!.list.add(currentGame)
        }

        //REMOVE
        listAdapter.listsNamesToRemoveTheGame.forEach { listRemove ->
            val aux = listsContainer.userLists.find { it.listName == listRemove }
            aux!!.list.remove(currentGame)
        }
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}