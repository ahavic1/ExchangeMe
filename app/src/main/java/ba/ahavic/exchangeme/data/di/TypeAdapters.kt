package ba.ahavic.exchangeme.data.di

import ba.ahavic.exchangeme.data.models.Currency
import ba.ahavic.exchangeme.data.models.Rate
import ba.ahavic.exchangeme.data.models.RatesApi
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import javax.inject.Inject

class GetRatesDeserializer @Inject constructor(): JsonDeserializer<RatesApi> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RatesApi {
        val jsonObject = json?.asJsonObject!!

        val base = jsonObject.get("base").asString
        val date = jsonObject.get("date").asString
        val rates = jsonObject.get("rates").asJsonObject.entrySet().map {
            val currency = Currency.valueOf(it.key.toUpperCase())
            Rate(currency, it.value.asBigDecimal)
        }

        return RatesApi(
            base,
            date,
            rates
        )
    }
}
