package ba.ahavic.exchangeme.data.rates

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
abstract class RatesDataModule {

    @Singleton
    @Binds
    abstract fun providesRatesRepository(repository: RatesRepositoryImpl) : RatesRepository

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun providesRatesClient(retrofit: Retrofit) = retrofit.create(RatesClient::class.java)
    }
}
