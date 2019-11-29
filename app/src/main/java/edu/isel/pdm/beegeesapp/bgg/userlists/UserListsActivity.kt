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
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.userlists.detaileduserlists.ListDetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.dialogs.createnewlist.CreateNewListDialog
import edu.isel.pdm.beegeesapp.bgg.dialogs.createnewlist.IChosenListDialogListener
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.userlists.view.ListsListAdapter
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_userlists.*

@Parcelize
class UserListsActivity : AppCompatActivity(),
    IChosenListDialogListener, Parcelable {

    val repo = (application as BggApplication).repo


    private lateinit var listRvAdapter: ListsListAdapter
    private lateinit var deleteIcon : Drawable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlists)
        repo.addCustomUserList("TesteLista")
        repo.addCustomUserList("Black Friday")
        repo.addCustomUserList("Wish List")
        repo.addCustomUserList("Joe's Fav")
        repo.addCustomUserList("Family Night")
        repo.addCustomUserList("Worse Games")
        repo.addCustomUserList("Marta's Fav")
        repo.addCustomUserList("Home Alone")
        repo.addCustomUserList("Keep Practising")
        repo.addCustomUserList("Want to Try")
        repo.addCustomUserList("Already Purchased")

        repo.addGameToCustomUserList("TesteList",GameInfo("Teste1"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste2"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste3"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste4"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste5"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste6"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste7"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste8"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste9"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste10"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste11"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste12"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste13"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste14"))
        repo.addGameToCustomUserList("TesteList",GameInfo("Teste15"))



        supportActionBar?.title = getString(R.string.dash_userListsInfo)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        deleteIcon = ContextCompat.getDrawable(this,R.drawable.deleteicon)!!

        val listRv = findViewById<RecyclerView>(R.id.lists_recycler_view)
        listRvAdapter = ListsListAdapter(this) { listItem: CustomUserList ->
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
        if (repo.addCustomUserList(name)) {
            Toast.makeText(this, "List created", Toast.LENGTH_SHORT).show()

        } else
            Toast.makeText(this, "List already exists", Toast.LENGTH_SHORT).show()
    }

    private fun listItemClicked(customUserList : CustomUserList) {
        val intent = Intent(this, ListDetailedViewActivity::class.java)
        intent.putExtra("LIST_OBJECT", customUserList)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        listRvAdapter.notifyDataSetChanged()
    }
}