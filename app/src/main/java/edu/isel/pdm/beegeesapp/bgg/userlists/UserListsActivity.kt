package edu.isel.pdm.beegeesapp.bgg.userlists

import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.userlists.detaileduserlists.ListDetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.dialogs.createnewlist.CreateNewListDialog
import edu.isel.pdm.beegeesapp.bgg.dialogs.createnewlist.IChosenListDialogListener
import edu.isel.pdm.beegeesapp.bgg.GameInfo
import edu.isel.pdm.beegeesapp.bgg.userlists.model.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.userlists.model.UserListContainer
import edu.isel.pdm.beegeesapp.bgg.userlists.view.ListsListAdapter
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_userlists.*

@Parcelize
class UserListsActivity : AppCompatActivity(),
    IChosenListDialogListener, Parcelable {
    companion object {
        var listContainer = UserListContainer()
    }

    private lateinit var listRvAdapter: ListsListAdapter
    private lateinit var deleteIcon : Drawable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlists)
        listContainer.addList("Black Friday")
        listContainer.addList("Wish List")
        listContainer.addList("Joe's Fav")
        listContainer.addList("Family Night")
        listContainer.addList("Worse Games")
        listContainer.addList("Marta's Fav")
        listContainer.addList("Home Alone")
        listContainer.addList("Keep Practising")
        listContainer.addList("Want to Try")
        listContainer.addList("Already Purchased")

        val gameList = mutableListOf<GameInfo>()
           gameList.add(GameInfo("Teste1"))
           gameList.add(GameInfo("Teste2"))
           gameList.add(GameInfo("Teste3"))
           gameList.add(GameInfo("Teste4"))
           gameList.add(GameInfo("Teste5"))
           gameList.add(GameInfo("Teste6"))
           gameList.add(GameInfo("Teste7"))
           gameList.add(GameInfo("Teste8"))
           gameList.add(GameInfo("Teste9"))
           gameList.add(GameInfo("Teste10"))
           gameList.add(GameInfo("Teste11"))
           gameList.add(GameInfo("Teste12"))
           gameList.add(GameInfo("Teste13"))



        listContainer.userLists[0].list = gameList


        supportActionBar?.title = getString(R.string.dash_userListsInfo)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        deleteIcon = ContextCompat.getDrawable(this,R.drawable.deleteicon)!!

        val listRv = findViewById<RecyclerView>(R.id.lists_recycler_view)
         listRvAdapter = ListsListAdapter(listContainer,this) { listItem: CustomUserList ->
            listItemClicked(listItem)}
        listRv.layoutManager = LinearLayoutManager(this)
        listRv.adapter = listRvAdapter

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                (listRv.adapter as ListsListAdapter).removeItem(viewHolder)

            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2

                if (dX>0){
                    deleteIcon.setBounds(itemView.left + iconMargin, itemView.top+iconMargin, itemView.left + iconMargin + deleteIcon.intrinsicWidth,itemView.bottom-iconMargin)
                }
                deleteIcon.draw(c)
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(listRv)

        addListButton.setOnClickListener {
            val dialog =
                CreateNewListDialog()
            dialog.show(supportFragmentManager,"New List Dialog")
        }

    }
    override fun chosenListName(name: String) {
        if(listContainer.userLists.find { it.listName == name }==null) {
            listContainer.addList(name)
            Toast.makeText(this, "List created", Toast.LENGTH_SHORT).show()

        }else
            Toast.makeText(this,"List already exists",Toast.LENGTH_SHORT).show()
    }

    private fun listItemClicked(listItem: CustomUserList) {
        val intent = Intent(this, ListDetailedViewActivity::class.java)
        intent.putExtra("LIST_OBJECT", listItem)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        listRvAdapter.notifyDataSetChanged()
    }
}