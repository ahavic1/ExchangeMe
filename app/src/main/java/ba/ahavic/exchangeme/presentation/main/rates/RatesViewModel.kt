package ba.ahavic.exchangeme.presentation.main.rates

import ba.ahavic.exchangeme.data.rates.RatesRepository
import ba.ahavic.exchangeme.presentation.base.BaseViewModel
import javax.inject.Inject

class RatesViewModel @Inject constructor(
    private val repository: RatesRepository
) : BaseViewModel() {

}