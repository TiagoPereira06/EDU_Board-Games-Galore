package edu.isel.pdm.beegeesapp

import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import edu.isel.pdm.beegeesapp.bgg.GamesRepository

class BggApplication : Application() {

   lateinit var repo : GamesRepository
    lateinit var queue: RequestQueue

    override fun onCreate() {
        super.onCreate()
        repo = GamesRepository(this)
        queue = Volley.newRequestQueue(this)
    }
}