package com.example.clearspend

data class DashboardUiState (
    val userName: String = "",
    val balanceText: String = "",
    val isBalanceVisible: Boolean = true,
    val transactions: List<TransactionUiModel> = emptyList()
)