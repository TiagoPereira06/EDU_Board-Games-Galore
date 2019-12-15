package edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.model.GameProfile

class ProfileGames : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showgameslist)

        if (intent.hasExtra("GAMEPROFILE_OBJECT")){
            val currentProfile = intent.getParcelableExtra("GAME_OBJECT") as GameProfile?

        }
    }
}