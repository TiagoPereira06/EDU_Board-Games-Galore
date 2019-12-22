package edu.isel.pdm.beegeesapp.bgg.games.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.GamesInterface
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo

class GameViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {

    private val gameThumbnail: CircularImageView = view.findViewById(R.id.thumbGame)
    private val gameName: TextView = view.findViewById(R.id.game_Name)
    private val gamePublisher: TextView = view.findViewById(R.id.companyName)
    private val gameReviewersCount: TextView = view.findViewById(R.id.reviewersCount)
    private val gameRating: RatingBar = view.findViewById(R.id.ratingBar)
    private val cardLayout: ConstraintLayout = view.findViewById(R.id.card_constraintlayout)
    private val collectionImage: ImageView = view.findViewById(R.id.addToCollectionsImage)

    fun bindTo(game: GameInfo?, clickListener: (GameInfo) -> Unit, addToCollectionListener: (GameInfo) -> Unit) {
        if (game != null) {
            Picasso.get()
                .load(Uri.parse(game.thumb_url)) // load the image
                .fit()
                .centerCrop()
                .into(gameThumbnail) // select the ImageView to load it into

            gameName.text = game.name
            //TODO TRADUÇÃO & PLACEHOLDERS
            gameReviewersCount.text = "(${game.num_user_ratings} reviews)"
            gamePublisher.text = game.primary_publisher
            gameRating.rating = game.average_user_rating.toFloat()
            collectionImage.setOnClickListener { addToCollectionListener(game) }
            cardLayout.setOnClickListener { clickListener(game) }
        }
    }
}

    class GamesListAdapter(
        private val games : GamesInterface,
        private val clickListener: (GameInfo) -> Unit,
        private val addToCollectionListener: (GameInfo) -> Unit
    ) : RecyclerView.Adapter<GameViewHolder>() {

        override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
            holder.bindTo(
                games.getGames()[position],
                clickListener,
                addToCollectionListener
            )
        }

        override fun getItemCount(): Int = games.getNumberOfGames()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder =
            GameViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.card_game, parent, false) as ViewGroup
            )
    }
