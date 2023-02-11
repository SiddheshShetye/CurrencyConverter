package com.siddroid.currencyconverter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siddroid.currencyconverter.domain.CurrencyHistoryUseCase
import com.siddroid.currencyconverter.ui.model.HistoryViewState
import com.siddroid.currencyconverter.ui.model.StateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HistoricalDataViewModel @Inject constructor(private val currencyHistoryUseCase: CurrencyHistoryUseCase): ViewModel() {

    private val _historyData = MutableStateFlow<List<HistoryViewState>>(listOf())
    val historydata: StateFlow<List<HistoryViewState>>
    get() = _historyData.asStateFlow()
    private val cal = Calendar.getInstance()

    init {
        cal.add(Calendar.DATE, -3)
    }

    fun fetchHistoryData() {
        viewModelScope.launch(Dispatchers.IO) {
            val convertedDate = TimeUnit.MILLISECONDS.toSeconds(cal.timeInMillis)
            //--------
            val historyList = mutableListOf<HistoryViewState>()
            currencyHistoryUseCase.fetch(convertedDate).forEach {
                historyList.add(StateMapper.mapTransactionToHistoryViewState(it))
            }
            _historyData.value = historyList
        }
    }

//      1676058724
// -3   1675802020088
}