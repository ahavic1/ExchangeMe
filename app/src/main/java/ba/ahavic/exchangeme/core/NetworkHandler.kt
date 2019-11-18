package ba.ahavic.exchangeme.core

import android.content.Context
import ba.ahavic.exchangeme.core.extensions.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler @Inject constructor(private val context: Context) {

    val isConnected: Boolean
        get() = context.networkInfo?.isConnected!!
}
