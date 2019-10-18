package edu.isel.pdm.beegeesapp.bgg


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import edu.isel.pdm.beegeesapp.R
import kotlinx.android.synthetic.main.fragment_favourites.*

/**
 * A simple [Fragment] subclass.
 */
class FavouritesFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Favourites"
        //TEST FRAGMENT
        buttonTest.setOnClickListener {
            var fr = fragmentManager?.beginTransaction()
            fr?.replace(
                R.id.fragment_container,
                DetailedViewFragment()
            )
            fr?.commit()
            /*val intent = Intent (activity, DetailedViewActivity::class.java)
            activity?.startActivity(intent)*/
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

}
