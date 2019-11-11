package ba.ahavic.exchangeme.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<ViewDataBindingType: ViewDataBinding> : AppCompatActivity() {

    @get: LayoutRes
    abstract var layoutId: Int

    protected lateinit var viewDataBinding: ViewDataBindingType

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        preInflate()
        if (layoutId != 0) {
            viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        }
        postInflate(viewDataBinding)
    }

    /**
     * Invoked before View inflation
     */
    protected open fun preInflate() { }

    /**
     * Invoked after View is inflated.
     * Called even when view is not inflated in which case [viewDataBinding] parameter is null.
     */
    protected open fun postInflate(viewDataBinding: ViewDataBinding?) { }
}