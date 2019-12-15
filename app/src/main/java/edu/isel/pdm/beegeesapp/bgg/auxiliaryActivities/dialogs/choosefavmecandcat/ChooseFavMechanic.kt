package edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.choosefavmecandcat

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.get
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.DialogType
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.createnewlist.IChosenStringDialogListener
import edu.isel.pdm.beegeesapp.bgg.databaseUtils.Mechanic

class ChooseFavMechanic : AppCompatDialogFragment() {

    private lateinit var lastLetterClicked: CompoundButton
    private val map : HashMap<Char,MutableList<Mechanic>> = HashMap()
    private lateinit var lettersChipGroup : ChipGroup
    private lateinit var mechanicsChipGroup : ChipGroup
    private lateinit var letterChip : Chip
    private lateinit var mechanicChip : Chip
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
        val view: View = activity!!.layoutInflater.inflate(R.layout.dialog_choosemechanic, null)

        lettersChipGroup = view.findViewById(R.id.letters)
        mechanicsChipGroup = view.findViewById(R.id.mechanics)
        applyB = view.findViewById(R.id.applyButton3)
        applyB.setOnClickListener {
            //TODO:(MELHORAR)
            val selectedChip = mechanicsChipGroup.findViewById(mechanicsChipGroup.checkedChipId) as Chip
            if(selectedChip != null) {
                (activity as (IChosenStringDialogListener)).chosenName(
                    selectedChip.text.toString(),
                    DialogType.NewMechanic
                )
                dismiss()
            }
        }
        cancelB = view.findViewById(R.id.cancelButton)
        cancelB.setOnClickListener {
            dismiss()
        }

        letterChip=view.findViewById(R.id.letterChipExample)
        letterChip.visibility = View.GONE
        mechanicChip = view.findViewById(R.id.mechanicChipExample)
        letterChip.visibility = View.GONE


        //CONVERTER PARA REPO
        val mecList = mutableListOf<Mechanic>()
        mecList.add(Mechanic("","A"))
        mecList.add(Mechanic("","AA"))
        mecList.add(Mechanic("","AAA"))
        mecList.add(Mechanic("","B"))
        mecList.add(Mechanic("","C"))
        mecList.add(Mechanic("","D"))

        fillMap(mecList)

        //LETTER INIT
        initInitialLetters()
        changeMechanicLetter(lastLetterClicked.text[0])

        val alert = AlertDialog.Builder(activity)
        alert.setView(view)

        return alert.create()
    }

    private fun fillMap(mecList: MutableList<Mechanic>) {
        mecList.forEach {
            if (map.containsKey(it.name[0]))
                map[it.name[0]]!!.add(it)
            else {
                val list = mutableListOf<Mechanic>()
                list.add(it)
                map[it.name[0]] = list
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun initInitialLetters() {
        for (char in map.keys) {
            val chip = Chip(context)
            chip.typeface = letterChip.typeface
            chip.chipBackgroundColor = letterChip.chipBackgroundColor
            chip.fontFeatureSettings = letterChip.fontFeatureSettings
            chip.isCheckable = letterChip.isCheckable
            chip.isCheckedIconVisible = letterChip.isCheckedIconVisible
            chip.isClickable = letterChip.isClickable
            chip.isFocusable = letterChip.isFocusable
            chip.setTextColor(letterChip.textColors)
            chip.text = char.toString()

            //TODO:(MELHORAR COMPORTAMENTO)
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    lastLetterClicked = buttonView
                    changeMechanicLetter(buttonView.text[0])
                } /*else if (lastLetterClicked.text.toString() == buttonView.text.toString())
                    buttonView.isChecked = true
                    */
            }
            lettersChipGroup.addView(chip)
        }
        lettersChipGroup.check(1)//A
        lastLetterClicked = lettersChipGroup[1] as CompoundButton
    }

    private fun changeMechanicLetter(
        initialLetter: Char) {
        mechanicsChipGroup.removeAllViews()
        for (mechanic in map[initialLetter]!!){
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
            chip.text = mechanic.name
            mechanicsChipGroup.addView(chip)
        }
    }
}