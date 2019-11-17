package edu.isel.pdm.beegeesapp.bgg.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import edu.isel.pdm.beegeesapp.R
import kotlinx.android.synthetic.main.dialog_addtolists.*

class AddToListDialog(private val existingLists: MutableList<String>) : AppCompatDialogFragment() {

    private lateinit var chooseListView : AutoCompleteTextView
    private lateinit var addB : TextView
    private lateinit var cancelB : TextView
    private lateinit var title : TextView
    private lateinit var message : TextView
    private lateinit var adapter : ArrayAdapter<String>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = activity!!.layoutInflater.inflate(R.layout.dialog_addtolists, null)

        chooseListView = view.findViewById(R.id.autoCompleteTextView)
        title = view.findViewById(R.id.title)
        message = view.findViewById(R.id.adicionalMessage)
        adapter = ArrayAdapter(context!!,android.R.layout.simple_list_item_1,existingLists)

        if(existingLists.isNullOrEmpty()) { //MODO DE CRIAÇÃO DE LISTA
            title.text = "CREATE NEW LIST"
            message.text = "What is the name of the new list?"
            chooseListView.hint = "List Name"
        } else{
            chooseListView.threshold = 0
            chooseListView.setAdapter(adapter)
            chooseListView.setOnFocusChangeListener { _, b -> if (b) chooseListView.showDropDown()
            }
        }

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