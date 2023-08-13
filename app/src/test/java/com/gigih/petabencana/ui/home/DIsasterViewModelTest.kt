package com.gigih.petabencana.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.gigih.petabencana.data.BencanaRepository
import com.gigih.petabencana.data.BencanaTable
import com.gigih.petabencana.data.UiState
import com.gigih.petabencana.utils.DataDummy
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
class DIsasterViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var bencanaRepository: BencanaRepository
    private lateinit var disasterViewModel: DIsasterViewModel
    private val dummyNews = DataDummy.generateDummyBencanaEntity()

    // Set up test coroutine dispatcher and scope
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setUp() {
        bencanaRepository = mockk(relaxed = true) // Use relaxed mock to allow unused methods
        disasterViewModel = DIsasterViewModel(bencanaRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `when Get GetDisaster Should Not Null and Return Success`() = testScope.runBlockingTest {
        // Arrange
        val expectedDisaster = MutableLiveData<UiState<List<BencanaTable>>>()
        expectedDisaster.value = UiState.Success(dummyNews)
        every { bencanaRepository.getDisaster() } returns expectedDisaster

        // Act
        disasterViewModel.fetchDisasterData()

        // Assert
        val actualDisaster = disasterViewModel.disasterResponse.value
        assertNotNull(actualDisaster)
        assertTrue(actualDisaster is UiState.Success)
        assertEquals(dummyNews, (actualDisaster as UiState.Success).data)
    }

    @Test
    fun `when Network Error Should Return Error`() = testScope.runBlockingTest {
        // Arrange
        val disaster = MutableLiveData<UiState<List<BencanaTable>>>()
        disaster.value = UiState.Error("Error")
        every { bencanaRepository.getDisaster() } returns disaster

        // Act
        disasterViewModel.fetchDisasterData()

        // Assert
        val actualDisaster = disasterViewModel.disasterResponse.value
        assertNotNull(actualDisaster)
        assertTrue(actualDisaster is UiState.Error)
    }
}
