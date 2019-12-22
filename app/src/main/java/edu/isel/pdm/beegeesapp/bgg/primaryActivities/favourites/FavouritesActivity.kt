package edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.GamesRepository
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.NotificationSettingsActivity
import edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.model.GameProfile
import edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.view.ProfileListAdapter
import kotlinx.android.synthetic.main.activity_favourites.*

class FavouritesActivity : AppCompatActivity() {


    private lateinit var repo: GamesRepository
    private lateinit var profileRvAdapter: ProfileListAdapter
    private lateinit var deleteIcon : Drawable
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
        deleteIcon = ContextCompat.getDrawable(this,R.drawable.deleteicon)!!

        val profileRv = findViewById<RecyclerView>(R.id.profiles_recycler_view)
        profileRv.layoutManager = LinearLayoutManager(this)

        //currentGameProfilesLists = mutableListOf<GameProfile>()
        currentGameProfilesLists = repo.getAllGameProfilesList()

/*
        currentGameProfilesLists.add(GameProfile("Teste 1"))
        currentGameProfilesLists.add(GameProfile("Teste 2"))
        currentGameProfilesLists.add(GameProfile("Teste 3"))
        currentGameProfilesLists.add(GameProfile("Teste 4"))
        currentGameProfilesLists.add(GameProfile("Teste 5"))
        currentGameProfilesLists.add(GameProfile("Teste 6"))

        currentGameProfilesLists.forEach {
            repo.addGameProfile(it)
        }
*/

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                profileRvAdapter.removeItem(viewHolder)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2

                if (dX>0){
                    deleteIcon.setBounds(itemView.left + iconMargin, itemView.top+iconMargin, itemView.left + iconMargin + deleteIcon.intrinsicWidth,itemView.bottom-iconMargin)
                }
                deleteIcon.draw(c)
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(profileRv)

        profileRvAdapter = ProfileListAdapter(application as BggApplication,currentGameProfilesLists)
        profileRv.adapter = profileRvAdapter


        addProfileButton.setOnClickListener {
            val intent = Intent(this, NewGameProfileActivity::class.java)
            startActivityForResult(intent,CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
          val newGameProfile = data!!.getParcelableExtra<GameProfile>("RETURN_NEWGAMEPROFILE")
            //GUARDAR NO REPO!
            currentGameProfilesLists.add(newGameProfile)
            profileRvAdapter.notifyDataSetChanged()
        }
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
}


