package edu.isel.pdm.beegeesapp

import android.app.Application
import edu.isel.pdm.beegeesapp.bgg.GamesRepository

class BggApplication : Application() {

   lateinit var repo : GamesRepository

    override fun onCreate() {
        super.onCreate()
        repo = GamesRepository(this)
    }
}