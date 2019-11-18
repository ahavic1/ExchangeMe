package ba.ahavic.exchangeme.presentation.main.rates

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ba.ahavic.exchangeme.R
import ba.ahavic.exchangeme.databinding.ItemRateBinding

interface RatesAdapterListener {
    fun onRateSelected(position: Int)
    fun onRateEdited(newValue: String, rate: RatesView)
}

class RatesAdapter(
    private val ratesListener: RatesAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private enum class ViewType(val identifier: Int) {
        EditableItem(0),
        Item(1)
    }

    private val rates: MutableList<RatesView> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        return when (viewType) {
            ViewType.EditableItem.identifier -> EditableRatesVH.create(parent,
                inflater,
                ratesListener
            )
            else -> RatesVH.create(parent, inflater, ratesListener)
        }
    }

    override fun getItemCount(): Int = rates.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RatesVH) {
            holder.bindData(rates[position], position)
        } else if (holder is EditableRatesVH) {
            holder.bindData(rates[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) ViewType.EditableItem.identifier else ViewType.Item.identifier
    }

    fun setData(data: List<RatesView>) {
        val result = DiffUtil.calculateDiff(MyDiffUtilCallback(rates, data))
        result.dispatchUpdatesTo(this)
        rates.clear()
        rates.addAll(data)
    }
}

class EditableRatesVH(
    private val binding: ItemRateBinding,
    private val ratesListener: RatesAdapterListener
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(
            parent: ViewGroup,
            layoutInflater: LayoutInflater,
            ratesListener: RatesAdapterListener
        ): EditableRatesVH {
            val binding: ItemRateBinding =
                DataBindingUtil.inflate(layoutInflater,
                    R.layout.item_rate, parent, false
                )
            return EditableRatesVH(binding, ratesListener).apply { setIsRecyclable(false) }
        }
    }

    fun bindData(rate: RatesView) = with(binding) {
        imageCountryFlag.setImageResource(rate.icon)
        textCurrency.setText(rate.currency)
        textCurrencyCode.text = rate.currencyCode
        editAmount.setText(rate.amount)
        editAmount.setSelection(editAmount.length())
        editAmount.doAfterTextChanged { editable ->
            ratesListener.onRateEdited(editable.toString(), rate)
        }
    }
}


class RatesVH(
    private val binding: ItemRateBinding,
    private val ratesListener: RatesAdapterListener
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(
            parent: ViewGroup,
            layoutInflater: LayoutInflater,
            ratesListener: RatesAdapterListener
        ): RatesVH {
            val binding: ItemRateBinding =
                DataBindingUtil.inflate(layoutInflater,
                    R.layout.item_rate, parent, false
                )
            return RatesVH(binding, ratesListener)
        }
    }

    fun bindData(
        rate: RatesView,
        position: Int
    ) = with(binding) {
        root.setOnClickListener {
            ratesListener.onRateSelected(position)
        }
        imageCountryFlag.setImageResource(rate.icon)
        textCurrency.setText(rate.currency)
        textCurrencyCode.text = rate.currencyCode
        editAmount.setText(rate.amount)
        editAmount.setSelection(editAmount.length())
        editAmount.isFocusable = false
        editAmount.isFocusableInTouchMode = false
        editAmount.isClickable = false
    }
}

class MyDiffUtilCallback(
    private val oldList: List<RatesView>,
    private val newList: List<RatesView>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].currencyCode == newList[newItemPosition].currencyCode

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val result = newList[newItemPosition].amount.compareTo(oldList[oldItemPosition].amount)
        return result == 0
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldAmount = oldList[oldItemPosition].amount
        val newAmount = newList[newItemPosition].amount

        val diff = Bundle()

        if (oldAmount != newAmount) {
            diff.putString("amount", newAmount)
        }

        return if (diff.size() == 0) {
            null
        } else diff
    }
}
