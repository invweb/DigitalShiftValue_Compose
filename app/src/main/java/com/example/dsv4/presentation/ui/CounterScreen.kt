package com.example.dsv4.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dsv4.presentation.effect.CounterEffect
import com.example.dsv4.presentation.intent.CounterIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(viewModel: CounterViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Обработка эффектов
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CounterEffect.ShowToast -> {
                    android.widget.Toast.makeText(
                        context,
                        effect.message,
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
                is CounterEffect.PlaySound -> {
                    // Здесь можно проиграть звук
                    println("Play sound with id: ${effect.soundId}")
                }
                is CounterEffect.NavigateBack -> {
                    // Навигация назад
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MVI Счетчик") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Загрузчик
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            // Ошибка
            state.error?.let { error ->
                Card(
                    modifier = Modifier.padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(
                            onClick = { viewModel.handleIntent(CounterIntent.ClearError) }
                        ) {
                            Text("OK")
                        }
                    }
                }
            }

            // Значение счетчика
            Text(
                text = state.count.toString(),
                fontSize = 80.sp,
                modifier = Modifier.padding(32.dp)
            )

            // Кнопки управления
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Button(
                    onClick = { viewModel.handleIntent(CounterIntent.Decrement) },
                    enabled = !state.isLoading && state.count > 0
                ) {
                    Text("-1")
                }

                Button(
                    onClick = { viewModel.handleIntent(CounterIntent.Reset) },
                    enabled = !state.isLoading
                ) {
                    Text("Сброс")
                }

                Button(
                    onClick = { viewModel.handleIntent(CounterIntent.Increment) },
                    enabled = !state.isLoading
                ) {
                    Text("+1")
                }
            }

            // Дополнительный функционал
            OutlinedButton(
                onClick = { viewModel.handleIntent(CounterIntent.SetCount(100)) },
                enabled = !state.isLoading,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Установить 100")
            }

            // Информация о последней операции
            state.lastOperation?.let { operation ->
                Text(
                    text = "Последнее действие: $operation",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
