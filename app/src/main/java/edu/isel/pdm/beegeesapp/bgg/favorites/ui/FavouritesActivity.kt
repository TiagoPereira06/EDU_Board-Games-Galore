package edu.isel.pdm.beegeesapp.bgg.favorites.ui

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.NotificationSettingsActivity
import edu.isel.pdm.beegeesapp.bgg.favorites.model.GameProfile
import edu.isel.pdm.beegeesapp.bgg.favorites.view.ProfileListAdapter
import kotlinx.android.synthetic.main.recycler_view_floating_button.*

class FavouritesActivity : FavoritesBaseActivity() {

    private lateinit var favoritesAdapter : ProfileListAdapter

    private val CODE = 16

    override fun setContentView() {
        setContentView(R.layout.recycler_view_floating_button)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
    }

    override fun initTitle() {
        supportActionBar?.title = "Your Game Profiles"
    }

    override fun initView() {
        val recyclerView = findViewById<RecyclerView>(R.id.lists_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ProfileListAdapter(application as BggApplication, viewModel) {
            gameProfile -> gameProfileClicked(gameProfile)
        }
        favoritesAdapter = recyclerView.adapter as ProfileListAdapter
    }

    override fun initBehavior(savedInstanceState: Bundle?) {

        viewModel.favorites.observe(this, Observer {
            favoritesAdapter.notifyDataSetChanged()
        })

        if (savedInstanceState == null) {
            viewModel.getFavorites()
        }

        addListButton.setOnClickListener {
            val intent = Intent(this, NewGameProfileActivity::class.java)
            startActivityForResult(intent,CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
          val newGameProfile = data!!.getParcelableExtra("RETURN_NEWGAMEPROFILE") as GameProfile
            viewModel.addNewGameProfile(newGameProfile,
                { favoritesAdapter.notifyItemInserted(viewModel.favorites.value!!.size - 1) },
                { Toast.makeText(this, "The name you inserted is already in use. Try another!", Toast.LENGTH_LONG).show() }
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_editorpref, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //TODO -> Inacabado
        startActivity(Intent(this, NotificationSettingsActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    private fun gameProfileClicked(profile: GameProfile) {
        val intent = Intent(this, ProfileGameActivity::class.java)
        /*profile.gamesList.add(GameInfo("yoyo", 20, 25, 40 ,40, "https://s3-us-west-1.amazonaws.com/5cc.images/games/uploaded/1559254941010-61PJxjjnbfL.jpg",
            6, 20, 1998, "Teste", "vamos passar com 20", "sim", emptyList(), emptyList(), null))

         */
        intent.putExtra("GAME_PROFILE_OBJECT", profile)
        startActivity(intent)
    }
}