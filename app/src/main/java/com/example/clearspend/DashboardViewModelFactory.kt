package com.example.clearspend

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory


val dashboardViewModelFactory: ViewModelProvider.Factory =
    viewModelFactory {
        initializer {
            val transactionRepository = TransactionRepository()

            val dashboardViewModel = DashboardViewModel(transactionRepository)

            return@initializer dashboardViewModel
        }
    }
