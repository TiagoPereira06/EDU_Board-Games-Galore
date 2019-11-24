package edu.isel.pdm.beegeesapp.bgg.userlists.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.userlists.model.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.userlists.model.UserListContainer
import kotlinx.android.synthetic.main.card_list.view.*


class ListsListAdapter(
    private val userListContainer : UserListContainer,
    private val host : AppCompatActivity,
    private val listClickListener : (CustomUserList) -> Unit

    ):RecyclerView.Adapter<ListsListAdapter.ListViewHolder>() {


    private var removedPosition : Int = 0
    private var removedList : CustomUserList? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_list,parent,false)
        )
    }

    override fun getItemCount() = userListContainer.userLists.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val list = userListContainer.userLists[position]
        holder.view.listName.text = list.listName
        holder.view.listSize.text = "(" + list.list.size.toString() + " games)"
        val drawableResourceId: Int = host.resources.getIdentifier(list.drawableResourceName, "drawable", host.packageName)
        holder.view.thumbList.setImageResource(drawableResourceId)
        holder.view.setOnClickListener{
            listClickListener(list)
        }
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        removedPosition=viewHolder.adapterPosition
        removedList=userListContainer.userLists[viewHolder.adapterPosition]

        userListContainer.userLists.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        Snackbar
            .make(viewHolder.itemView, removedList!!.listName+" Deleted",Snackbar.LENGTH_LONG).setAction("UNDO") {
                userListContainer.addListAt(removedList!!, removedPosition)
                notifyItemInserted(removedPosition)
            }.setActionTextColor(ContextCompat.getColor(host,R.color.colorPrimary))
            .show()



    }

    class ListViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
