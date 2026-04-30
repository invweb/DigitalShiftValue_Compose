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
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(viewModel: CounterViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Effects processing
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
                    // not implemented
                }
                is CounterEffect.NavigateBack -> {
                    // back navigation
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(com.example.dsv4.R.string.app_name)) },
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
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            // error
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
                            Text(stringResource(com.example.dsv4.R.string.Ok))
                        }
                    }
                }
            }

            // Counter Value
            Text(
                text = state.count.toString(),
                fontSize = 80.sp,
                modifier = Modifier.padding(32.dp)
            )

            // Control buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Button(
                    onClick = { viewModel.handleIntent(CounterIntent.Decrement) },
                    enabled = !state.isLoading && state.count > 0
                ) {
                    Text(stringResource(com.example.dsv4.R.string.minus))
                }

                Button(
                    onClick = { viewModel.handleIntent(CounterIntent.Reset) },
                    enabled = !state.isLoading
                ) {
                    Text(stringResource(com.example.dsv4.R.string.Reset))
                }

                Button(
                    onClick = { viewModel.handleIntent(CounterIntent.Increment) },
                    enabled = !state.isLoading
                ) {
                    Text(stringResource(com.example.dsv4.R.string.plus))
                }
            }

            OutlinedButton(
                onClick = { viewModel.handleIntent(CounterIntent.SetCount(100)) },
                enabled = !state.isLoading,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(stringResource(com.example.dsv4.R.string.SetHundred))
            }

            // last action info
            state.lastOperation?.let { operation ->
                Text(
                    text = stringResource(com.example.dsv4.R.string.last_action) + " " + operation,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
