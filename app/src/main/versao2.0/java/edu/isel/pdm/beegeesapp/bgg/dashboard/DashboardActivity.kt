package edu.isel.pdm.beegeesapp.bgg.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.games.ui.GamesWithPaginationAndMenu
import edu.isel.pdm.beegeesapp.bgg.games.ui.GamesWithPagination
import edu.isel.pdm.beegeesapp.bgg.about.AboutActivity
import edu.isel.pdm.beegeesapp.bgg.favorites.ui.FavouritesActivity
import edu.isel.pdm.beegeesapp.bgg.games.model.SearchType
import edu.isel.pdm.beegeesapp.bgg.userLists.ui.UserLists
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo


class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()
        val trend = findViewById<CardView>(R.id.trendingView)
        val fav = findViewById<CardView>(R.id.favouritesView)
        val search = findViewById<CardView>(R.id.searchView)
        val about = findViewById<CardView>(R.id.aboutView)
        val userlist = findViewById<CardView>(R.id.userlist_View)

        initUI(trend, fav, search, about,userlist)
    }

    private fun initUI(
        trendView: CardView,
        favView: CardView,
        searchView: CardView,
        aboutView: CardView,
        userList : CardView
    ) {
        // TRENDING
        trendView.setOnClickListener {
            val intent = Intent(this, GamesWithPagination::class.java)
            intent.putExtra("REQUEST_INFO", RequestInfo(SearchType.Trending))
            startActivity(intent)
        }
        val title: TextView = trendView.findViewById(R.id.titleView) as TextView
        title.text = getString(R.string.dash_trending)
        val subtitle: TextView = trendView.findViewById(R.id.infoView) as TextView
        subtitle.text = getString(R.string.dash_trendingInfo)
        val image: ImageView = trendView.findViewById(R.id.iconView) as ImageView
        image.setImageResource(R.drawable.trendingdash)

        // FAVORITES
        favView.setOnClickListener {
            //TODO
            startActivity(Intent(this, FavouritesActivity::class.java))
        }
        val title1: TextView = favView.findViewById(R.id.titleView) as TextView
        title1.text = getString(R.string.dash_fav)
        val subtitle1: TextView = favView.findViewById(R.id.infoView) as TextView
        subtitle1.text = getString(R.string.dash_favouritesInfo)
        val image1: ImageView = favView.findViewById(R.id.iconView) as ImageView
        image1.setImageResource(R.drawable.favoritedash)

        // SEARCH
        searchView.setOnClickListener {
            val intent = Intent(this, GamesWithPaginationAndMenu::class.java)
            intent.putExtra("REQUEST_INFO", RequestInfo(SearchType.Name, null))
            startActivity(intent)
        }
        val title2: TextView = searchView.findViewById(R.id.titleView) as TextView
        title2.text = getString(R.string.dash_search)
        val subtitle2: TextView = searchView.findViewById(R.id.infoView) as TextView
        subtitle2.text = getString(R.string.dash_searchInfo)
        val image2: ImageView = searchView.findViewById(R.id.iconView) as ImageView
        image2.setImageResource(R.drawable.searchdash)

        // ABOUT
        aboutView.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
        val title3: TextView = aboutView.findViewById(R.id.titleView) as TextView
        title3.text = getString(R.string.dash_about)
        val subtitle3: TextView = aboutView.findViewById(R.id.infoView) as TextView
        subtitle3.text = getString(R.string.dash_aboutInfo)
        val image3: ImageView = aboutView.findViewById(R.id.iconView) as ImageView
        image3.setImageResource(R.drawable.informationdash)
        // CUSTOM LIST
        userList.setOnClickListener{
            //TODO
            startActivity(Intent(this,
                UserLists::class.java))
        }
        val title4: TextView = userList.findViewById(R.id.titleView) as TextView
        title4.text = getString(R.string.dash_userLists)
        val subtitle4: TextView = userList.findViewById(R.id.infoView) as TextView
        subtitle4.text = getString(R.string.dash_userListsInfo)
        val image4: ImageView = userList.findViewById(R.id.iconView) as ImageView
        image4.setImageResource(R.drawable.customlistsdash)
    }
}


