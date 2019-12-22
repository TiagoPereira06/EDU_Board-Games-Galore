package edu.isel.pdm.beegeesapp.bgg.dialog


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import edu.isel.pdm.beegeesapp.R
import kotlinx.android.synthetic.main.dialog_chooselistthumb.*

class ChooseListThumbDialog : AppCompatDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = activity!!.layoutInflater.inflate(R.layout.dialog_chooselistthumb, null)

        table


        val alert = AlertDialog.Builder(activity)
        alert.setView(view)


        return alert.create()
    }
}