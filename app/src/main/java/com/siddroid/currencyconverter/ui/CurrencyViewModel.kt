package com.siddroid.currencyconverter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siddroid.currencyconverter.domain.CurrencyConversionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val currencyConversionUseCase: CurrencyConversionUseCase): ViewModel() {

    var isReversed: Boolean = false
    private val viewState = ViewState()

    private val _conversionData = MutableStateFlow(viewState)
    val conversionData: StateFlow<ViewState>
    get() = _conversionData.asStateFlow()

    private val _errorState = MutableStateFlow("")
    val errorState: StateFlow<String>
    get() = _errorState.asStateFlow()

    private val _currencies = MutableStateFlow<Set<String>>(setOf())
    val currencies: StateFlow<Set<String>>
    get() = _currencies.asStateFlow()

    fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val resp = currencyConversionUseCase.fetch()
            if (resp.success) {
                val currenciesList = resp.symbols.keys
                if (currenciesList.isNotEmpty())
                    _currencies.value = currenciesList
            }
        }
    }

    fun convert(amount: String, from: String, to: String) {
        if (amount.isBlank() || amount.toDouble().toInt() <= 0) {
            _errorState.value = "Please Enter All required Fields"
            return
        } else {
            _errorState.value = ""
        }
        viewModelScope.launch(Dispatchers.IO) {
            val conversionData = currencyConversionUseCase.invoke(amount.toDouble().toInt(), from, to)
            if (conversionData.success) {
                if (isReversed) {
                    _conversionData.update { it.copy(amount = conversionData.result?.toString().orEmpty(), convertedAmount = amount, from = to, to = from) }
                } else {
                    _conversionData.update { it.copy(convertedAmount = conversionData.result.toString(), amount = amount, from = from, to = to) }
                }

            }
            isReversed = false
        }
    }
}