package edu.isel.pdm.beegeesapp.bgg.chooselisttoadd

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import kotlinx.android.synthetic.main.card_chooselisttoadd.view.*
import kotlinx.android.synthetic.main.card_chooselisttoadd.view.listName
import kotlinx.android.synthetic.main.card_chooselisttoadd.view.listSize
import kotlinx.android.synthetic.main.card_chooselisttoadd.view.thumbList


class ChooseListAdapter(
    private val allCustomUserLists: MutableList<CustomUserList>,
    private val host: Activity,
    private val currentGame: GameInfo

): RecyclerView.Adapter<ChooseListAdapter.ChooseListViewHolder>() {

    val listsNamesToAddTheGame = mutableListOf<String>()
    val listsNamesToRemoveTheGame = mutableListOf<String>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseListViewHolder {
        return ChooseListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_chooselisttoadd, parent, false)
        )
    }

    override fun getItemCount() = allCustomUserLists.size


    override fun onBindViewHolder(holder: ChooseListViewHolder, position: Int) {
        val list = allCustomUserLists[position]
        holder.view.listName.text = list.listName
        holder.view.listSize.text = "(" + list.gamesList.size.toString() + " games)"
        val drawableResourceId: Int =
            host.resources.getIdentifier(list.drawableResourceName, "drawable", host.packageName)
        holder.view.thumbList.setImageResource(drawableResourceId)
        val check = holder.view.chechBoxtoAdd
        check.isChecked = list.gamesList.contains(currentGame)
        check.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked and (!listsNamesToAddTheGame.contains(list.listName))) {
                listsNamesToAddTheGame.add(list.listName)
                listsNamesToRemoveTheGame.remove(list.listName)
            } else if(!isChecked and (!listsNamesToRemoveTheGame.contains(list.listName))){
                listsNamesToRemoveTheGame.add(list.listName)
                listsNamesToAddTheGame.remove(list.listName)
        }
        }






    }


















    class ChooseListViewHolder(val view : View) : RecyclerView.ViewHolder(view)
}