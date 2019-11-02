package edu.isel.pdm.beegeesapp.bgg.games.view

import android.net.Uri
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
import com.squareup.picasso.Picasso
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesViewModel

class GameViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {

    private val gameThumbnail: CircularImageView = view.findViewById(R.id.thumbGame)
    private val gameName: TextView = view.findViewById(R.id.game_Name)
    private val gamePublisher: TextView = view.findViewById(R.id.companyName)
    private val gameReviewersCount: TextView = view.findViewById(R.id.reviewersCount)
    private val gameRating: RatingBar = view.findViewById(R.id.ratingBar)
    private val heartButton: LikeButton = view.findViewById(R.id.heart_button)
    private val cardLayout: ConstraintLayout = view.findViewById(R.id.card_constraintlayout)

    fun bindTo(game: GameInfo, clickListener: (GameInfo) -> Unit) {
        Picasso.get()
            .load(Uri.parse(game.thumb_url)) // load the image
            .fit()
            .centerCrop()
            .into(gameThumbnail) // select the ImageView to load it into
        gameName.text = game.name
        gameReviewersCount.text = "(${game.num_user_ratings} reviews)"
        //TODO
        gamePublisher.text = publishersText(game.publishers)
        gameRating.rating = game.average_user_rating.toFloat()
        //TODO:LISTENER FAVORITOS
        //heartButton.isLiked = contains...

        cardLayout.setOnClickListener { clickListener(game) }
        heartButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                Log.v("DEBUG", "LIKED")
            }

            override fun unLiked(likeButton: LikeButton?) {
                Log.v("DEBUG", "UNLIKED")
            }

        })

    }

    private fun publishersText(publishers: List<String>): String {
        var res = ""
        val aux = ", "
        for (element in publishers) {
            res += "$element, "
        }
        return res.removeSuffix(aux)
    }

    class GamesListAdapter(
        private val viewModel: GamesViewModel,
        private val clickListener: (GameInfo) -> Unit
    ) : RecyclerView.Adapter<GameViewHolder>() {

        override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
            holder.bindTo(viewModel.content.value?.get(position)!!, clickListener)
        }

        override fun getItemCount(): Int = viewModel.content.value?.size ?: 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder =
            GameViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.cardview_game_layout, parent, false) as ViewGroup
            )
    }
}