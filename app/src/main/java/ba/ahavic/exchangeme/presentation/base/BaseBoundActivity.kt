package ba.ahavic.exchangeme.presentation.base

import androidx.databinding.ViewDataBinding

abstract class BaseBoundActivity<ViewModelType : BaseViewModel, ViewDataBindingType : ViewDataBinding> :
    BaseActivity<ViewDataBindingType>(), BoundView<ViewModelType> {

    final override lateinit var viewModel: ViewModelType

    override fun preInflate() {
        super.preInflate()
        //viewModel = ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory())
        //lifecycle.addObserver(viewModel)
    }

    override fun postInflate(viewDataBinding: ViewDataBinding?) {
        super.postInflate(viewDataBinding)
        viewDataBinding?.let {
            if (viewModelId != 0) {
                it.setVariable(viewModelId, viewModel)
                it.lifecycleOwner = this
                it.executePendingBindings()
            }
        }
        bindToViewModel()
    }
}
