package ba.ahavic.exchangeme.core.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> T.asMutableLiveData(): MutableLiveData<T>
    = MutableLiveData<T>().apply { value = this@asMutableLiveData }

fun <T> T.asLiveData(): LiveData<T>
    = MutableLiveData<T>().apply { value = this@asLiveData }