package ba.ahavic.exchangeme.presentation.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import ba.ahavic.exchangeme.core.AppError
import ba.ahavic.exchangeme.core.AppException
import ba.ahavic.exchangeme.core.ReasonOfError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    /**
     * [_coroutineExceptionHandler] context element is used as generic catch block of coroutine.
     */
    private var _coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        isLoading(false)
        if (exception is AppException) defaultErrorHandler(exception.appError)
        if (exception is UnknownHostException) defaultErrorHandler(AppError(reason =  ReasonOfError.HostNotFound))
        else throw exception
    }

    var arguments: Bundle = Bundle()

    private val _navigationEvent = SingleLiveEvent<NavigationEvent>()
    val navigationEvent: LiveData<NavigationEvent> = _navigationEvent

    private val _loading = SingleLiveEvent<Boolean>()
    val isLoading: LiveData<Boolean> = _loading

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

    protected fun isLoading(loading: Boolean) {
        _loading.value = loading
    }

    protected fun setError(error: BaseError) {
        // log errorBody
        _error.value = error
    }

    /**
     * Override to provide specific feature errorBody handling but don't forget to call super!
     */
    @CallSuper
    protected open fun defaultErrorHandler(appError: AppError) {
        _error.value = when (appError.reason) {
            ReasonOfError.InvalidBase -> InvalidBaseError
            ReasonOfError.ResourceNotFound -> UnknownHostError
            ReasonOfError.HostNotFound -> UnknownHostError
            ReasonOfError.UnknownError -> DefaultError
        }
    }

    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        try {
            viewModelScope.launch(_coroutineExceptionHandler) {
                block()
            }
        } catch (exception: Exception) {
            throw exception
        } finally {
            if (_loading.value != false) {
                isLoading(false)
            }
        }
    }
}
