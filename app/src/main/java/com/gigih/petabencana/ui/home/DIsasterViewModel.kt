package com.gigih.petabencana.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gigih.petabencana.data.BencanaRepository
import com.gigih.petabencana.data.BencanaResponse
import com.gigih.petabencana.data.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DIsasterViewModel(private val bencanaRepository: BencanaRepository) : ViewModel() {
    private val _disasterResponse = MutableStateFlow<UiState<BencanaResponse>>(UiState.Loading)
    val disasterResponse: StateFlow<UiState<BencanaResponse>> = _disasterResponse

    fun fetchDisasterData() {
        viewModelScope.launch {
            _disasterResponse.value = bencanaRepository.getDisaster()
        }
    }
}