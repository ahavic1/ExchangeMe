package ba.ahavic.exchangeme.presentation.main

import androidx.lifecycle.ViewModel
import ba.ahavic.exchangeme.BR
import ba.ahavic.exchangeme.R
import ba.ahavic.exchangeme.databinding.FragmentRatesBinding
import ba.ahavic.exchangeme.presentation.base.BaseViewModel
import ba.ahavic.exchangeme.presentation.base.di.ViewModelKey
import ba.ahavic.exchangeme.presentation.base.view.BaseBoundFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

class RatesFragment: BaseBoundFragment<RatesViewModel, FragmentRatesBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_rates
    override val viewModelNameRId: Int
        get() = BR.viewModel
    override val viewModelClass: Class<RatesViewModel>
        get() = RatesViewModel::class.java

    override fun bindToViewModel() {
    }
}


class RatesViewModel @Inject constructor(): BaseViewModel() {

}

@Module
abstract class RatesModule {
    @Binds
    @IntoMap
    @ViewModelKey(RatesViewModel::class)
    abstract fun providesHomeViewModel(viewModel: RatesViewModel) : ViewModel
}
