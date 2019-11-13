package ba.ahavic.exchangeme.data.rates

import ba.ahavic.exchangeme.data.base.BaseRepository
import ba.ahavic.exchangeme.data.models.RatesApi
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface RatesRepository {
    @FlowPreview
    suspend fun getRates(baseCurrency: String = "EUR"): Flow<RatesApi>
}

class RatesRepositoryImpl @Inject constructor(
    private val client: RatesClient
) : BaseRepository(), RatesRepository {

    @FlowPreview
    override suspend fun getRates(baseCurrency: String): Flow<RatesApi> {
        TODO("not implemented")
    }
}

interface RatesClient {
    @GET("/latest")
    fun getRatesAsync(@Query("base") base: String) : Deferred<Response<RatesApi>>
}
