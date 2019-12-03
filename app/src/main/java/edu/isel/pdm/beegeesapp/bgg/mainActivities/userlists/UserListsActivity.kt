package edu.isel.pdm.beegeesapp.bgg.mainActivities.userlists

import android.app.Activity
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
import edu.isel.pdm.beegeesapp.bgg.databaseUtils.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.GamesRepository
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.createnewlist.CreateNewListDialog
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.createnewlist.IChosenListDialogListener
import edu.isel.pdm.beegeesapp.bgg.mainActivities.userlists.detaileduserlists.ListDetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.mainActivities.userlists.view.ListsListAdapter
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_userlists.*

@Parcelize
class UserListsActivity : AppCompatActivity(),
    IChosenListDialogListener, Parcelable {


    private val REQUEST_CODE = 12
    private lateinit var listRvAdapter: ListsListAdapter
    private lateinit var deleteIcon : Drawable
    private lateinit var repo: GamesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repo = (application as BggApplication).repo
        setContentView(R.layout.activity_userlists)
        supportActionBar?.title = getString(R.string.dash_userListsInfo)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        deleteIcon = ContextCompat.getDrawable(this,R.drawable.deleteicon)!!

        //repo.clearAllCustomUserLists()

        val listRv = findViewById<RecyclerView>(R.id.lists_recycler_view)
        listRvAdapter =
            ListsListAdapter(application as BggApplication/*, repo.getAllCustomUserLists()*/) { listItem: CustomUserList ->
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
            listRvAdapter.notifyDataSetChanged()

        } else
            Toast.makeText(this, "List already exists", Toast.LENGTH_SHORT).show()
    }

    private fun listItemClicked(customUserList : CustomUserList) {
        val intent = Intent(this, ListDetailedViewActivity::class.java)
        intent.putExtra("LIST_OBJECT", customUserList)
        startActivityForResult(intent,REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val listToUpdate = data!!.getParcelableExtra<CustomUserList>("RETURN_LIST")
            repo.updateCustomUserList(listToUpdate)
        }
    }

    override fun onResume() {
        super.onResume()
        listRvAdapter.notifyDataSetChanged()
    }
}