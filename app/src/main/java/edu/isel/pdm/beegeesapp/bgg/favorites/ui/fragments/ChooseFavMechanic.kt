package edu.isel.pdm.beegeesapp.bgg.favorites.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.dialog.DialogType
import edu.isel.pdm.beegeesapp.bgg.dialog.IChosenStringDialogListener
import edu.isel.pdm.beegeesapp.bgg.favorites.model.MechanicsViewModel


class ChooseFavMechanic : AppCompatDialogFragment() {

    private lateinit var lastLetterClicked: CompoundButton
    private lateinit var lettersChipGroup : ChipGroup
    private lateinit var mechanicsChipGroup : ChipGroup

    private lateinit var letterChip : Chip
    private lateinit var mechanicChip : Chip
    private lateinit var applyB : ImageView

    private lateinit var mechanics : MechanicsViewModel

    private fun getMechanicsViewModel(application: BggApplication) = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MechanicsViewModel(application) as T
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = activity!!.layoutInflater.inflate(R.layout.dialog_choosemechanic, null)

        lettersChipGroup = view.findViewById(R.id.letters)
        mechanicsChipGroup = view.findViewById(R.id.mechanics)

        applyB = view.findViewById(R.id.saveB)
        applyB.setOnClickListener {
            val chipId = mechanicsChipGroup.checkedChipId
            if (chipId != -1) {
                val selectedChip = mechanicsChipGroup.findViewById(chipId) as Chip
                (activity as (IChosenStringDialogListener)).chosenName(
                    selectedChip.text.toString(),
                    DialogType.NewMechanic
                )
                dismiss()
            }
        }
        letterChip = view.findViewById(R.id.letterChipExample)
        letterChip.visibility = View.GONE
        mechanicChip = view.findViewById(R.id.mechanicChipExample)
        letterChip.visibility = View.GONE


        mechanics = ViewModelProviders
            .of(this, getMechanicsViewModel(activity!!.application as BggApplication))
            .get(MechanicsViewModel::class.java)

        mechanics.mechanics.observe(this, Observer {
            val map = getMapOfMechanics(mechanics.mechanics.value!!)
            initMechanicsChips(map)

        })




        if (savedInstanceState == null) {
            mechanics.getMechanics {
                //On Fail
                Toast.makeText(
                    activity,
                    "Could not retrieve mechanics. Check internet connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val alert = AlertDialog.Builder(activity)
        alert.setView(view)

        return alert.create()
    }

    @SuppressLint("ResourceType")
    private fun initMechanicsChips(map: MutableMap<Char, List<String>>) {
        for (key in map.keys) {
            val chip = Chip(context)
            chip.typeface = letterChip.typeface
            chip.chipBackgroundColor = letterChip.chipBackgroundColor
            chip.fontFeatureSettings = letterChip.fontFeatureSettings
            chip.isCheckable = letterChip.isCheckable
            chip.isCheckedIconVisible = letterChip.isCheckedIconVisible
            chip.isClickable = letterChip.isClickable
            chip.isFocusable = letterChip.isFocusable
            chip.setTextColor(letterChip.textColors)
            chip.text = key.toString()

            //TODO:(MELHORAR COMPORTAMENTO)
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    lastLetterClicked = buttonView
                    updateMechanics(map, buttonView.text[0])
                } /*else if (lastLetterClicked.text.toString() == buttonView.text.toString())
                    buttonView.isChecked = true
                    */
            }
            lettersChipGroup.addView(chip)
        }
        //TODO - MELHORAR -> COMO? NÃO SEI
        lettersChipGroup.check(1)//A
        lastLetterClicked = lettersChipGroup[1] as CompoundButton
    }

    private fun updateMechanics(map : Map<Char, List<String>>, mechanicLetter: Char) {
        mechanicsChipGroup.removeAllViews()

        for (mechanic in map[mechanicLetter]!!) {
            val chip = Chip(context)
            chip.chipBackgroundColor = resources.getColorStateList(R.color.colorPrimaryDark)
            chip.highlightColor = mechanicChip.highlightColor
            chip.typeface = mechanicChip.typeface
            chip.fontFeatureSettings = mechanicChip.fontFeatureSettings
            chip.isCheckable = mechanicChip.isCheckable
            chip.isCheckedIconVisible = mechanicChip.isCheckedIconVisible
            chip.isClickable = mechanicChip.isClickable
            chip.isFocusable = mechanicChip.isFocusable
            chip.setTextColor(mechanicChip.textColors)
            chip.text = mechanic
            mechanicsChipGroup.addView(chip)
        }
    }


    private fun getMapOfMechanics(mechanics : List<String>): MutableMap<Char, List<String>> {
        val map : MutableMap<Char, List<String>> = mutableMapOf()
        var entries : MutableList<String> = mutableListOf()
        var lastLetter : Char? = null

        for (mechanicString in mechanics) {
            if (lastLetter != mechanicString[0]) {
                if (lastLetter != null) {
                    map[lastLetter] = entries
                }
                lastLetter = mechanicString[0]
                entries = mutableListOf()
            }
            entries.add(mechanicString)
        }
        if (lastLetter != null) map[lastLetter] = entries
        return map
    }
}