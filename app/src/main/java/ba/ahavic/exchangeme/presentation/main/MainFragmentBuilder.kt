package ba.ahavic.exchangeme.presentation.main

import ba.ahavic.exchangeme.di.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Provides all fragment dependencies for MainActivity
 */
@Module
abstract class MainFragmentBuilder {

    @FragmentScope
    @ContributesAndroidInjector(modules = [RatesModule::class])
    abstract fun providesMainFragment() : RatesFragment
}
