package com.example.clearspend

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class DashboardViewModelTest {

    private val repository = TransactionRepository()


    @Test
    fun toggleBalanceVisibility_whenVisible_hidesBalance() {
        //Arrange

        val viewModel = DashboardViewModel(repository)

        //Act

        viewModel.toggleBalanceVisibility()


        //Assert

        assertFalse(viewModel.uiState.value.isBalanceVisible)
    }

    @Test
    fun toggleBalanceVisibility_whenCalledTwice_showsBalance() {
        //Arrange

        val viewModel = DashboardViewModel(repository)

        //Act

        viewModel.toggleBalanceVisibility()
        viewModel.toggleBalanceVisibility()


        //Assert

        assertTrue(viewModel.uiState.value.isBalanceVisible)
    }

    @Test
    fun hidingBalance_notChangeDashboardOtherData () {
        //Arrange

        val viewModel = DashboardViewModel(repository)
        val state = viewModel.uiState.value


        //Act

        viewModel.toggleBalanceVisibility()


        //Assert

        val stateAfterToggle = viewModel.uiState.value


        assertEquals(state.userName, stateAfterToggle.userName)
        assertEquals(state.balanceText, stateAfterToggle.balanceText)
        assertEquals(state.transactions, stateAfterToggle.transactions)
    }
}