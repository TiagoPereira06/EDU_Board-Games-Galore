package edu.isel.pdm.beegeesapp.bgg.favorites.ui.fragments

import androidx.lifecycle.MutableLiveData
import edu.isel.pdm.beegeesapp.R

class ChooseFavMechanic : FavoriteDetailsFragment() {

    override fun initViews() {
        fragment = activity!!.layoutInflater.inflate(R.layout.dialog_choosemechanic, null)
        letterChip = fragment.findViewById(R.id.letterChipExample)
        detailChip = fragment.findViewById(R.id.mechanicChipExample)
        lettersChipGroup = fragment.findViewById(R.id.letters)
        detailsChipGroup = fragment.findViewById(R.id.mechanics)
        applyButton = fragment.findViewById(R.id.saveB)
    }

    override fun getDetails(): MutableLiveData<List<String>> {
        return viewModel.getMechanics()
    }

    override fun searchDetails(onFail: () -> Unit) {
        viewModel.searchMechanics(onFail)
    }

    override fun getSelectedDetail(): String? {
        return viewModel.getSelectedMechanic()
    }

    override fun setSelectedDetail(detail: String) {
        viewModel.setSelectedMechanic(detail)
    }
}