package edu.isel.pdm.beegeesapp.bgg


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.isel.pdm.beegeesapp.R
import kotlinx.android.synthetic.main.activity_detailedview.*

/**
 * A simple [Fragment] subclass.
 */
class DetailedViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (savedInstanceState != null) {
            var currentGame = savedInstanceState.get("Game Object") as Game
            gameName.text = currentGame.name
            //TODO: PREENCHER VISTA
        }
        return inflater.inflate(R.layout.fragment_detailed_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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