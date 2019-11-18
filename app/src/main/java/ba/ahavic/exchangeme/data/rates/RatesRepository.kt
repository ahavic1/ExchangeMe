package ba.ahavic.exchangeme.data.rates

import ba.ahavic.exchangeme.data.base.BaseRepository
import ba.ahavic.exchangeme.data.base.asBody
import ba.ahavic.exchangeme.data.models.RatesApi
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.nio.channels.Channel
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

interface RatesRepository {
    suspend fun getRates(baseCurrency: String = "EUR"): ReceiveChannel<RatesApi>
}

class RatesRepositoryImpl @Inject constructor(
    private val client: RatesClient
) : BaseRepository(), RatesRepository {

    @ExperimentalCoroutinesApi
    override suspend fun getRates(baseCurrency: String): ReceiveChannel<RatesApi> {
        return CoroutineScope(coroutineContext).produce {
            while (true) {
                val rates = client.getRatesAsync(baseCurrency)
                    .await()
                    .asBody()
                send(rates)
                delay(1_000)
            }
        }
    }
}

interface RatesClient {
    @GET("/latest")
    fun getRatesAsync(@Query("base") base: String): Deferred<Response<RatesApi>>
}
