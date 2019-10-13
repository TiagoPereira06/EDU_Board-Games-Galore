package edu.isel.pdm.beegeesapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about.*

/**
 * Screen that displays the author information
 */
class AboutActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.hide()
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