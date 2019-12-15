package edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.createnewlist

import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.DialogType

interface IChosenStringDialogListener {
    fun chosenName(name : String, type : DialogType)
}