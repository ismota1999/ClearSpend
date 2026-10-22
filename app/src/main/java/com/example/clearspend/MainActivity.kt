package com.example.clearspend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clearspend.ui.theme.ClearSpendTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ClearSpendTheme {
                DashboardRoute()
            }
        }
    }
}

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onVisibilityClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Welcome back, ${uiState.userName}",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = "Let's take a look at your spending.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            item {
                BalanceCard(
                    balance = uiState.balanceText,
                    isBalanceVisible = uiState.isBalanceVisible,
                    onVisibilityClick = onVisibilityClick
                )
            }

            item {
                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            items(
                items = uiState.transactions,
                key = { transaction -> transaction.id }
            ) { transaction ->
                TransactionRow(
                    name = transaction.name,
                    category = transaction.category,
                    date = transaction.dateText,
                    amount = transaction.amountText,
                    isIncome = transaction.isIncome
                )
            }
        }
    }
}

@Composable
fun BalanceCard(
    balance: String,
    isBalanceVisible: Boolean,
    onVisibilityClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Available balance",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = if (isBalanceVisible) {
                    balance
                } else {
                    "Balance hidden"
                },
                style = MaterialTheme.typography.headlineMedium
            )

            TextButton(
                onClick = onVisibilityClick
            ) {
                Text(
                    text = if (isBalanceVisible) {
                        "Hide balance"
                    } else {
                        "Show balance"
                    }
                )
            }
        }
    }
}

@Composable
fun TransactionRow(
    name: String,
    category: String,
    date: String,
    amount: String,
    isIncome: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(
                text = amount,
                style = MaterialTheme.typography.titleMedium,
                color = if (isIncome) {
                    Color(0xFF2E7D32)
                } else {
                    MaterialTheme.colorScheme.error
                }
            )
        }
    }
}

@Composable
fun DashboardRoute(
    viewModel: DashboardViewModel = viewModel(factory = dashboardViewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DashboardScreen(
        uiState = uiState,
        onVisibilityClick = viewModel::toggleBalanceVisibility
    )
}


@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    ClearSpendTheme {
        DashboardScreen(
            uiState = DashboardUiState(
                userName = "Alex",
                balanceText = "€2,485.60",
                transactions = sampleTransactions
            ),
            onVisibilityClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun BalanceCardPreview() {
    ClearSpendTheme {
        BalanceCard(
            balance = "€2,485.60",
            isBalanceVisible = true,
            onVisibilityClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
