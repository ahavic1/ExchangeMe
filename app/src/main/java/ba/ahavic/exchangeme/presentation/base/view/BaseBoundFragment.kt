package ba.ahavic.exchangeme.presentation.base.view

import android.content.DialogInterface
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ba.ahavic.exchangeme.core.extensions.getNavController
import ba.ahavic.exchangeme.core.extensions.showDialog
import ba.ahavic.exchangeme.presentation.base.BaseError
import ba.ahavic.exchangeme.presentation.base.BaseViewModel
import ba.ahavic.exchangeme.presentation.base.NavigationEvent
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
        setNavigationObserver()
        setErrorObserver()
    }

    protected open fun setErrorObserver() {
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error is BaseError.FeatureError)
                context?.showDialog(error.title, error.description,
                    DialogInterface.OnDismissListener { dialog ->
                        dialog.dismiss()
                    })
        })
    }

    private fun setNavigationObserver() {
        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer { navEvent ->
            when (navEvent) {
                is NavigationEvent.To -> getNavController().navigate(navEvent.directions)
                is NavigationEvent.Back -> getNavController().navigateUp()
            }
        })
    }
}