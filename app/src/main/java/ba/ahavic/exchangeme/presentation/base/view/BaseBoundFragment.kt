package ba.ahavic.exchangeme.presentation.base.view

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import ba.ahavic.exchangeme.presentation.base.BaseViewModel
import javax.inject.Inject

abstract class BaseBoundFragment<ViewModelType : BaseViewModel, ViewDataBindingType : ViewDataBinding> :
    BaseFragment<ViewDataBindingType>(),
    BoundView<ViewModelType> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    final override lateinit var viewModel: ViewModelType

    override fun preInflate() {
        super.preInflate()
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass)
        lifecycle.addObserver(viewModel)
    }

    override fun postInflate(viewDataBinding: ViewDataBinding?) {
        super.postInflate(viewDataBinding)
        viewDataBinding?.let {
            if (viewModelNameRId != 0) {
                it.setVariable(viewModelNameRId, viewModel)
                it.lifecycleOwner = this
                it.executePendingBindings()
            }
        }
        bindToViewModel()
    }
}