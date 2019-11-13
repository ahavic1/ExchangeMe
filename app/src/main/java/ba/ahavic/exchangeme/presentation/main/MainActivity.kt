package ba.ahavic.exchangeme.presentation.main

import androidx.lifecycle.ViewModel
import ba.ahavic.exchangeme.BR
import ba.ahavic.exchangeme.R
import ba.ahavic.exchangeme.databinding.ActivityMainBinding
import ba.ahavic.exchangeme.presentation.base.BaseViewModel
import ba.ahavic.exchangeme.presentation.base.di.ViewModelKey
import ba.ahavic.exchangeme.presentation.base.view.BaseBoundActivity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

class MainActivity : BaseBoundActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main
    override val viewModelNameRId: Int
        get() = BR.viewModel
    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override fun bindToViewModel() {

    }
}

class MainViewModel @Inject constructor() : BaseViewModel() {

}

@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun providesMainViewModel(viewModel: MainViewModel) : ViewModel
}