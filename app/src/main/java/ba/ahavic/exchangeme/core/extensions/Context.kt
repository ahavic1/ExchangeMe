package ba.ahavic.exchangeme.core.extensions

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

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