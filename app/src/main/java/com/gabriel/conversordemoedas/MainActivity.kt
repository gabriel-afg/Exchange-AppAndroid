package com.gabriel.conversordemoedas

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.gabriel.conversordemoedas.databinding.ActivityMainBinding
import com.gabriel.conversordemoedas.ui.CurrencyTypesAdapter
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<CurrencyExchangeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // Carrega a view principal
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.apply {
            launch {
                viewModel.currencyTypes.collect { result ->
                    result.onSuccess { currencyTypes ->
                        binding.spnFromExchange.adapter = CurrencyTypesAdapter(currencyTypes)
                        binding.spnToExchange.adapter = CurrencyTypesAdapter(currencyTypes)
                    }.onFailure {
                        Toast.makeText(this@MainActivity, it.message,
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
            launch {
                viewModel.exchangeRate.collect { result ->
                    result.onSuccess {
                        Log.d("MainActivity", it.toString())
                    }.onFailure {
                        Log.d("MainActivity", it.message.toString())
                    }
                }
            }
        }
    }
}