package ba.ahavic.exchangeme.presentation.main.rates

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalStateException

class CustomLinearLayoutManager(context: Context) : LinearLayoutManager(context) {

    private var onLayoutChangedListener: OnLayoutChanged? = null

    fun addOnLayoutChangedListener(onLayoutChangedListener: OnLayoutChanged) {
        this.onLayoutChangedListener = onLayoutChangedListener
    }

    override fun onItemsMoved(recyclerView: RecyclerView, from: Int, to: Int, itemCount: Int) {
        super.onItemsMoved(recyclerView, from, to, itemCount)
        onLayoutChangedListener?.itemsMoved(recyclerView, from, to, itemCount)
    }

    fun isInLayoutOrScroll(): Boolean {
        return try {
            assertInLayoutOrScroll("Not in layout")
            true
        } catch (ex: IllegalStateException) {
            false
        }
    }

    interface OnLayoutChanged {
        fun itemsMoved(recyclerView: RecyclerView, from: Int, to: Int, itemCount: Int)
    }
}