package edu.isel.pdm.beegeesapp.bgg

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.about.AboutActivity
import edu.isel.pdm.beegeesapp.bgg.favourites.FavouritesActivity
import edu.isel.pdm.beegeesapp.bgg.search.SearchActivity
import edu.isel.pdm.beegeesapp.bgg.search.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.trending.TrendingActivity


class DashboardActivity : AppCompatActivity() {

    private val favGames: List<GameInfo> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_dashboard)
        val trend = findViewById<CardView>(R.id.trendingView)
        val fav = findViewById<CardView>(R.id.favouritesView)
        val search = findViewById<CardView>(R.id.searchView)
        val about = findViewById<CardView>(R.id.aboutView)
        initUI(trend, fav, search, about)
    }

    private fun initUI(
        trendView: CardView,
        favView: CardView,
        searchView: CardView,
        aboutView: CardView
    ) {
        // TRENDING
        trendView.setOnClickListener {
            startActivity(Intent(this, TrendingActivity::class.java))
        }
        val title: TextView = trendView.findViewById(R.id.titleView) as TextView
        title.text = "Trending"
        val subtitle: TextView = trendView.findViewById(R.id.infoView) as TextView
        subtitle.text = "See what's trending"
        val image: ImageView = trendView.findViewById(R.id.iconView) as ImageView
        image.setImageResource(R.drawable.trendingdash)

        // FAVORITES
        favView.setOnClickListener {
            startActivity(Intent(this, FavouritesActivity::class.java))
        }
        val title1: TextView = favView.findViewById(R.id.titleView) as TextView
        title1.text = "Favourites"
        val subtitle1: TextView = favView.findViewById(R.id.infoView) as TextView
        subtitle1.text = "Your games shelf"
        val image1: ImageView = favView.findViewById(R.id.iconView) as ImageView
        image1.setImageResource(R.drawable.favoritedash)

        // SEARCH
        searchView.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        val title2: TextView = searchView.findViewById(R.id.titleView) as TextView
        title2.text = "Search"
        val subtitle2: TextView = searchView.findViewById(R.id.infoView) as TextView
        subtitle2.text = "Looking for a specific game?"
        val image2: ImageView = searchView.findViewById(R.id.iconView) as ImageView
        image2.setImageResource(R.drawable.searchdash)

        // ABOUT
        aboutView.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
        val title3: TextView = aboutView.findViewById(R.id.titleView) as TextView
        title3.text = "About"
        val subtitle3: TextView = aboutView.findViewById(R.id.infoView) as TextView
        subtitle3.text = "The developers who did this"
        val image3: ImageView = aboutView.findViewById(R.id.iconView) as ImageView
        image3.setImageResource(R.drawable.informationdash)
    }

    fun addToFavourites(game: GameInfo) {
        //  favGames[]=game
        //TODO: LISTENER FAVORITOS
    }


}


