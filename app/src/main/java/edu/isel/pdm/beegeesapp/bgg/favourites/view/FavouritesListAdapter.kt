package edu.isel.pdm.beegeesapp.bgg.favourites.view


import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import kotlinx.android.synthetic.main.card_fav.view.*

class FavouritesListAdapter(private val favGames: List<GameInfo>) :
    RecyclerView.Adapter<FavouritesListAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_fav, parent, false)
        )
    }

    override fun getItemCount() = favGames.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = favGames[position]
        Picasso.get()
            .load(Uri.parse(game.thumb_url)) // load the image
            .fit()
            .centerCrop()
            .into(holder.view.game_Image) // select the ImageView to load it into
        holder.view.game_Name.text = game.name
        //holder.view.game_Image.setImageResource(R.drawable.thumb_url)
    }

    class GameViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
