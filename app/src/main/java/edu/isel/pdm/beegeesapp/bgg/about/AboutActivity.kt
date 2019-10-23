package edu.isel.pdm.beegeesapp.bgg.about

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.isel.pdm.beegeesapp.R
import kotlinx.android.synthetic.main.activity_about.*

/**
 * Screen that displays the author information
 */
class AboutActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.title = "Who made this?"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        fun navigateToGitHub(author: Int) {
            if (author == 1) {
                val url = Uri.parse(getString(R.string.about_author1gitpage))
                startActivity(Intent(Intent.ACTION_VIEW, url))
            } else {
                val url = Uri.parse(getString(R.string.about_author2gitpage))
                startActivity(Intent(Intent.ACTION_VIEW, url))
            }
        }

        githubLogoa1.setOnClickListener { navigateToGitHub(1) }
        githubLogoa2.setOnClickListener { navigateToGitHub(2) }
    }


}