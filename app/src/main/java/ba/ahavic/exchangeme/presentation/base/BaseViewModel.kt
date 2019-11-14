package ba.ahavic.exchangeme.presentation.base

import androidx.lifecycle.*
import androidx.navigation.NavDirections

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    private val _navigationEvent = SingleLiveEvent<NavigationEvent>()
    val navigationEvent: LiveData<NavigationEvent> = _navigationEvent

    private val _error = MutableLiveData<BaseError>()
    val error: LiveData<BaseError> = _error

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onViewResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onViewPause() {
    }

    protected fun navigate(navAction: NavigationEvent) {
        _navigationEvent.value = navAction
    }

    protected fun navigate(navDirections: NavDirections) {
        _navigationEvent.value = NavigationEvent.To(navDirections)
    }

    protected fun setError(error: BaseError) {
        // log error
        _error.value = error
    }

}
