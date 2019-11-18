package ba.ahavic.exchangeme.core.extensions

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AlertDialog

val Context.networkInfo: NetworkInfo?
    get() = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

fun Context.showDialog(title: Int, message: Int, listener: DialogInterface.OnDismissListener? = null) {
    val builder = AlertDialog.Builder(this)

    builder.apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(android.R.string.ok) { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }
        listener?.let {
            setOnDismissListener(it)
        }
        setCancelable(false)
    }.create().apply {
        setCanceledOnTouchOutside(false)
    }.show()
}
