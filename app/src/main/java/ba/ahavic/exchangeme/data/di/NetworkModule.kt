package ba.ahavic.exchangeme.data.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
abstract class NetworkModule {

    @Module
    companion object {
        private const val DEFAULT_TIMEOUT_MS = 10000L

        @Provides
        @Singleton
        @JvmStatic
        fun provideDispatcher(): Dispatcher = Dispatcher()

        @Provides
        @Singleton
        @JvmStatic
        @IntoSet
        fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideOkHttpClient(
            dispatcher: Dispatcher,
            interceptors: Set<@JvmSuppressWildcards Interceptor>
        ): OkHttpClient = createOkHttpClient(dispatcher, interceptors)

        private fun createOkHttpClient(
            dispatcher: Dispatcher,
            interceptors: Set<@JvmSuppressWildcards Interceptor>
        ): OkHttpClient {
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .dispatcher(dispatcher)

            interceptors.forEach { builder.addInterceptor(it) }

            return builder
                .connectTimeout(DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .build()
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideRetrofit(
            okHttpClient: OkHttpClient, networkConfig: NetworkConfig
        ): Retrofit = Retrofit.Builder()
            .baseUrl(networkConfig.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .client(okHttpClient)
            .build()
    }

    @Binds
    @Singleton
    abstract fun provideDefaultNetworkConfig(networkConfig: DefaultNetworkConfig): NetworkConfig
}