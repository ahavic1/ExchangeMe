package ba.ahavic.exchangeme.presentation.base.di

import ba.ahavic.exchangeme.di.ActivityScope
import ba.ahavic.exchangeme.presentation.main.MainActivity
import ba.ahavic.exchangeme.presentation.main.MainModule
import ba.ahavic.exchangeme.presentation.main.MainFragmentBuilder
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class, MainFragmentBuilder::class])
    abstract fun provideMainActivity(): MainActivity
}
