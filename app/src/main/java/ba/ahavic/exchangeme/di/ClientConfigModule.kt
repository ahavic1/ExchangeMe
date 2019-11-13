package ba.ahavic.exchangeme.di

import ba.ahavic.exchangeme.BuildConfig
import ba.ahavic.exchangeme.core.ClientConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ClientConfigModule {

    @Singleton
    @Provides
    @JvmStatic
    fun providesClientConfig() : ClientConfig = object : ClientConfig {
        override val clientId: String = BuildConfig.clientId
        override val clientSecret: String = BuildConfig.clientSecret
    }
}
