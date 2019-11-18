package ba.ahavic.exchangeme.presentation.base.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<ViewDataBindingType : ViewDataBinding> : DaggerAppCompatActivity(),
    LifecycleOwner {

    @get:LayoutRes
    abstract val layoutId: Int

    protected lateinit var viewDataBinding: ViewDataBindingType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preInflate()
        if (layoutId != 0) {
            viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        }
        postInflate(viewDataBinding)
    }

    /**
     * Invoked before View inflation
     */
    protected open fun preInflate() {}

    /**
     * Invoked after View is inflated.
     * Called even when view is not inflated in which case [viewDataBinding] parameter is null.
     */
    protected open fun postInflate(viewDataBinding: ViewDataBinding?) {}
}
