package ba.ahavic.exchangeme.presentation.base

import androidx.lifecycle.ViewModel

/**
 * Represents View that can be bound to ViewModel
 */
interface BoundView<ViewModelType: ViewModel> {

    /**
     * Provides the name of the ViewModel variable for data binding
     */
    val viewModelId: Int

    /**
     * Provides ViewModel or this View. ViewModel is injected into View and
     * available from this field.
     */
    var viewModel: ViewModelType

    /**
     * Provides ViewModel class to be injected into this View by ViewModelProviders.
     */
    val viewModelClass: Class<ViewModelType>

    /**
     * Invoked when view is created and bound to ViewModel.
     * Do final bindings in this method.
     */
    fun bindToViewModel()

}