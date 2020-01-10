package edu.isel.pdm.beegeesapp.bgg.favorites.ui

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.SimpleItemTouchHelper
import edu.isel.pdm.beegeesapp.bgg.database.GameProfile
import edu.isel.pdm.beegeesapp.bgg.favorites.FavoritesBaseActivity
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

        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.deleteicon)!!

        val itemTouchHelperCallback = SimpleItemTouchHelper(
            //OnMove
            { _, _, _ -> false },

            //OnSwiped
            { viewHolder, _ -> removeProfileAtPosition(viewHolder.adapterPosition) },

            //onChildDrawOver
            { c, _, viewHolder, dX, _, _, _ ->
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2

                if (dX > 0) {
                    deleteIcon.setBounds(
                        itemView.left + iconMargin,
                        itemView.top + iconMargin,
                        itemView.left + iconMargin + deleteIcon.intrinsicWidth,
                        itemView.bottom - iconMargin
                    )
                }
                deleteIcon.draw(c)
            })

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
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

    private fun removeProfileAtPosition(position: Int) {
        val profileToRemove = viewModel.favorites.value!![position]
        viewModel.removeGameProfile(
            profileToRemove,
            {
                //Removed with success
                favoritesAdapter.notifyItemRemoved(position)
                createUndoOption(profileToRemove, position)
            },
            {
                //Removed failed
                //TODO -> TRADUÇÃO E PLACEHOLDERS
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            })

    }

    private fun createUndoOption(profileRemoved: GameProfile, oldPosition: Int) {
        Snackbar.make(
            layout_userLists,
            "Profile ${profileRemoved.name} Deleted",
            Snackbar.LENGTH_LONG
        )
            .setAction("UNDO") {
                viewModel.addGameProfile(profileRemoved, oldPosition, {
                    favoritesAdapter.notifyItemInserted(oldPosition)
                    Snackbar.make(
                        layout_userLists,
                        "Profile ${profileRemoved.name} Restored",
                        Snackbar.LENGTH_LONG
                    ).show()
                }, {})
            }
            .setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_editorpref, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this, NotificationSettingsActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    private fun gameProfileClicked(profile: GameProfile) {
        val intent = Intent(this, ProfileGameActivity::class.java)
        intent.putExtra("GAME_PROFILE_OBJECT", profile)
        startActivity(intent)
    }
}