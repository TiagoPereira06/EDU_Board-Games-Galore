package edu.isel.pdm.beegeesapp.bgg.userlists

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.dialogs.AddToListDialog
import edu.isel.pdm.beegeesapp.bgg.dialogs.IChosenListDialogListener
import edu.isel.pdm.beegeesapp.bgg.userlists.model.UserListContainer
import edu.isel.pdm.beegeesapp.bgg.userlists.view.ListsListAdapter
import kotlinx.android.synthetic.main.activity_userlists.*

class UserListsActivity : AppCompatActivity(),
    IChosenListDialogListener {

    var listContainer = UserListContainer()
    private var listsNames = mutableListOf<String>()

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
        listsNames = listContainer.getAllListsNames()


        supportActionBar?.title = getString(R.string.dash_userListsInfo)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))

        val favRv = findViewById<RecyclerView>(R.id.lists_recycler_view)
        val myAdapter = ListsListAdapter(listContainer,this)
        favRv.layoutManager = LinearLayoutManager(this)
        favRv.adapter = myAdapter

        addListButton.setOnClickListener {
            val dialog = AddToListDialog(mutableListOf()) //LISTA VAZIA = MODO CRIAÇÃO
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
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_longpress_list,menu)

    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete ->{
                //TODO(REAGIR INTERAÇÃO)
                Toast.makeText(applicationContext,"Delete List",Toast.LENGTH_SHORT).show()
            }
            R.id.editName ->{
                //TODO(REAGIR INTERAÇÃO)
                Toast.makeText(applicationContext,"Edit List Name",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onContextItemSelected(item)
    }
}