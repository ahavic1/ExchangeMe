package ba.ahavic.exchangeme.core.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun Fragment.getNavController(): NavController = NavHostFragment.findNavController(this)

fun Fragment.hideKeyboard() {
    try {
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let {
            it.hideSoftInputFromWindow((context as Activity).window.currentFocus?.windowToken, 0)
        }
    } catch (e: NullPointerException) {
        // Ignore
    }
}