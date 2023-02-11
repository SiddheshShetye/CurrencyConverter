package com.siddroid.currencyconverter.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.CheckResult
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.siddroid.currencyconverter.R
import com.siddroid.currencyconverter.core.network.InternetConnectivity
import com.siddroid.currencyconverter.databinding.ActivityMainBinding
import com.siddroid.currencyconverter.ui.viewmodel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnClickListener, OnItemSelectedListener {

    private var isAutomaticConversionText: Boolean = false
    private var isAutomaticAmountText: Boolean = false
    private val currencyViewModel: CurrencyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var fromAdapter:ArrayAdapter<String>
    @Inject lateinit var toAdapter:ArrayAdapter<String>
    @Inject lateinit var internetAvailability: InternetConnectivity

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            viewmodel = currencyViewModel
            clickHandler = this@MainActivity
        }
        setContentView(binding.root)
        initObservers()
        binding.spnFrom.adapter = fromAdapter
        binding.spnTo.adapter = toAdapter
        if (internetAvailability.isNetworkConnected()) {
            currencyViewModel.getCurrencies()
        }
        binding.edtConversion.textChanges()
            .filterNot { it.isNullOrBlank() }
            .debounce(500)
            .onEach {
                if (isAutomaticConversionText) {
                    isAutomaticConversionText = false
                    return@onEach
                }
                lifecycleScope.launch {
                    currencyViewModel.isReversed = true
                    if (internetAvailability.isNetworkConnected()) {
                        currencyViewModel.convert(
                            it?.toString()?.trim().orEmpty(),
                            binding.spnTo.selectedItem?.toString().orEmpty(),
                            binding.spnFrom.selectedItem?.toString().orEmpty()
                        )
                    }
                }
            }
            .launchIn(lifecycleScope)

        binding.edtAmount.textChanges()
            .filterNot { it.isNullOrBlank() }
            .debounce(500)
            .onEach {
                if (isAutomaticAmountText) {
                    isAutomaticAmountText = false
                    return@onEach
                }
                lifecycleScope.launch {
                    currencyViewModel.isReversed = false
                    if (internetAvailability.isNetworkConnected()) {
                        currencyViewModel.convert(
                            it?.toString()?.trim().orEmpty(),
                            binding.spnFrom.selectedItem?.toString().orEmpty(),
                            binding.spnTo.selectedItem?.toString().orEmpty()
                        )
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun initObservers() {
        binding.spnFrom.onItemSelectedListener = this
        binding.spnTo.onItemSelectedListener = this

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                currencyViewModel.conversionData.collect {
                        isAutomaticConversionText = true
                        isAutomaticAmountText = true
                    binding.edtAmount.setText(it.amount)
                    binding.edtConversion.setText(it.convertedAmount)

                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                currencyViewModel.errorState.collect {
                    if (it.isBlank()) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                currencyViewModel.currencies.collect {
                    fromAdapter.addAll(it.toList())
                    toAdapter.addAll(it.toList())
                    withContext(Dispatchers.Main) {
                        binding.spnFrom.setSelection(0)
                        binding.spnTo.setSelection(1)
                        binding.edtAmount.setText("1")
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnExchange -> {
                val temp = binding.spnFrom.selectedItemPosition
                binding.spnFrom.setSelection(binding.spnTo.selectedItemPosition)
                binding.spnTo.setSelection(temp)
            }
            R.id.btnHistory -> {
                startActivity(Intent(this, HistoricalDataActivity::class.java))
            }
        }
    }

    @ExperimentalCoroutinesApi
    @CheckResult
    fun EditText.textChanges(): Flow<CharSequence?> {
        return callbackFlow {
            val listener = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    trySend(s)
                }
            }
            addTextChangedListener(listener)
            awaitClose { removeTextChangedListener(listener) }
        }.onStart { emit(text) }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (internetAvailability.isNetworkConnected()) {
            currencyViewModel.convert(
                binding.edtAmount.text.toString().trim(),
                binding.spnFrom.selectedItem?.toString().orEmpty(),
                binding.spnTo.selectedItem?.toString().orEmpty()
            )
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}