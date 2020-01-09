package edu.isel.pdm.beegeesapp.bgg.userLists.view

import android.net.Uri
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.database.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.card_gamelists.view.*


class ListDetailedViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(game: GameInfo, clickListener: (GameInfo) -> Unit) {
        Picasso.get()
            .load(Uri.parse(game.thumb_url))
            .fit()
            .centerCrop()
            .into(view.thumbGameinList)

        view.gameinList_Name.text = game.name
        view.setOnClickListener { clickListener(game) }
    }
}

class ListDetailedViewAdapter(
    private val userList: CustomUserList,
    private val clickListener: (GameInfo) -> Unit
): RecyclerView.Adapter<ListDetailedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDetailedViewHolder {
        return ListDetailedViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_gamelists, parent, false)
        )
    }

    override fun getItemCount() = userList.gamesList.size

    override fun onBindViewHolder(holder: ListDetailedViewHolder, position: Int) {
        holder.bindTo(userList.gamesList[position], clickListener)
    }





}
