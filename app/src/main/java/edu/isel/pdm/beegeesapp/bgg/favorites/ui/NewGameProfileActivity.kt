package edu.isel.pdm.beegeesapp.bgg.favorites.ui

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.chip.Chip
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.database.GameProfile
import edu.isel.pdm.beegeesapp.bgg.database.genRandomThumbnailImage
import edu.isel.pdm.beegeesapp.bgg.dialog.DialogType
import edu.isel.pdm.beegeesapp.bgg.dialog.IChosenStringDialogListener
import edu.isel.pdm.beegeesapp.bgg.favorites.FavoritesBaseActivity
import edu.isel.pdm.beegeesapp.bgg.favorites.ui.fragments.ChooseFavCategory
import edu.isel.pdm.beegeesapp.bgg.favorites.ui.fragments.ChooseFavMechanic
import kotlinx.android.synthetic.main.activity_newgameprofile.*

class NewGameProfileActivity : FavoritesBaseActivity(), IChosenStringDialogListener {

    private lateinit var randomThumb : String

    override fun setContentView() {
        setContentView(R.layout.activity_newgameprofile)
    }

    override fun initTitle() {
        supportActionBar?.title = "New Game Profile"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))    }

    override fun initView() {
        randomThumb = genRandomThumbnailImage()
        val drawableResourceId: Int = resources.getIdentifier(randomThumb, "drawable", packageName)
        profileThumb.setImageResource(drawableResourceId)

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

        val catChip = findViewById<Chip>(R.id.categorychip)
        catChip.setOnCloseIconClickListener {
            catChip.text = ""
            catChip.visibility = View.INVISIBLE
        }
        val mecChip = findViewById<Chip>(R.id.mechanicchip)
        mecChip.setOnCloseIconClickListener {
            mecChip.text = ""
            mecChip.visibility = View.INVISIBLE
        }    }

    override fun initBehavior(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val mec = viewModel.mec
            val cat = viewModel.cat
            if (!mec.isNullOrBlank()) {
                mechanicchip.text = mec
                mechanicchip.visibility = View.VISIBLE

            }
            if(!cat.isNullOrBlank()) {
                categorychip.text = cat
                categorychip.visibility = View.VISIBLE
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        if (mechanicchip.text.toString().isNotEmpty())
            viewModel.mec = mechanicchip.text
        if (categorychip.text.toString().isNotEmpty())
            viewModel.cat = categorychip.text
        super.onSaveInstanceState(outState)
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
        if(viewModel.checkConstraints(profileName,categoryName,mechanicName)) {
            val newGameProfile = GameProfile(
                randomThumb,
                profileName,
                categoryName,
                mechanicName,
                publisherName,
                designerName
            )
            returnIntent.putExtra("RETURN_NEWGAMEPROFILE", newGameProfile)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
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