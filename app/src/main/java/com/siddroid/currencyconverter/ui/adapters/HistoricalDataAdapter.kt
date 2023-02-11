package com.siddroid.currencyconverter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.siddroid.currencyconverter.BR
import com.siddroid.currencyconverter.R
import com.siddroid.currencyconverter.databinding.ItemHistoryBinding
import com.siddroid.currencyconverter.ui.model.HistoryViewState

class HistoricalDataAdapter: Adapter<HistoricalItemViewHolder>() {

    private val historyList = mutableListOf<HistoryViewState>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricalItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemHistoryBinding>(LayoutInflater.from(parent.context),
        R.layout.item_history, parent, false)
        return HistoricalItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoricalItemViewHolder, position: Int) {
        holder.updateRow(historyList[position])

    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun updateData(list: List<HistoryViewState>) {
        historyList.addAll(list)
        notifyItemRangeChanged(0, historyList.size)
    }
}

class HistoricalItemViewHolder(private val itemHistoryBinding: ItemHistoryBinding): ViewHolder(itemHistoryBinding.root) {
    fun updateRow(transaction: HistoryViewState) {
        itemHistoryBinding.setVariable(BR.transaction, transaction)
        itemHistoryBinding.executePendingBindings()
    }
}