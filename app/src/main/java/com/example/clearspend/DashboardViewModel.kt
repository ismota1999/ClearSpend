package com.example.clearspend

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        DashboardUiState(
            userName = "Iuri",
            balanceText = "€2,485.60",
            transactions = repository.getTransactions()
        )
    )

    val uiState: StateFlow<DashboardUiState> =
        _uiState.asStateFlow()

    fun toggleBalanceVisibility() {
        _uiState.update { currentState ->
            currentState.copy(
                isBalanceVisible = !currentState.isBalanceVisible
            )
        }
    }

}