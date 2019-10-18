package edu.isel.pdm.beegeesapp.bgg

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.isel.pdm.beegeesapp.R
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : AppCompatActivity() {

    private val favGames: List<Game> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        button.setOnClickListener{
            startActivity(Intent(this, FavouritesActivity::class.java))
        }

        button2.setOnClickListener{
            startActivity(Intent(this, TrendingActivity::class.java))
        }
        button3.setOnClickListener{
            startActivity(Intent(this, SearchActivity::class.java))
        }

        button4.setOnClickListener{
            startActivity(Intent(this, AboutActivity::class.java))
        }

        supportActionBar?.title = "Dashboard"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))

    }

        fun addToFavourites(game: Game) {
            //  favGames[]=game
            //TODO: LISTENER FAVORITOS
        }


}


