package edu.isel.pdm.beegeesapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_popular.*

/**
 * A simple [Fragment] subclass.
 */
class PopularFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //TEST FRAGMENT
        buttonTest.setOnClickListener {
            var fr = fragmentManager?.beginTransaction()
            fr?.replace(R.id.fragment_container, DetailedViewFragment())
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
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }




}
