package edu.isel.pdm.beegeesapp.bgg.favorites.ui

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.database.genRandomThumbnailImage
import edu.isel.pdm.beegeesapp.bgg.dialog.DialogType
import edu.isel.pdm.beegeesapp.bgg.dialog.IChosenStringDialogListener
import edu.isel.pdm.beegeesapp.bgg.favorites.model.GameProfile
import edu.isel.pdm.beegeesapp.bgg.favorites.ui.fragments.ChooseFavCategory
import edu.isel.pdm.beegeesapp.bgg.favorites.ui.fragments.ChooseFavMechanic
import kotlinx.android.synthetic.main.activity_newgameprofile.*

class NewGameProfileActivity : AppCompatActivity(), IChosenStringDialogListener {

    private lateinit var randomThumb : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newgameprofile)

        supportActionBar?.title = "New Game Profile"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))

        randomThumb = genRandomThumbnailImage()
        val drawableResourceId: Int = resources.getIdentifier(randomThumb, "drawable", packageName)
        profileThumb.setImageResource(drawableResourceId)

        catButton.setOnClickListener {
            val dialog =  ChooseFavCategory()
            dialog.show(supportFragmentManager, "Category Dialog")

        }

        mecButton.setOnClickListener {
            val dialog =  ChooseFavMechanic()
            dialog.retainInstance = true
            dialog.show(supportFragmentManager, "Mechanic Dialog")
        }

        val catChip = findViewById<Chip>(R.id.categorychip)
        catChip.setOnCloseIconClickListener {
            catChip.text = ""
            catChip.visibility = View.INVISIBLE
        }
        val mecChip = findViewById<Chip>(R.id.mechanicchip)
        mecChip.setOnCloseIconClickListener {
            mecChip.text = ""
            mecChip.visibility = View.INVISIBLE
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
        if (profileName == "") return false
        val categoryName = categorychip.text.toString()
        val mechanicName = mechanicchip.text.toString()
        val publisherName = publishername.text.toString()
        val designerName = designerName.text.toString()
        val newGameProfile = GameProfile(randomThumb, profileName, categoryName, mechanicName, publisherName, designerName)
        returnIntent.putExtra("RETURN_NEWGAMEPROFILE", newGameProfile)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun chosenName(name: String, type: DialogType) {
        when(type){
            DialogType.NewMechanic ->{
                mechanicchip.visibility = View.VISIBLE
                mechanicchip.text = name
            }
            else ->{
                categorychip.visibility = View.VISIBLE
                categorychip.text = name
            }
        }
    }
}