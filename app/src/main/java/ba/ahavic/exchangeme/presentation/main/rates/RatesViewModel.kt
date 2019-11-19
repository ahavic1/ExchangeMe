package ba.ahavic.exchangeme.presentation.main.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ba.ahavic.exchangeme.core.extensions.greaterThanZero
import ba.ahavic.exchangeme.core.extensions.round
import ba.ahavic.exchangeme.data.models.Currency
import ba.ahavic.exchangeme.data.models.Rate
import ba.ahavic.exchangeme.data.rates.RatesRepository
import ba.ahavic.exchangeme.presentation.base.BaseViewModel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.consumeEach
import java.math.BigDecimal
import javax.inject.Inject

class RatesViewModel @Inject constructor(
    private val repository: RatesRepository
) : BaseViewModel(), RatesAdapterListener {

    private var baseRate: Rate = Rate(currency = Currency.EUR, amount = BigDecimal(1))

    private val rates: MutableList<Rate> = mutableListOf()

    private val _ratesView = MutableLiveData<List<RatesView>>()
    val ratesView: LiveData<List<RatesView>> = _ratesView

    override fun onViewResume() {
        super.onViewResume()
        getRates()
    }

    override fun onViewPause() {
        super.onViewPause()
        viewModelScope.coroutineContext.cancelChildren()
    }

    override fun onRateSelected(position: Int) {
        _ratesView.value?.get(position)?.let { ratesView ->
            viewModelScope.coroutineContext.cancelChildren()
            baseRate = rates.removeAt(position).copy(amount = ratesView.amount.toBigDecimal())
            rates.add(0, baseRate)
            getRates()
        }
    }

    override fun onRateEdited(newValue: String, rate: RatesView) {
        if (newValue != baseRate.amount.toString()) {
            baseRate.amount = if (newValue.isNotEmpty()) newValue.toBigDecimal() else 0.toBigDecimal()
            renderRates()
        }
    }

    private fun getRates() {
        launch {
            repository.getRates(baseRate.currency.name).consumeEach { ratesApi ->
                updateRates(ratesApi.rates)
                renderRates()
            }
        }
    }

    private fun updateRates(ratesApi: List<Rate>) {
        if (rates.isNotEmpty()) {
            ratesApi.forEach { rateApi ->
                val index = rates.indexOfFirst { it.currency == rateApi.currency }
                rates[index] = rateApi
            }
        } else {
            rates.add(baseRate)
            rates.addAll(ratesApi)
        }
    }

    private fun renderRates() {
        _ratesView.value = rates.mapIndexed { index, rate ->
            RatesView(
                currencyCode = rate.currency.name,
                currency = rate.currency.currencyName,
                icon = rate.currency.icon,
                amount = if (index == 0) parseAmount(rate.amount)
                else parseAmount(rate.amount * baseRate.amount)
            )
        }
    }

    private fun parseAmount(amount: BigDecimal): String {
        return if (amount.greaterThanZero()) {
            amount.round(2).stripTrailingZeros().toPlainString()
        } else {
            "0"
        }
    }
}

data class RatesView(
    val currencyCode: String,
    val currency: Int,
    val icon: Int,
    val amount: String
)
