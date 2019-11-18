package ba.ahavic.exchangeme.data.models

import java.math.BigDecimal

data class RatesApi(val base: String, val date: String, val rates: List<Rate>)

data class Rate(val currency: Currency, var amount: BigDecimal)


