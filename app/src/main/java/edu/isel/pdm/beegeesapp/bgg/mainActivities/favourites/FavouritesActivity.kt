package edu.isel.pdm.beegeesapp.bgg.mainActivities.favourites

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.mainActivities.favourites.view.FavouritesListAdapter
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo


class FavouritesActivity : AppCompatActivity() {

    private val favGames: List<GameInfo> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        supportActionBar?.title = getString(R.string.dash_favouritesInfo)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))


        val listRv = findViewById<RecyclerView>(R.id.fav_recycler_view)
        val myAdapter = FavouritesListAdapter(favGames)
        listRv.layoutManager = GridLayoutManager(this, 3)
        listRv.adapter = myAdapter




    }

}


