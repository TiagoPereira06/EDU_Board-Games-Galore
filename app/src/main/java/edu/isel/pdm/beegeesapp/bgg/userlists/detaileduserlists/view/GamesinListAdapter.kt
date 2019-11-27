package edu.isel.pdm.beegeesapp.bgg.userlists.detaileduserlists.view

import android.app.Activity
import android.net.Uri
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.userlists.detaileduserlists.ListDetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.userlists.model.CustomUserList
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.card_gamelists.view.*


class GamesinListAdapter(
    private val listToShow: CustomUserList,
    private val editorMode: Boolean

): RecyclerView.Adapter<GamesinListAdapter.GameinListHolder>() {

    private val gamesToRemove = mutableListOf<GamePosObj>()
    private lateinit var holder: GameinListHolder

    private var removedPosition : Int = 0
    private var removedGame : GameInfo? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameinListHolder {
        return GameinListHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_gamelists, parent, false)
        )
    }

    override fun getItemCount() = listToShow.list.size

    override fun onBindViewHolder(holder: GameinListHolder, position: Int) {
        this.holder = holder
        val currentGame = listToShow.list[position]
        val obj = GamePosObj(holder.adapterPosition,currentGame)
        val check = holder.view.checkBox as CheckBox
        check.isChecked = false

        if(editorMode) {
            holder.view.checkBox.visibility = View.VISIBLE
            val check = holder.view.checkBox as CheckBox
            //check.isChecked = false
            check.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked /*and (!gamesToRemove.contains(obj))*/) {
                    gamesToRemove.add(obj)
                } else if(!isChecked /*and (!gamesToRemove.contains(obj))*/){
                    gamesToRemove.remove(obj)
                }
            }

        }else holder.view.checkBox.visibility = View.INVISIBLE

        Picasso.get()
            .load(Uri.parse(currentGame.thumb_url)) // load the image
            .fit()
            .centerCrop()
            .into(holder.view.thumbGameinList) // select the ImageView to load it into
        holder.view.gameinList_Name.text = currentGame.name

    }

    fun removeItems(host : Activity){
        var counter = 0

        gamesToRemove.sortWith(compareBy {it.pos})
        gamesToRemove.forEach {
            listToShow.list.remove(it.game)
               notifyItemRemoved(it.pos - (counter++))

        }
        if(gamesToRemove.isNotEmpty()) {
            Snackbar
                .make(holder.itemView, "Item(s) removed", Snackbar.LENGTH_LONG).setAction("UNDO") {
                    gamesToRemove.forEach { undo ->
                        listToShow.list.add(undo.pos, undo.game)
                        notifyItemInserted(undo.pos)
                    }
                    (host as ListDetailedViewActivity).deleteMenuItem.isVisible=true
                }.setActionTextColor(ContextCompat.getColor(host, R.color.colorPrimary))
                .show()
        }


    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder,host : Activity) {
        removedPosition=viewHolder.adapterPosition
        removedGame=listToShow.list[viewHolder.adapterPosition]

        listToShow.list.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        Snackbar
            .make(viewHolder.itemView, removedGame!!.name+" Deleted",Snackbar.LENGTH_LONG).setAction("UNDO") {
                listToShow.list.add(removedPosition,removedGame!!)
                notifyItemInserted(removedPosition)
            }.setActionTextColor(ContextCompat.getColor(host,R.color.colorPrimary))
            .show()
    }


    class GameinListHolder(val view: View) : RecyclerView.ViewHolder(view)

}

@Parcelize
data class GamePosObj(
    val pos : Int ,
    val game : GameInfo
) : Parcelable