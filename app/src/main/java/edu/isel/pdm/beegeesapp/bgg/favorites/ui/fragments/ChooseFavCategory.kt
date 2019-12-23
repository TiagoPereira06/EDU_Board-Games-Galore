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
import edu.isel.pdm.beegeesapp.bgg.favorites.model.CategoriesViewModel

class ChooseFavCategory : AppCompatDialogFragment() {

    private lateinit var lastLetterClicked: CompoundButton
    private lateinit var lettersChipGroup : ChipGroup
    private lateinit var categoriesChipGroup : ChipGroup
    private lateinit var letterChip : Chip
    private lateinit var categoryChip : Chip
    private lateinit var applyB : ImageView

    private lateinit var categories : CategoriesViewModel

    private fun getCategoriesViewModel(application: BggApplication) = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CategoriesViewModel(application) as T
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
        val view: View = activity!!.layoutInflater.inflate(R.layout.dialog_choosecategory, null)

        lettersChipGroup = view.findViewById(R.id.letters1)
        categoriesChipGroup = view.findViewById(R.id.categories)
        applyB = view.findViewById(R.id.saveB2)
        applyB.setOnClickListener {
            val chipId = categoriesChipGroup.checkedChipId
            if (chipId != -1) {
                val selectedChip = categoriesChipGroup.findViewById(chipId) as Chip
                (activity as (IChosenStringDialogListener)).chosenName(
                    selectedChip.text.toString(),
                    DialogType.NewCategory
                )
            }
            dismiss()
        }

        letterChip = view.findViewById(R.id.letter1ChipExample)
        letterChip.visibility = View.GONE
        categoryChip = view.findViewById(R.id.categoryChipExample)
        letterChip.visibility = View.GONE


        categories = ViewModelProviders
            .of(this, getCategoriesViewModel(activity!!.application as BggApplication))
            .get(CategoriesViewModel::class.java)

        categories.categories.observe(this, Observer {
            val map = getMapOfCategories(categories.categories.value!!)
            initCategoriesChip(map)
        })

        if (savedInstanceState == null) {
            categories.getCategories {
                //On Fail
                Toast.makeText(activity, "Could not retrieve categories. Check internet connection.",
                    Toast.LENGTH_SHORT).show()
            }
        }

        val alert = AlertDialog.Builder(activity)
        alert.setView(view)

    return alert.create()
    }


    //TODO -> REPETIÇÃO DE CÓDIGO

    @SuppressLint("ResourceType")
    private fun initCategoriesChip(map: MutableMap<Char, List<String>>) {
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
                    updateCategories(map, buttonView.text[0])
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


    private fun updateCategories(map : Map<Char, List<String>>, categoryLetter: Char) {
        categoriesChipGroup.removeAllViews()

        for (category in map.getValue(categoryLetter)) {
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
            chip.text = category
            categoriesChipGroup.addView(chip)
        }
    }


    private fun getMapOfCategories(categories : List<String>): MutableMap<Char, List<String>> {
        val map : MutableMap<Char, List<String>> = mutableMapOf()
        var entries : MutableList<String> = mutableListOf()
        var lastLetter : Char? = null

        for (categoryString in categories) {
            if (lastLetter != categoryString[0]) {
                if (lastLetter != null) {
                    map[lastLetter] = entries
                }
                lastLetter = categoryString[0]
                entries = mutableListOf()
            }
            entries.add(categoryString)
        }
        if (lastLetter != null) map[lastLetter] = entries
        return map
    }
}