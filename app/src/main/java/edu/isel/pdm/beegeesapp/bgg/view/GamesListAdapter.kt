package edu.isel.pdm.beegeesapp.bgg.view

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.like.LikeButton
import com.like.OnLikeListener
import com.mikhaellopez.circularimageview.CircularImageView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.model.GamesViewModel
import edu.isel.pdm.beegeesapp.bgg.model.GameInfo

class GameViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {

    private val gameThumbnail: CircularImageView = view.findViewById(R.id.thumbGame)
    private val gameName: TextView = view.findViewById(R.id.gameName)
    private val gamePublisher: TextView = view.findViewById(R.id.companyName)
    private val gameReviewersCount: TextView = view.findViewById(R.id.reviewersCount)
    private val gameRating: RatingBar = view.findViewById(R.id.ratingBar)
    private val heartButton: LikeButton = view.findViewById(R.id.heart_button)
    private val cardLayout: ConstraintLayout = view.findViewById(R.id.card_constraintlayout)

    fun bindTo(game: GameInfo, clickListener: (GameInfo) -> Unit) {
        gameThumbnail.setImageResource(R.drawable.thumb)
        gameName.text = game.name
        gameReviewersCount.text = "(" + game.numberUserReviews.toString() + " reviews )"
        gamePublisher.text = game.publisher
        gameRating.rating = game.averageUserRating.toFloat()
        //TODO:LISTENER FAVORITOS
        //heartButton.isLiked = contains...

        cardLayout.setOnClickListener{clickListener(game)}
        heartButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                Log.v("DEBUG","LIKED")
            }

            override fun unLiked(likeButton: LikeButton?) {
                Log.v("DEBUG","UNLIKED")
            }

        })

    }

    class GamesListAdapter(private val viewModel : GamesViewModel,val clickListener: (GameInfo) -> Unit) : RecyclerView.Adapter<GameViewHolder>() {

        override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
            holder.bindTo(viewModel.games[position],clickListener)
        }
        override fun getItemCount() : Int = viewModel.games.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder =
            GameViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.cardview_game_layout, parent, false) as ViewGroup
            )
    }
}