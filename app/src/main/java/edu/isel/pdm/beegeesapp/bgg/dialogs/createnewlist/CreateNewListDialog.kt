package edu.isel.pdm.beegeesapp.bgg.dialogs.createnewlist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import edu.isel.pdm.beegeesapp.R

class CreateNewListDialog : AppCompatDialogFragment() {

    private lateinit var chooseListView : AutoCompleteTextView
    private lateinit var addB : TextView
    private lateinit var cancelB : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = activity!!.layoutInflater.inflate(R.layout.dialog_createnewlist, null)

        chooseListView = view.findViewById(R.id.autoCompleteTextView)

        this.addB = view.findViewById(R.id.addButton)
        this.cancelB = view.findViewById(R.id.canButton)

        val alert = AlertDialog.Builder(activity)
        alert.setView(view)

        this.addB.setOnClickListener {
            (activity as (IChosenListDialogListener)).chosenListName(this.chooseListView.text.toString())
            dismiss()
        }

        this.cancelB.setOnClickListener {
            dismiss()
        }

        return alert.create()

    }
}