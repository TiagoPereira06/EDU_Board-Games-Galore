package edu.isel.pdm.beegeesapp.bgg.games.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.squareup.picasso.Picasso
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.BaseActivity
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import edu.isel.pdm.beegeesapp.bgg.games.model.SearchType
import kotlinx.android.synthetic.main.activity_detailedgameview.*


class GameDetailedViewActivity : BaseActivity() {

    private lateinit var currentGame: GameInfo

    override fun setContentView() {
        setContentView(R.layout.activity_detailedgameview)
    }

    override fun initModel() {
        if (intent.hasExtra("GAME_OBJECT")) {
            currentGame = intent.getParcelableExtra("GAME_OBJECT") as GameInfo
        }
        else throw IllegalArgumentException("Object not Found!")
    }

    override fun initTitle() {
        supportActionBar?.hide()
    }

    override fun initView() {
        Picasso.get()
            .load(Uri.parse(currentGame.thumb_url)) // load the image
            .fit()
            .into(thumbGame) // select the ImageView to load it into

        game_Name.text = currentGame.name
        nPlayers.text = currentGame.min_players.toString() + "-" + currentGame.max_players.toString()
        playTime.text = currentGame.min_playtime.toString() + "-" + currentGame.max_playtime.toString() + " min"
        minAge.text = "+" + currentGame.min_age
        ratingText.text = currentGame.average_user_rating.toInt().toString() + "/5"
        priceText.text = currentGame.price + "$"
        gameYear.text = currentGame.year_published.toString()
        description.text = Html.fromHtml(currentGame.description)
        companyText.text = currentGame.primary_publisher

        val list = currentGame.artists
        for (index in list.indices) {
            val row = TableRow(this)
            val a1 = TextView(this)
            a1.text = list[index]
            a1.setTextColor(resources.getColor(R.color.colorText))
            a1.textSize = (15f)
            a1.typeface = ResourcesCompat.getFont(this,R.font.biryani)
            a1.setOnClickListener {
                val info = RequestInfo(
                    SearchType.Artist,
                    a1.text as String
                )
                val intent = Intent(this, GamesWithPaginationAndMenu::class.java)
                intent.putExtra("REQUEST_INFO", info)
                intent.putExtra("INITIAL_SEARCH", true)
                startActivity(intent)
            }
            a1.gravity = Gravity.CENTER
            row.gravity = Gravity.CENTER
            row.addView(a1)
            creatorText.addView(row)
        }    }

    override fun initBehavior(savedInstanceState: Bundle?) {
        ruleBookText.setOnClickListener {
            val url = Uri.parse(currentGame.rules_url)
            startActivity(Intent(Intent.ACTION_VIEW, url))
        }

        thumbGame.setOnClickListener {
            val url = Uri.parse(currentGame.gameUrl)
            startActivity(Intent(Intent.ACTION_VIEW, url))
        }

        companyText.setOnClickListener {
            val info = RequestInfo(
                SearchType.Publisher,
                companyText.text as String
            )
            val intent = Intent(this, GamesWithPaginationAndMenu::class.java)
            intent.putExtra("REQUEST_INFO", info)
            intent.putExtra("INITIAL_SEARCH", true)
            startActivity(intent)
        }    }
}