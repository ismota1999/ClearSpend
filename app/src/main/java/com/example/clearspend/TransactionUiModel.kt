package com.example.clearspend


data class TransactionUiModel(
    val id: Long,
    val name: String,
    val category: String,
    val dateText: String,
    val amountText: String,
    val isIncome: Boolean
)