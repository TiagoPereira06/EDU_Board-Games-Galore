package edu.isel.pdm.beegeesapp.bgg.userLists.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.database.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.userLists.model.UserListsViewModel

class ChooseListViewHolder(val view: ViewGroup) : RecyclerView.ViewHolder(view) {

    private val checkBox: CheckBox = view.findViewById(R.id.chechBoxToAdd)
    private val listName: TextView = view.findViewById(R.id.listName)
    private val listSize: TextView = view.findViewById(R.id.listSize)
    private val image: ImageView = view.findViewById(R.id.thumbList)

    fun bindTo(list: CustomUserList?, host: Activity, position: Int, isListChecked : Boolean, listener: (Boolean, CustomUserList, Int) -> Unit) {
        if (list != null) {
            listName.text = list.listName

            // TODO -> https://stackoverflow.com/questions/33164886/android-textview-do-not-concatenate-text-displayed-with-settext
            // TODO -> USAR PLACEHOLDERS -> RESOURCE STRING
            listSize.text = "(${list.gamesList.size} games)"

            image.setImageResource(
                host.resources.getIdentifier(
                    list.drawableResourceName,
                    "drawable",
                    host.packageName
                )
            )
            checkBox.isChecked = isListChecked
            checkBox.setOnCheckedChangeListener { _, isChecked -> listener(isChecked, list, position) }
        }
    }
}

class AddGameToCollectionAdapter(
    private val viewModel: UserListsViewModel,
    private val host: Activity,
    private val onCheckedChangeListener: (Boolean, CustomUserList, Int) -> Unit
) : RecyclerView.Adapter<ChooseListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseListViewHolder {
        return ChooseListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_chooselisttoadd, parent, false) as ViewGroup
        )
    }

    override fun getItemCount() = viewModel.getUserListsSize()

    override fun onBindViewHolder(holder: ChooseListViewHolder, position: Int) {
        holder.bindTo(
            viewModel.getUserLists().value?.get(position),
            host,
            position,
            viewModel.getCheckedLists()[position],
            onCheckedChangeListener
        )
    }

}