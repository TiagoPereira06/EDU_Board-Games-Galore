package edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.ChooseThumbDialog
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.choosefavmecandcat.ChooseFavCategory
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.choosefavmecandcat.ChooseFavMechanic
import edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.model.GameProfile
import kotlinx.android.synthetic.main.activity_newgameprofile.*

class NewGameProfileActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "New Game Profile"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        setContentView(R.layout.activity_newgameprofile)

        profileThumb.setOnClickListener {
            val dialog = ChooseThumbDialog()
            dialog.retainInstance = true
            dialog.show(supportFragmentManager, "Thumbnail Dialog")
        }

        catButton.setOnClickListener {
            val dialog =  ChooseFavCategory()
            dialog.retainInstance = true
            dialog.show(supportFragmentManager, "Category Dialog")

        }

        mecButton.setOnClickListener {
            val dialog =  ChooseFavMechanic()
            dialog.retainInstance = true
            dialog.show(supportFragmentManager, "Mechanic Dialog")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_savechanges, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val returnIntent = Intent()
        val profileName = profileName.text.toString()
        val categoryName = categorychip.text.toString()
        val mechanicName = mechanicchip.text.toString()
        val publisherName = publishername.text.toString()
        val designerName = designerName.text.toString()
        //val thumb = profileThumb
        val newGameProfile = GameProfile()
        returnIntent.putExtra("RETURN_NEWGAMEPROFILE",newGameProfile)
        setResult(Activity.RESULT_OK,returnIntent)
        finish()
        return super.onOptionsItemSelected(item)
    }
}