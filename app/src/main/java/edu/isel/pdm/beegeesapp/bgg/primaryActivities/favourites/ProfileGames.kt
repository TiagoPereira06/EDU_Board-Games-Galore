package edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.model.GameProfile

class ProfileGames : AppCompatActivity() {
    companion object {
        val INITIAL_STATE_EXTRA_KEY = "SHOULD_DISPLAY"

        fun createIntent(origin: Context, shouldDisplay: Boolean) =
            Intent(origin, ProfileGames::class.java).apply {
                if (shouldDisplay) putExtra(INITIAL_STATE_EXTRA_KEY, shouldDisplay)
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showgameslist)

        if (intent.hasExtra("GAMEPROFILE_OBJECT")){
            val currentProfile = intent.getParcelableExtra("GAME_OBJECT") as GameProfile?

        }
    }
}