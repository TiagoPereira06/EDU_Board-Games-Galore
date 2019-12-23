package edu.isel.pdm.beegeesapp.bgg.userLists.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.SimpleItemTouchHelper
import edu.isel.pdm.beegeesapp.bgg.database.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.dialog.CreateNewListDialog
import edu.isel.pdm.beegeesapp.bgg.dialog.DialogType
import edu.isel.pdm.beegeesapp.bgg.dialog.IChosenStringDialogListener
import edu.isel.pdm.beegeesapp.bgg.userLists.UserListsActivity
import edu.isel.pdm.beegeesapp.bgg.userLists.view.ListsOfListsAdapter
import kotlinx.android.synthetic.main.recycler_view_floating_button.*

class UserLists : UserListsActivity(),
    IChosenStringDialogListener {

    private val CODE : Int = 20

    private lateinit var listsAdapter : ListsOfListsAdapter

    override fun initView() {
        super.initView()

        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.deleteicon)!!

        val itemTouchHelperCallback = SimpleItemTouchHelper(
                //OnMove
                { _, _, _ -> false },

                //OnSwiped
                { viewHolder, _ -> removeListAtPosition(viewHolder.adapterPosition) },

                //onChildDrawOver
                { c, _, viewHolder, dX, _, _, _ ->
                    val itemView = viewHolder.itemView
                    val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2

                    if (dX > 0) {
                        deleteIcon.setBounds(
                            itemView.left + iconMargin,
                            itemView.top + iconMargin,
                            itemView.left + iconMargin + deleteIcon.intrinsicWidth,
                            itemView.bottom - iconMargin
                        )
                    }
                    deleteIcon.draw(c)
                })

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerView.adapter = ListsOfListsAdapter(viewModel, this)
        { listItem: CustomUserList -> listItemClicked(listItem) }
        listsAdapter = recyclerView.adapter as ListsOfListsAdapter
    }

    override fun initBehavior(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.getLists {
                if (it.size == 0) {
                    //TODO -> TRADUÇÃO
                    Toast.makeText(this, "Your Collection is empty!", Toast.LENGTH_SHORT).show()
                } else {
                    listsAdapter.notifyDataSetChanged()
                }
            }
        }

        addListButton.setOnClickListener {
            val dialog = CreateNewListDialog()
            dialog.show(supportFragmentManager, "New List Dialog")
        }
    }


    private fun removeListAtPosition(position: Int) {
        val listToRemove = viewModel.getUserLists().value!![position]
        viewModel.removeCustomUserList(
            listToRemove,
            {
                //Removed with success
                listsAdapter.notifyItemRemoved(position)
                createUndoOption(listToRemove, position)
            },
            {
                //Removed failed
                //TODO -> TRADUÇÃO E PLACEHOLDERS
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            })

    }

    private fun createUndoOption(listRemoved: CustomUserList, oldPosition: Int) {
        //TODO -> TRADUÇÃO E PLACEHOLDERS
        Snackbar.make(
            layout_userLists,
            "List ${listRemoved.listName} deleted",
            Snackbar.LENGTH_LONG
        )
            .setAction("UNDO") {
                //TODO -> TRADUÇÃO E PLACEHOLDERS
                viewModel.addCustomUserList(listRemoved, oldPosition) {
                    listsAdapter.notifyItemInserted(oldPosition)
                    //TODO -> TRADUÇÃO E PLACEHOLDERS
                    Snackbar.make(
                        layout_userLists,
                        "List ${listRemoved.listName} restored",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
            .setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .show()
    }

    override fun chosenName(name: String, type: DialogType) {
        viewModel.addCustomUserListByName(name,
            {
                // List added
                //TODO -> TRADUÇÃO E PLACEHOLDERS
                Toast.makeText(this, " List $name created with success!", Toast.LENGTH_SHORT).show()
                listsAdapter.notifyItemInserted(viewModel.getUserListsSize() - 1)
            },
            {
                // List not added
                //TODO -> TRADUÇÃO E PLACEHOLDERS
                Toast.makeText(this, "There´s already a List with that name.", Toast.LENGTH_SHORT)
                    .show()
            }
        )
    }

    private fun listItemClicked(customUserList : CustomUserList) {
        val intent = Intent(this, ListDetailedViewActivity::class.java)
        intent.putExtra("LIST_OBJECT", customUserList)
        startActivityForResult(intent, CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val updatedList = data!!.getParcelableExtra("RETURN_LIST") as CustomUserList
            val list = viewModel.getUserLists().value!!.find { it.listName == updatedList.listName } as CustomUserList
            val position = viewModel.getUserLists().value!!.indexOf(list)
            list.gamesList = updatedList.gamesList
            viewModel.updateUserList(updatedList) { listsAdapter.notifyItemChanged(position) }
        }
    }

}