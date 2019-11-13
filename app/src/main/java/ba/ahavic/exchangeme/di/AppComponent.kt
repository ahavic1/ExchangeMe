package ba.ahavic.exchangeme.di

import ba.ahavic.exchangeme.App
import ba.ahavic.exchangeme.data.di.NetworkModule
import ba.ahavic.exchangeme.presentation.base.di.ActivityBuilder
import ba.ahavic.exchangeme.presentation.base.di.ViewModelFactoryModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ClientConfigModule::class,

    // Android
    AndroidSupportInjectionModule::class,
    ViewModelFactoryModule::class,
    ActivityBuilder::class,

    // Data
    NetworkModule::class

])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}
