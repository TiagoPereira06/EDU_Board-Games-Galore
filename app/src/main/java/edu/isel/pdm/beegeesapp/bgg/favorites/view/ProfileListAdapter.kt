package edu.isel.pdm.beegeesapp.bgg.favorites.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.database.GameProfile
import edu.isel.pdm.beegeesapp.bgg.favorites.model.FavoritesViewModel
import kotlinx.android.synthetic.main.card_gameprofile.view.*

class ProfileListAdapter(
    private val host: BggApplication,
    private val favoritesViewModel : FavoritesViewModel,
    private val profileOnClickListener : (GameProfile) -> Unit
) :RecyclerView.Adapter<ProfileListAdapter.GameProfileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameProfileViewHolder {
        return GameProfileViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_gameprofile,parent,false)
        )
    }

    override fun getItemCount() = favoritesViewModel.favorites.value?.size ?: 0

    override fun onBindViewHolder(holder: GameProfileViewHolder, position: Int) {
        val gameProfile = favoritesViewModel.favorites.value!![position]
        holder.view.profileName.text = gameProfile.name
        val drawableResourceId: Int = host.resources.getIdentifier(gameProfile.drawableResourceName, "drawable", host.packageName)
        holder.view.thumbProfile.setImageResource(drawableResourceId)
        if (gameProfile.categoryName == "") {
            holder.view.categoryChip.visibility = View.INVISIBLE
        } else
            holder.view.categoryChip.text = gameProfile.categoryName
        if (gameProfile.mechanicName == "") {
            holder.view.mechanicChip.visibility = View.INVISIBLE
        } else
            holder.view.mechanicChip.text = gameProfile.mechanicName

        if (gameProfile.designer != "") holder.view.artistNameProfile.text = gameProfile.designer
        else holder.view.artistNameProfile.visibility = View.INVISIBLE

        if (gameProfile.publisher != "") holder.view.publisherNameProfile.text =
            gameProfile.publisher
        else holder.view.publisherNameProfile.visibility = View.INVISIBLE
        holder.view.setOnClickListener {
            profileOnClickListener(gameProfile)
        }

    }
    class GameProfileViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}