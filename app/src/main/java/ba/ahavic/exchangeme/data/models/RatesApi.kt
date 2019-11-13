package ba.ahavic.exchangeme.data.models

data class RatesApi(val base: String, val date: String, val rates: List<RateApi>)

data class RateApi(val currency: String, val amount: Double)
