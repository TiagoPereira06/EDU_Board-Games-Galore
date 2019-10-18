package edu.isel.pdm.beegeesapp.bgg


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import edu.isel.pdm.beegeesapp.R
//import edu.isel.pdm.beegeesapp.view.GamesListAdapter

/**
 * A simple [Fragment] subclass.
 */
class PopularFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.title = "Popular"
        super.onActivityCreated(savedInstanceState)
        val games = listOf(
            Game(
                R.drawable.thumb,
                95,
                "Spirit Island",
                "Greater Than Games",
                53.45,
                4.97754
            ),
            Game(
                R.drawable.thumb,
                125,
                "GVgv HUUvwd",
                "Hbbfih DIUHUh IGGIg",
                10.45,
                1.97754
            ),
            Game(
                R.drawable.thumb,
                100,
                "KJBjib IBhgd",
                "Greater Than Games",
                22.45,
                2.97754
            ),
            Game(
                R.drawable.thumb,
                15,
                "SDJHwhd AJdihd",
                "Greater Than Games",
                20.45,
                2.97754
            ),
            Game(
                R.drawable.thumb,
                22,
                "Spirit Island",
                "Greater Than Games",
                41.45,
                1.97754
            ),
            Game(
                R.drawable.thumb,
                105,
                "Spirit Island",
                "Greater Than Games",
                124.45,
                0.97754
            )
        )

        /*popular_recycler_view.layoutManager = LinearLayoutManager(context)
        popular_recycler_view.adapter = GamesListAdapter(games)*/

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }


}
