package edu.isel.pdm.beegeesapp.bgg.favourites

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.isel.pdm.beegeesapp.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import edu.isel.pdm.beegeesapp.bgg.favourites.view.FavouritesListAdapter
import edu.isel.pdm.beegeesapp.bgg.search.model.GameInfo


class FavouritesActivity : AppCompatActivity() {

    private val favGames : List<GameInfo> = listOf(
        GameInfo("Spirit Island"),
        GameInfo("DOmokm Bwndu"),
        GameInfo("Opm Gsiw"),
        GameInfo("Odpkm KDIMOKm"),
        GameInfo("Fdgok"),
        GameInfo("OFKMNokm"),
        GameInfo("OJFNojnwo"),
        GameInfo("IHHBq"),
        GameInfo("DWAub"),
        GameInfo("BBIB9w"),
        GameInfo("BBIB9w"),
        GameInfo("BBIB9w"),
        GameInfo("BBIB9w"),
        GameInfo("BBIB9w"),
        GameInfo("BBIB9w"),
        GameInfo("BBIB9w"),
        GameInfo("BBIB9w"),
        GameInfo("IBIbbo")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        supportActionBar?.title = "Your Shelf"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))


        val favRv = findViewById<RecyclerView>(R.id.fav_recycler_view)
        val myAdapter = FavouritesListAdapter(favGames)
        favRv.layoutManager = GridLayoutManager(this, 3)
        favRv.adapter = myAdapter

    }

}


