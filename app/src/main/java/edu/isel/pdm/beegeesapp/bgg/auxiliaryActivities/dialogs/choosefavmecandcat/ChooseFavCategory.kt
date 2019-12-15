package edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.choosefavmecandcat

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.chip.ChipGroup
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.databaseUtils.Mechanic

class ChooseFavCategory : AppCompatDialogFragment() {

    private var initialLettersCategories = mutableListOf<String>()
    private var categories = mutableListOf<Mechanic>()
    private lateinit var lettersChipGroup : ChipGroup
    private lateinit var categoriesChipGroup : ChipGroup
    private lateinit var applyB : TextView
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
        val view: View = activity!!.layoutInflater.inflate(R.layout.dialog_choosecategory, null)
/*
        lettersChipGroup = view.findViewById(R.id.letters)
        mechanicsChipGroup = view.findViewById(R.id.mechanics)
        applyB = view.findViewById(R.id.applyButton)
        cancelB = view.findViewById(R.id.cancelButton)
        */


        val alert = AlertDialog.Builder(activity)
        alert.setView(view)




    return alert.create()
    }
}