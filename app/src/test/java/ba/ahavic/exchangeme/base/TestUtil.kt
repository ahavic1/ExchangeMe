package ba.ahavic.exchangeme.base

import ba.ahavic.exchangeme.data.di.GetRatesDeserializer
import ba.ahavic.exchangeme.data.models.RatesApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.reflect.Type

object TestUtil {

    private const val mocksPath = "../app/src/test/java/ba/ahavic/exchangeme/mocks/"
    private const val ratesMock = "rates.json"
    private const val ratesAudMock = "rates_aud.json"

    private val mocksMap: HashMap<Type, Any> = hashMapOf()

    private val gson: Gson by lazy {
        GsonBuilder().registerTypeAdapter(RatesApi::class.java, GetRatesDeserializer()).create()
    }

    fun getRates(): RatesApi {
        val type = object : TypeToken<RatesApi>() {}.type
        val cached = mocksMap[type]
        return if (cached != null) {
            cached as RatesApi
        } else {
            val rates = loadJson<RatesApi>(ratesMock, type)
            mocksMap[type] = rates
            rates
        }
    }

    fun getRatesAUD(): RatesApi = loadJson(ratesAudMock, object : TypeToken<RatesApi>() {}.type)

    private inline fun <reified T> loadJson(file: String, type: Type): T {
        val sb = StringBuilder()
        BufferedReader(InputStreamReader(FileInputStream(mocksPath + file))).use {
            var line = it.readLine()
            while (line != null) {
                sb.append(line)
                line = it.readLine()
            }
        }
        return gson.fromJson(sb.toString(), type)
    }
}
