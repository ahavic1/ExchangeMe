package ba.ahavic.exchangeme.presentation.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.DaggerFragment

abstract class BaseFragment<ViewDataBindingType: ViewDataBinding>: DaggerFragment() {

    @get:LayoutRes
    abstract val layoutId: Int

    protected lateinit var viewDataBinding: ViewDataBindingType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preInflate()
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postInflate(viewDataBinding)
    }

    /**
     * Invoked before view inflation
     */
    protected open fun preInflate() { }

    /**
     * Invoked after view inflation.
     * Called even when view is not inflated in which case [viewDataBinding] parameter is null.
     */
    protected open fun postInflate(viewDataBinding: ViewDataBinding?) { }
}