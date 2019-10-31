package edu.isel.pdm.beegeesapp.bgg

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.search.SearchActivity
import edu.isel.pdm.beegeesapp.bgg.search.Type
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.search.model.SearchInfo
import kotlinx.android.synthetic.main.activity_detailedview.*

class DetailedViewActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailedview)
        if (intent.hasExtra("GAME_OBJECT")) {
            val currentGame = intent.getParcelableExtra("GAME_OBJECT") as GameInfo?
            //TODO:IMAGEM
            if (currentGame != null) {
                Picasso.get()
                    .load(Uri.parse(currentGame.thumb)) // load the image
                    .fit()
                    .into(thumbGame) // select the ImageView to load it into
                game_Name.text = currentGame.name
                nPlayers.text = currentGame.minPlayers.toString() + "-" + currentGame.maxPlayers.toString()
                playTime.text = currentGame.minPlayTime.toString() + "-" + currentGame.maxPlayTime.toString() + " min"
                minAge.text = "+" + currentGame.minAge
                ratingText.text = currentGame.averageUserRating.toInt().toString() + "/5"
                priceText.text = currentGame.price + "$"
                gameYear.text = currentGame.yearPublished.toString()
                description.text = currentGame.description
                companyText.text = currentGame.publisher
                creatorText.text = currentGame.artists
                ruleBookText.setOnClickListener {
                    val url = Uri.parse(currentGame.rulesURL)
                    startActivity(Intent(Intent.ACTION_VIEW, url))
                }
                thumbGame.setOnClickListener {
                    val url = Uri.parse(currentGame.gameURL)
                    startActivity(Intent(Intent.ACTION_VIEW, url))
                }
                companyText.setOnClickListener {
                    val info = SearchInfo( Type.Publisher, companyText.text as String)
                    val intent = Intent(this, SearchActivity::class.java)
                    intent.putExtra("SEARCH_KEYWORD", info)
                    startActivity(intent)
                }
            }
        }

        else throw IllegalArgumentException("Object not Found!")

        supportActionBar?.hide()
        //creatorText.movementMethod = ScrollingMovementMethod()

    }
}