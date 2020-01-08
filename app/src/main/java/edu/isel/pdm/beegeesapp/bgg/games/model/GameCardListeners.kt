package edu.isel.pdm.beegeesapp.bgg.games.model

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import edu.isel.pdm.beegeesapp.bgg.BaseActivity
import edu.isel.pdm.beegeesapp.bgg.games.ui.GameDetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.userLists.ui.AddGameToCollection

class GameCardListeners(var host : BaseActivity) {

     fun gameItemClicked(gameItem: GameInfo) {
        val intent = Intent(host, GameDetailedViewActivity::class.java)
        intent.putExtra("GAME_OBJECT", gameItem)
        startActivity(host,intent,null)
    }

     fun addToCollectionItemClicked(gameItem: GameInfo) {
        val intent = Intent(host, AddGameToCollection::class.java)
        intent.putExtra("GAME_INFO", gameItem)
        startActivity(host,intent,null)
    }
}