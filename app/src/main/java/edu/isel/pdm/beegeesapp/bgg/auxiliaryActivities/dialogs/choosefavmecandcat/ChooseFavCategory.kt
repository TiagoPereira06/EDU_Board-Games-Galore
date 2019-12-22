package edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.choosefavmecandcat

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.get
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.DialogType
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.createnewlist.IChosenStringDialogListener
import edu.isel.pdm.beegeesapp.bgg.databaseUtils.Category

class ChooseFavCategory : AppCompatDialogFragment() {

    private lateinit var lastLetterClicked: CompoundButton
    private val map : HashMap<Char,MutableList<Category>> = HashMap()
    private lateinit var lettersChipGroup : ChipGroup
    private lateinit var categoryChipGroup : ChipGroup
    private lateinit var letterChip : Chip
    private lateinit var categoryChip : Chip
    private lateinit var applyB : ImageView




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

        lettersChipGroup = view.findViewById(R.id.letters1)
        categoryChipGroup = view.findViewById(R.id.categories)
        applyB = view.findViewById(R.id.saveB2)
        applyB.setOnClickListener {
            val checkedId = categoryChipGroup.checkedChipId
            if(checkedId!=-1) {
                val selectedChip = categoryChipGroup.findViewById(checkedId) as Chip
                (activity as (IChosenStringDialogListener)).chosenName(
                    selectedChip.text.toString(),
                    DialogType.NewCategory
                )
                dismiss()
            }
        }

        letterChip=view.findViewById(R.id.letter1ChipExample)
        letterChip.visibility = View.GONE
        categoryChip = view.findViewById(R.id.categoryChipExample)
        letterChip.visibility = View.GONE


        //CONVERTER PARA REPO
        val catList = mutableListOf<Category>()
        catList.add(Category("","A"))
        catList.add(Category("","AA"))
        catList.add(Category("","AAA"))
        catList.add(Category("","B"))
        catList.add(Category("","C"))
        catList.add(Category("","D"))

        fillMap(catList)

        //LETTER INIT
        initInitialLetters()
        changeMechanicLetter(lastLetterClicked.text[0])

        val alert = AlertDialog.Builder(activity)
        alert.setView(view)

        return alert.create()
    }

    private fun fillMap(mecList: MutableList<Category>) {
        mecList.forEach {
            if (map.containsKey(it.name[0]))
                map[it.name[0]]!!.add(it)
            else {
                val list = mutableListOf<Category>()
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
        categoryChipGroup.removeAllViews()
        for (mechanic in map[initialLetter]!!){
            val chip = Chip(context)
            chip.chipBackgroundColor = resources.getColorStateList(R.color.colorPrimary)
            chip.highlightColor = categoryChip.highlightColor
            chip.typeface = categoryChip.typeface
            chip.fontFeatureSettings = categoryChip.fontFeatureSettings
            chip.isCheckable = categoryChip.isCheckable
            chip.isCheckedIconVisible = categoryChip.isCheckedIconVisible
            chip.isClickable = categoryChip.isClickable
            chip.isFocusable = categoryChip.isFocusable
            chip.setTextColor(categoryChip.textColors)
            chip.text = mechanic.name
            categoryChipGroup.addView(chip)
        }
    }
}