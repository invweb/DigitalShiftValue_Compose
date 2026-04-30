package com.example.dsv4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.dsv4.ui.theme.DSV4Theme
import com.example.dsv4.presentation.ui.CounterScreen
import com.example.dsv4.presentation.ui.CounterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DSV4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    val viewModel: CounterViewModel by viewModels()
                    CounterScreen(viewModel = viewModel)
                }
            }
        }
    }
}