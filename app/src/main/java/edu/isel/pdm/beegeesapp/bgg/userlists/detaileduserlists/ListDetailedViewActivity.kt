package edu.isel.pdm.beegeesapp.bgg.userlists.detaileduserlists

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.userlists.UserListsActivity
import edu.isel.pdm.beegeesapp.bgg.userlists.detaileduserlists.view.GamesinListAdapter
import edu.isel.pdm.beegeesapp.bgg.userlists.model.CustomUserList

class ListDetailedViewActivity: AppCompatActivity() {

    private  lateinit var gamesRvAdapter: GamesinListAdapter
    private lateinit var currentList : CustomUserList
    private var editMode = false
    private var userListContainer = UserListsActivity.listContainer
    private lateinit var gamesRv : RecyclerView
    private lateinit var deleteIcon : MenuItem
    private lateinit var saveIcon : MenuItem
    private lateinit var editIcon : FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        setContentView(R.layout.activity_detailedlistview)
        if (intent.hasExtra("LIST_OBJECT")) {
            currentList = intent.getParcelableExtra("LIST_OBJECT") as CustomUserList
            currentList = userListContainer.userLists.find { it.listName == currentList.listName }!!
            supportActionBar?.title =currentList.listName
            //val drawableResourceId: Int = resources.getIdentifier(currentList.drawableResourceName, "drawable", packageName)
            //thumbList.setImageResource(drawableResourceId)

            gamesRv = findViewById(R.id.listofgames_recycler_view)
            editIcon = findViewById(R.id.editButton)

            editIcon.setOnClickListener {
                editMode=true
                gamesRv.adapter = GamesinListAdapter(currentList,editMode)
                gamesRvAdapter = gamesRv.adapter as GamesinListAdapter
                deleteIcon.isVisible=true
                saveIcon.isVisible=true
                editIcon.hide()
            }

            gamesRvAdapter = GamesinListAdapter(currentList,editMode)
            gamesRv.layoutManager = LinearLayoutManager(this)
            gamesRv.isNestedScrollingEnabled = false
            gamesRv.adapter=gamesRvAdapter
        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_editormodelist, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        deleteIcon = menu?.findItem(R.id.deleteIcon)!!
        saveIcon = menu.findItem(R.id.saveIcon)!!
        deleteIcon.isVisible = false
        saveIcon.isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteIcon -> {
                deleteIcon.isVisible = false
                gamesRvAdapter.removeItems(this)
            }

            R.id.saveIcon -> {
                saveIcon.isVisible = false
                editIcon.show()
                editMode=false
                gamesRv.adapter = GamesinListAdapter(currentList,editMode)
                gamesRvAdapter = gamesRv.adapter as GamesinListAdapter
            }

        }
        return true
    }
}