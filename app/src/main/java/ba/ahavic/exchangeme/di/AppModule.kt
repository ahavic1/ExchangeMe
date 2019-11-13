package ba.ahavic.exchangeme.di

import android.app.Application
import android.content.Context
import ba.ahavic.exchangeme.App
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindApplication(application: App) : Application

    @Binds
    @Singleton
    abstract fun bindContext(application: Application) : Context

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideDispachers(): Dispatchers = Dispatchers
    }
}
