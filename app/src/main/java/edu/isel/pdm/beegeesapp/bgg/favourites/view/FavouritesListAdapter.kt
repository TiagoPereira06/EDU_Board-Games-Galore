package edu.isel.pdm.beegeesapp.bgg.favourites.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.search.model.GameInfo
import kotlinx.android.synthetic.main.fav_cardview.view.*

class FavouritesListAdapter(private val favGames: List<GameInfo>) :
    RecyclerView.Adapter<FavouritesListAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fav_cardview, parent, false)
        )
    }

    override fun getItemCount() = favGames.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = favGames[position]
        holder.view.game_Name.text = game.name
        holder.view.game_Image.setImageResource(R.drawable.thumb)
    }

    class GameViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
