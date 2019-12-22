package edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.model.GameProfile
import kotlinx.android.synthetic.main.card_gameprofile.view.*

class ProfileListAdapter(
    private val host: BggApplication,
    private val allProfilesList : MutableList<GameProfile>
    ) :RecyclerView.Adapter<ProfileListAdapter.GameProfileViewHolder>() {

    private var removedPosition : Int = 0
    private var removedProfile : GameProfile? = null


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
        val drawableResourceId: Int = host.resources.getIdentifier(gameProfile.drawableResourceName, "drawable", host.packageName)
        holder.view.thumbProfile.setImageResource(drawableResourceId)
        holder.view.categoryChip.text = gameProfile.categoryName
        holder.view.mechanicChip.text = gameProfile.mechanicName
        holder.view.artistNameProfile.text = "Artist: " + gameProfile.artist
        holder.view.publisherNameProfile.text = "Publisher: "+gameProfile.publisher

    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        removedPosition=viewHolder.adapterPosition
        removedProfile=allProfilesList[viewHolder.adapterPosition]

        allProfilesList.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        Snackbar
            .make(viewHolder.itemView, removedProfile!!.name+" Deleted", Snackbar.LENGTH_LONG).setAction("UNDO") {
                allProfilesList.add(removedPosition,removedProfile!!)
                notifyItemInserted(removedPosition)
            }.setActionTextColor(ContextCompat.getColor(host,R.color.colorPrimary))
            .show()
    }


    class GameProfileViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}