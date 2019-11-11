package ba.ahavic.exchangeme.presentation.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onViewResume() { }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onViewPause() { }

}