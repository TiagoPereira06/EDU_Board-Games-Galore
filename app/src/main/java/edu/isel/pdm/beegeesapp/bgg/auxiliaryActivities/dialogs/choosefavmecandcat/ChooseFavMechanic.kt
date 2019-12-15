package edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.choosefavmecandcat

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.databaseUtils.Mechanic
import kotlinx.android.synthetic.main.dialog_choosemechanic.*

class ChooseFavMechanic : AppCompatDialogFragment() {

    private var initialLettersMechanics = mutableListOf<String>()
    private var mechanics = mutableListOf<Mechanic>()
    private lateinit var lettersChipGroup : ChipGroup
    private lateinit var mechanicsChipGroup : ChipGroup
    private lateinit var letterChip : Chip
    private lateinit var mechanicChip : Chip



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

        letterChip=view.findViewById<Chip>(R.id.letterChipExample)
        letterChip.visibility = View.GONE
        mechanicChip = view.findViewById<Chip>(R.id.mechanicChipExample)
        letterChip.visibility = View.GONE


        //CONVERTER PARA REPO
        val mecList = mutableListOf<Mechanic>()

        val map : HashMap<Char,MutableList<Mechanic>> = HashMap()

        mecList.forEach{
            if(map.containsKey(it.name[0]))
            map[it.name[0]]!!.add(it)
            else {
                val list = mutableListOf<Mechanic>()
                list.add(it)
                map[it.name[0]] = list
            }
        }



        //EXTRAIR PROPRIEDADES E REPLICAR!

        //LETTER INIT
        for (i in 0 until 25){
            val chip = Chip(context)
            chip.typeface = letterChip.typeface
            chip.chipBackgroundColor = letterChip.chipBackgroundColor
            chip.fontFeatureSettings = letterChip.fontFeatureSettings
            chip.isCheckable = letterChip.isCheckable
            chip.isCheckedIconVisible = letterChip.isCheckedIconVisible
            chip.isClickable = letterChip.isClickable
            chip.isFocusable = letterChip.isFocusable
            chip.setTextColor(letterChip.textColors)
            chip.text = i.toString()
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    changeMechanicLetter(buttonView.text[0])
                }/*else
                    buttonView.isChecked = true*/
            }
            lettersChipGroup.addView(chip)
        }
        lettersChipGroup.check(1)//A


        //MECHANIC INIT
        for (i in 0 until 12){
            val chip = Chip(context)
            chip.chipBackgroundColor = mechanicChip.chipBackgroundColor
           // chip.backgroundTintMode = mechanicChip.backgroundTintMode
            chip.highlightColor = mechanicChip.highlightColor
            chip.typeface = mechanicChip.typeface
            chip.chipBackgroundColor = mechanicChip.chipBackgroundColor
            chip.fontFeatureSettings = mechanicChip.fontFeatureSettings
            chip.isCheckable = mechanicChip.isCheckable
            chip.isCheckedIconVisible = mechanicChip.isCheckedIconVisible
            chip.isClickable = mechanicChip.isClickable
            chip.isFocusable = mechanicChip.isFocusable
            chip.setTextColor(mechanicChip.textColors)
            chip.text = "Mechanic$i"
/*            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked) Toast.makeText(context,buttonView.text.toString(),Toast.LENGTH_LONG).show()
            }*/
            mechanicsChipGroup.addView(chip)
        }





        val alert = AlertDialog.Builder(activity)
        alert.setView(view)

    return alert.create()
    }

    private fun changeMechanicLetter(initialLetter: Char) {

    }
}