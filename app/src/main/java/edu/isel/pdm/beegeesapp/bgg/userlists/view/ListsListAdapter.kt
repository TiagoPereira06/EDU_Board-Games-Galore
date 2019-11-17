package edu.isel.pdm.beegeesapp.bgg.userlists.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.userlists.model.UserListContainer
import kotlinx.android.synthetic.main.card_list.view.*


class ListsListAdapter(private val userListContainer : UserListContainer,private val host:AppCompatActivity) :
    RecyclerView.Adapter<ListsListAdapter.ListViewHolder>() {

    val prefix = "samplegameicons"

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
        host.registerForContextMenu(holder.view)
    }

    class ListViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
