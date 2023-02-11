package com.siddroid.currencyconverter.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.siddroid.currencyconverter.core.network.InternetConnectivity
import com.siddroid.currencyconverter.databinding.ActivityHistoricalDataBinding
import com.siddroid.currencyconverter.ui.adapters.HistoricalDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class HistoricalDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoricalDataBinding
    private val viewModel: HistoricalDataViewModel by viewModels()
    @Inject lateinit var adapter: HistoricalDataAdapter
    @Inject lateinit var internetAvailability: InternetConnectivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoricalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvHistory.layoutManager = LinearLayoutManager(this).apply { orientation = LinearLayoutManager.VERTICAL }
        binding.rvHistory.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.rvHistory.adapter = adapter
        setObservers()
        if (internetAvailability.isNetworkConnected()) {
            viewModel.fetchHistoryData()
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.historydata.collect() {
                withContext(Dispatchers.Main) {
                    adapter.updateData(it)
                }
            }
        }

    }
}