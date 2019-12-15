package edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.GamesRepository
import edu.isel.pdm.beegeesapp.bgg.databaseUtils.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.primaryActivities.about.AboutActivity
import edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.model.GameProfile
import edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.view.ProfileListAdapter
import kotlinx.android.synthetic.main.activity_favourites.*
import java.util.function.BiFunction

class FavouritesActivity : AppCompatActivity() {


    private lateinit var repo: GamesRepository
    private lateinit var currentGameProfilesLists: MutableList<GameProfile>
    private val CODE = 16



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)
        repo = (application as BggApplication).repo
        //currentGameProfilesLists = repo.getAllGameProfilesList()

        //supportActionBar?.title = getString(R.string.dash_favouritesInfo)
        supportActionBar?.title = "Your Game Profiles"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))

        val profileRv = findViewById<RecyclerView>(R.id.profiles_recycler_view)
        profileRv.layoutManager = LinearLayoutManager(this)

        currentGameProfilesLists = mutableListOf<GameProfile>()

        currentGameProfilesLists.add(GameProfile("Teste 1"))
        currentGameProfilesLists.add(GameProfile("Teste 2"))
        currentGameProfilesLists.add(GameProfile("Teste 3"))
        currentGameProfilesLists.add(GameProfile("Teste 4"))
        currentGameProfilesLists.add(GameProfile("Teste 5"))
        currentGameProfilesLists.add(GameProfile("Teste 6"))

        profileRv.adapter = ProfileListAdapter(application as BggApplication,currentGameProfilesLists)


        addProfileButton.setOnClickListener {
            val intent = Intent(this, NewGameProfileActivity::class.java)
            startActivityForResult(intent,CODE)
        }

    }
    }


