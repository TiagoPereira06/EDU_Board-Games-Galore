package edu.isel.pdm.beegeesapp.bgg

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.squareup.picasso.Picasso
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import edu.isel.pdm.beegeesapp.bgg.search.SearchActivity
import edu.isel.pdm.beegeesapp.bgg.search.Type
import kotlinx.android.synthetic.main.activity_detailedgameview.*


class GameDetailedViewActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailedgameview)

        if (intent.hasExtra("GAME_OBJECT")) {
            val currentGame = intent.getParcelableExtra("GAME_OBJECT") as GameInfo?

            if (currentGame != null) {
                Picasso.get()
                    .load(Uri.parse(currentGame.thumb_url)) // load the image
                    .fit()
                    .into(thumbGame) // select the ImageView to load it into
                game_Name.text = currentGame.name
                nPlayers.text =
                    currentGame.min_players.toString() + "-" + currentGame.max_players.toString()
                playTime.text =
                    currentGame.min_playtime.toString() + "-" + currentGame.max_playtime.toString() + " min"
                minAge.text = "+" + currentGame.min_age
                ratingText.text = currentGame.average_user_rating.toInt().toString() + "/5"
                priceText.text = currentGame.price + "$"
                gameYear.text = currentGame.year_published.toString()
                description.text = Html.fromHtml(currentGame.description)
                companyText.text = currentGame.primary_publisher

                val list = currentGame.artists
                for (index in list.indices){
                    val row = TableRow(this)
                    val a1 = TextView(this)
                    a1.text = list[index]
                    a1.setTextColor(resources.getColor(R.color.colorText))
                    a1.textSize = (15f)
                    a1.typeface = ResourcesCompat.getFont(this,R.font.biryani)
                    a1.setOnClickListener {
                        val info = RequestInfo(
                            Type.Artist,
                            a1.text as String
                        )
                        val intent = Intent(this, SearchActivity::class.java)
                        intent.putExtra("SEARCH_KEYWORD", info)
                        startActivity(intent)
                    }
                    a1.gravity = Gravity.CENTER
                    row.gravity = Gravity.CENTER
                    row.addView(a1)
                    creatorText.addView(row)

                }
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
                        Type.Publisher,
                        companyText.text as String
                    )
                    val intent = Intent(this, SearchActivity::class.java)
                    intent.putExtra("SEARCH_KEYWORD", info)
                    startActivity(intent)
                }
            }
        }

        else throw IllegalArgumentException("Object not Found!")

        supportActionBar?.hide()

    }
}