package edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.model.GameProfile
import kotlinx.android.synthetic.main.card_gameprofile.view.*

class ProfileListAdapter(
    private val host: BggApplication,
    private val allProfilesList : MutableList<GameProfile>

    ) :RecyclerView.Adapter<ProfileListAdapter.GameProfileViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameProfileViewHolder {
        return GameProfileViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_gameprofile,parent,false)
        )
    }


    override fun getItemCount() = allProfilesList.size

    override fun onBindViewHolder(holder: GameProfileViewHolder, position: Int) {
        //val list = repo.getAllCustomUserLists()[position]
        val gameProfile = allProfilesList[position]
        holder.view.profileName.text = gameProfile.name
        val drawableResourceId: Int = host.resources.getIdentifier(gameProfile.iconString, "drawable", host.packageName)
        holder.view.thumbProfile.setImageResource(drawableResourceId)
        holder.view.categoryChip.text = gameProfile.categoryName
        holder.view.mechanicChip.text = gameProfile.mechanicName
        holder.view.artistNameProfile.text = "Artist: " + gameProfile.artist
        holder.view.publisherNameProfile.text = "Publisher: "+gameProfile.publisher

    }

    class GameProfileViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}