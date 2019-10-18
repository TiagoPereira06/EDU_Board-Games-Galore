package edu.isel.pdm.beegeesapp.bgg

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.model.GameInfo
import kotlinx.android.synthetic.main.activity_detailedview.*

class DetailedViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailedview)
        if(intent.hasExtra("Game Object")){
            var currentGame = intent.getParcelableExtra("Game Object") as GameInfo
            gameName.text = currentGame!!.name
            //TODO: POPULAR AS RESTANTES VISTAS

        }else throw IllegalArgumentException("Object not Found!")
        supportActionBar?.hide()
        creatorText.movementMethod = ScrollingMovementMethod()
        ruleBookText.setOnClickListener {
            val url = Uri.parse("https://drive.google.com/file/d/0B9kp130SgLtdcGxTcTFodlhaWDg")
            startActivity(Intent(Intent.ACTION_VIEW, url))
        }
        thumbGame.setOnClickListener {
            val url =
                Uri.parse("https://www.boardgameatlas.com/search/game/kPDxpJZ8PD/spirit-island")
            startActivity(Intent(Intent.ACTION_VIEW, url))
        }
    }
}