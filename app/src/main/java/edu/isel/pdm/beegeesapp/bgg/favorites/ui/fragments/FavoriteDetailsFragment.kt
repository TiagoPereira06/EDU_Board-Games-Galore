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
import androidx.lifecycle.*
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.dialog.DialogType
import edu.isel.pdm.beegeesapp.bgg.dialog.IChosenStringDialogListener
import edu.isel.pdm.beegeesapp.bgg.favorites.model.GameDetailsViewModel

abstract class FavoriteDetailsFragment : AppCompatDialogFragment() {

    private lateinit var lastLetterClicked: CompoundButton

    protected lateinit var lettersChipGroup: ChipGroup

    protected lateinit var detailsChipGroup: ChipGroup

    protected lateinit var letterChip: Chip

    protected lateinit var detailChip: Chip

    private var firstChip: Chip? = null

    protected lateinit var applyButton: ImageView

    protected lateinit var viewModel: GameDetailsViewModel

    protected lateinit var fragment: View

    abstract fun initViews()

    abstract fun getDetails(): MutableLiveData<List<String>>

    abstract fun searchDetails(onFail: () -> Unit)

    abstract fun getSelectedDetail(): String?

    abstract fun setSelectedDetail(detail: String)

    private fun getGameDetailsViewModel(application: BggApplication) =
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return GameDetailsViewModel(application) as T
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
        initViews()

        letterChip.visibility = View.GONE

        detailChip.visibility = View.GONE

        initModel()

        getDetails().observe(this, Observer {
            initChips(getMapOfDetails(getDetails().value!!))
        })

        if (savedInstanceState == null) {
            searchDetails {
                //On Fail
                Toast.makeText(
                    activity,
                    "Could not retrieve data. Check you internet connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        applyButton.setOnClickListener {
            val chipId = detailsChipGroup.checkedChipId
            if (chipId > 0) {
                val selectedChip = detailsChipGroup.findViewById(chipId) as Chip
                (activity as (IChosenStringDialogListener)).chosenName(
                    selectedChip.text.toString(),
                    DialogType.NewCategory
                )
            }
            dismiss()
        }

        val alert = AlertDialog.Builder(activity)
        alert.setView(fragment)

        return alert.create()
    }

    private fun initModel() {
        viewModel = ViewModelProviders
            .of(this, getGameDetailsViewModel(activity!!.application as BggApplication))
            .get(GameDetailsViewModel::class.java)
    }


    @SuppressLint("ResourceType")
    private fun initChips(detailsMap: MutableMap<Char, List<String>>) {

        for (key in detailsMap.keys) {
            val chip = Chip(context)
            if (firstChip == null) firstChip = chip
            chip.typeface = letterChip.typeface
            chip.chipBackgroundColor = letterChip.chipBackgroundColor
            chip.fontFeatureSettings = letterChip.fontFeatureSettings
            chip.isCheckable = letterChip.isCheckable
            chip.isCheckedIconVisible = letterChip.isCheckedIconVisible
            chip.isClickable = letterChip.isClickable
            chip.isFocusable = letterChip.isFocusable
            chip.setTextColor(letterChip.textColors)
            chip.text = key.toString()

            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    lastLetterClicked = buttonView
                    setSelectedDetail(chip.text.toString())
                    updateChips(detailsMap, lastLetterClicked.text[0])
                } else if (lastLetterClicked.text.toString() == buttonView.text.toString()) {
                    detailsChipGroup.removeAllViews()
                }
            }

            lettersChipGroup.addView(chip)

            if (chip.text.toString() == getSelectedDetail()) {
                lettersChipGroup.check(chip.id)
            }

        }

        if (lettersChipGroup.checkedChipId < 0) {
            lettersChipGroup.check(firstChip!!.id)
        }
        lastLetterClicked = lettersChipGroup[1] as CompoundButton
    }

    private fun updateChips(detailsMap: MutableMap<Char, List<String>>, letter: Char) {
        detailsChipGroup.removeAllViews()

        for (detail in detailsMap.getValue(letter)) {
            val chip = Chip(context)
            chip.chipBackgroundColor = resources.getColorStateList(R.color.colorPrimary)
            chip.highlightColor = detailChip.highlightColor
            chip.typeface = detailChip.typeface
            chip.fontFeatureSettings = detailChip.fontFeatureSettings
            chip.isCheckable = detailChip.isCheckable
            chip.isCheckedIconVisible = detailChip.isCheckedIconVisible
            chip.isClickable = detailChip.isClickable
            chip.isFocusable = detailChip.isFocusable
            chip.setTextColor(detailChip.textColors)
            chip.text = detail
            detailsChipGroup.addView(chip)
        }
    }

    private fun getMapOfDetails(details: List<String>): MutableMap<Char, List<String>> {
        val map: MutableMap<Char, List<String>> = mutableMapOf()
        var entries: MutableList<String> = mutableListOf()
        var lastLetter: Char? = null

        for (detail in details) {
            if (lastLetter != detail[0]) {
                if (lastLetter != null) {
                    map[lastLetter] = entries
                }
                lastLetter = detail[0]
                entries = mutableListOf()
            }
            entries.add(detail)
        }
        if (lastLetter != null) map[lastLetter] = entries
        return map
    }

}