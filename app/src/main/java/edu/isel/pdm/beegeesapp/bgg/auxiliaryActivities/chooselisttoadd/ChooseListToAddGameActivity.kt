package edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.chooselisttoadd

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.GamesRepository
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo

class ChooseListToAddGameActivity: AppCompatActivity() {

    private lateinit var listRv: RecyclerView
    private lateinit var listAdapter: ChooseListAdapter
    private lateinit var currentGame : GameInfo
    private lateinit var repo: GamesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.title = "Your Collection"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        setContentView(R.layout.activity_chooselisttoadd)

        if (intent.hasExtra("GAME_INFO")) {
            currentGame = intent.getParcelableExtra("GAME_INFO") as GameInfo
        }
        repo = (application as BggApplication).repo
        listRv = findViewById(R.id.listRecyclerView)
        listRv.layoutManager = LinearLayoutManager(this)
        listAdapter =
            ChooseListAdapter(
                repo.getAllCustomUserLists(),
                this,
                currentGame
            )
        listRv.adapter = listAdapter
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_savechanges, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val returnIntent = Intent()
        val listOfListsNamesToAddGame = listAdapter.listsNamesToAddTheGame.toTypedArray()
        val listOfListsNamesToRemoveGame = listAdapter.listsNamesToRemoveTheGame.toTypedArray()
        returnIntent.putExtra("RETURN_ARRAYTOADD",listOfListsNamesToAddGame)
        returnIntent.putExtra("RETURN_ARRAYTOREMOVE",listOfListsNamesToRemoveGame)
        if ((listAdapter.listsNamesToAddTheGame.isNullOrEmpty()) and (listAdapter.listsNamesToRemoveTheGame.isNullOrEmpty())){ //NÃO HOUVE ALTERAÇÕES!
            setResult(Activity.RESULT_CANCELED,returnIntent)
        }else{
            setResult(Activity.RESULT_OK,returnIntent)
        }
        finish()
        return super.onOptionsItemSelected(item)
    }
}