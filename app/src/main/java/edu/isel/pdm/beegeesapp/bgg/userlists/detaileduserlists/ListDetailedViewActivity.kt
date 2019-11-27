package edu.isel.pdm.beegeesapp.bgg.userlists.detaileduserlists

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
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
     lateinit var deleteMenuItem : MenuItem
    private lateinit var saveMenuItem : MenuItem
    private lateinit var editMenuItem : FloatingActionButton
    private lateinit var deleteIcon : Drawable



    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        setContentView(R.layout.activity_detailedlistview)
        if (intent.hasExtra("LIST_OBJECT")) {
            currentList = intent.getParcelableExtra("LIST_OBJECT") as CustomUserList
            currentList = userListContainer.userLists.find { it.listName == currentList.listName }!!
            supportActionBar?.title =currentList.listName
            deleteIcon = ContextCompat.getDrawable(this,R.drawable.deleteicon)!!

            //val drawableResourceId: Int = resources.getIdentifier(currentList.drawableResourceName, "drawable", packageName)
            //thumbList.setImageResource(drawableResourceId)

            gamesRv = findViewById(R.id.listofgames_recycler_view)
            editMenuItem = findViewById(R.id.editButton)

            editMenuItem.setOnClickListener {
                editMode=true
                gamesRv.adapter = GamesinListAdapter(currentList,editMode)
                gamesRvAdapter = gamesRv.adapter as GamesinListAdapter
                deleteMenuItem.isVisible=true
                saveMenuItem.isVisible=true
                editMenuItem.hide()
            }

            gamesRvAdapter = GamesinListAdapter(currentList,editMode)
            gamesRv.layoutManager = LinearLayoutManager(this)
            gamesRv.isNestedScrollingEnabled = false
            gamesRv.adapter=gamesRvAdapter


            val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                    (gamesRv.adapter as GamesinListAdapter).removeItem(viewHolder,this@ListDetailedViewActivity)

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
            itemTouchHelper.attachToRecyclerView(gamesRv)



        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_editormodelist, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        deleteMenuItem = menu?.findItem(R.id.deleteIcon)!!
        saveMenuItem = menu.findItem(R.id.saveIcon)!!
        deleteMenuItem.isVisible = false
        saveMenuItem.isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteIcon -> {
                deleteMenuItem.isVisible = false
                gamesRvAdapter.removeItems(this)
            }

            R.id.saveIcon -> {
                saveMenuItem.isVisible = false
                editMenuItem.show()
                editMode=false
                gamesRv.adapter = GamesinListAdapter(currentList,editMode)
                gamesRvAdapter = gamesRv.adapter as GamesinListAdapter
            }

        }
        return true
    }
}