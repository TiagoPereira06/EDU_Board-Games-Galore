package edu.isel.pdm.beegeesapp.bgg.favorites.ui.fragments

import androidx.lifecycle.MutableLiveData
import edu.isel.pdm.beegeesapp.R

class ChooseFavCategory : FavoriteDetailsFragment() {

    override fun initViews() {
        fragment = activity!!.layoutInflater.inflate(R.layout.dialog_choosecategory, null)
        letterChip = fragment.findViewById(R.id.letter1ChipExample)
        detailChip = fragment.findViewById(R.id.categoryChipExample)
        lettersChipGroup = fragment.findViewById(R.id.letters1)
        detailsChipGroup = fragment.findViewById(R.id.categories)
        applyButton = fragment.findViewById(R.id.saveB2)
    }

    override fun getDetails(): MutableLiveData<List<String>> {
        return viewModel.getCategories()
    }

    override fun searchDetails(onFail: () -> Unit) {
        viewModel.searchCategories(onFail)
    }

    override fun getSelectedDetail(): String? {
        return viewModel.getSelectedCategory()
    }

    override fun setSelectedDetail(detail: String) {
        viewModel.setSelectedCategory(detail)
    }

}