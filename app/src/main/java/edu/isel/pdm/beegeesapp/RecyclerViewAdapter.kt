package edu.isel.pdm.beegeesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cardview_game_layout.view.*

class RecyclerViewAdapter(private val games: List<Game>) :
    RecyclerView.Adapter<RecyclerViewAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_game_layout, parent, false)
        )
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.view.gameName.text = game.name
        holder.view.companyName.text = game.publisher
        //holder.view.gamePrice.text = game.price.toString() + " €"
        holder.view.thumbGame.setImageResource(R.drawable.thumb)
        holder.view.reviewersCount.text = "(" + game.numberUserReviews.toString() + " reviews )"
        holder.view.ratingBar.rating = game.averageUserRating.toFloat()

        holder.view.card_constraintlayout.setOnClickListener {
            /* val intent = Intent(this, DetailedViewActivity::class.java)
             intent.putExtra("Game Object", game)
             startActivity(intent)*/

            Toast.makeText(it.context, "You clicked -> " + game.name, Toast.LENGTH_LONG).show()
        }

        //holder.view.hearth_button.setOnLikeListener()

    }


    class GameViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}