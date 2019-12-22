package edu.isel.pdm.beegeesapp.bgg.userLists.ui

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.SimpleItemTouchHelper
import edu.isel.pdm.beegeesapp.bgg.database.CustomUserList
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.ui.GameDetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.userLists.view.ListDetailedViewAdapter
import kotlinx.android.synthetic.main.activity_detailedlistview.*

class ListDetailedViewActivity : AppCompatActivity() {

    private lateinit var listViewAdapter : ListDetailedViewAdapter

    private lateinit var currentCustomUserList : CustomUserList

    private lateinit var gamesList : MutableList<GameInfo>

    private lateinit var recyclerview : RecyclerView

    private lateinit var deleteIcon : Drawable

    private val CODE = "NUMBER_OF_GAMES"

    private var gamesRemoved : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        setContentView(R.layout.activity_detailedlistview)

        if (intent.hasExtra("LIST_OBJECT")) {

            currentCustomUserList = intent.getParcelableExtra("LIST_OBJECT")!!

            supportActionBar?.title = currentCustomUserList.listName

            gamesList = currentCustomUserList.gamesList

            deleteIcon = ContextCompat.getDrawable(this, R.drawable.deleteicon)!!

            //TODO -> layout já usado..
            recyclerview = findViewById(R.id.listofgames_recycler_view)

            //todo -> repetição de código -> GameItemClicked..
            listViewAdapter =
                ListDetailedViewAdapter(
                    currentCustomUserList
                ) { game: GameInfo ->
                    gameItemClicked(game)
                }

            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.isNestedScrollingEnabled = false
            recyclerview.adapter = listViewAdapter

            val itemTouchHelperCallback = SimpleItemTouchHelper(
                //OnMove
                { _, _, _ -> false },

                //OnSwiped
                { viewHolder, _ -> removeGameAtPosition(viewHolder.adapterPosition) },

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
            itemTouchHelper.attachToRecyclerView(recyclerview)

            if (savedInstanceState != null) {
                gamesRemoved = savedInstanceState.getInt(CODE)
            }


        }

        super.onCreate(savedInstanceState)
    }

    private fun removeGameAtPosition(position : Int) {
        val removedGame = gamesList[position]
        gamesList.removeAt(position)
        listViewAdapter.notifyItemRemoved(position)
        gamesRemoved++
        //TODO -> TRADUÇÃO E PLACEHOLDERS
        Snackbar
            .make(layout_games, "Game ${removedGame.name} deleted from list!", Snackbar.LENGTH_SHORT).setAction("UNDO") {
                gamesList.add(position, removedGame)
                listViewAdapter.notifyItemInserted(position)
                gamesRemoved--
            }
            .setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .show()
    }

    private fun gameItemClicked(gameItem: GameInfo) {
        val intent = Intent(this, GameDetailedViewActivity::class.java)
        intent.putExtra("GAME_OBJECT", gameItem)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        if (gamesRemoved > 0) {
            returnIntent.putExtra("RETURN_LIST", currentCustomUserList)
            setResult(Activity.RESULT_OK, returnIntent)
        }
        else {
            setResult(Activity.RESULT_CANCELED, returnIntent)
        }
        super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CODE, gamesRemoved)
        super.onSaveInstanceState(outState)
    }
}
