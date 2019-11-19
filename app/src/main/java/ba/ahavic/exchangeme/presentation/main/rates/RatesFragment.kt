package ba.ahavic.exchangeme.presentation.main.rates

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import ba.ahavic.exchangeme.BR
import ba.ahavic.exchangeme.R
import ba.ahavic.exchangeme.databinding.FragmentRatesBinding
import ba.ahavic.exchangeme.presentation.base.di.ViewModelKey
import ba.ahavic.exchangeme.presentation.base.view.BaseBoundFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

class RatesFragment: BaseBoundFragment<RatesViewModel, FragmentRatesBinding>() {

    private val ratesAdapter: RatesAdapter by lazy {
        RatesAdapter(viewModel)
    }

    private val linearLayoutManager: CustomLinearLayoutManager by lazy {
        CustomLinearLayoutManager(context!!)
    }

    override val layoutId: Int
        get() = R.layout.fragment_rates
    override val viewModelNameRId: Int
        get() = BR.viewModel
    override val viewModelClass: Class<RatesViewModel>
        get() = RatesViewModel::class.java

    override fun bindToViewModel() {
        setUI()
        setListeners()
        setObservers()
    }

    private fun setUI() {
        setToolbarTitle(R.string.rates_toolbar_title)

        viewDataBinding.recyclerRates.run {
            adapter = ratesAdapter
            layoutManager = linearLayoutManager
        }
    }

    private fun setListeners() {
        linearLayoutManager.addOnLayoutChangedListener(object : CustomLinearLayoutManager.OnLayoutChanged {
            override fun itemsMoved(recyclerView: RecyclerView, from: Int, to: Int, itemCount: Int) {
                recyclerView.scrollToPosition(to)
            }
        })
    }

    private fun setObservers() {
        viewModel.ratesView.observe(viewLifecycleOwner, Observer { ratesView ->
            if (ratesView != null && !linearLayoutManager.isInLayoutOrScroll()) {
                ratesAdapter.setData(ratesView)
            }
        })
    }
}

@Module
abstract class RatesModule {
    @Binds
    @IntoMap
    @ViewModelKey(RatesViewModel::class)
    abstract fun providesHomeViewModel(viewModel: RatesViewModel) : ViewModel
}
