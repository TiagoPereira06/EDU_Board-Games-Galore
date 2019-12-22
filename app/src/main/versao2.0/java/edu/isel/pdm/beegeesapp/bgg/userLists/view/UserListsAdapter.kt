package edu.isel.pdm.beegeesapp.bgg.userLists.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.userLists.model.UserListsViewModel
import edu.isel.pdm.beegeesapp.bgg.database.CustomUserList
import kotlinx.android.synthetic.main.card_list.view.*

class ListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(list : CustomUserList?, host: Activity, listOnClickListener : (CustomUserList) -> Unit) {
        if (list == null) return
        view.listName.text = list.listName
        //TODO -> TRADUÇÃO E PLACEHOULDERS
        view.listSize.text = "(" + list.gamesList.size.toString() + " games)"
        val drawableResourceId: Int = host.resources.getIdentifier(list.drawableResourceName, "drawable", host.packageName)
        view.thumbList.setImageResource(drawableResourceId)
        view.setOnClickListener{
            listOnClickListener(list)
        }
    }
}

class ListsOfListsAdapter(
    private val viewModel: UserListsViewModel,
    private val host : Activity,
    private val listOnClickListener : (CustomUserList) -> Unit
    ):RecyclerView.Adapter<ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_list, parent, false)
        )
    }

    override fun getItemCount() = viewModel.getUserListsSize()

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindTo(viewModel.getUserLists().value?.get(position), host, listOnClickListener)
    }
}
