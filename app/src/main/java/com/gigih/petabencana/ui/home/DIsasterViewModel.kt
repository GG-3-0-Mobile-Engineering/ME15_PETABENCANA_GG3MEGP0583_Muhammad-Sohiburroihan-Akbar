package com.gigih.petabencana.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gigih.petabencana.data.BencanaRepository
import com.gigih.petabencana.data.BencanaResponse
import com.gigih.petabencana.data.BencanaTable
import com.gigih.petabencana.data.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DIsasterViewModel(private val bencanaRepository: BencanaRepository) : ViewModel() {
    private val _disasterResponse = MutableStateFlow<UiState<List<BencanaTable>>>(UiState.Loading)
    val disasterResponse: StateFlow<UiState<List<BencanaTable>>> = _disasterResponse

    private val _locationDisaster = MutableStateFlow<UiState<BencanaResponse>>(UiState.Loading)
    val locationDisaster: StateFlow<UiState<BencanaResponse>> = _locationDisaster

    fun fetchDisasterData() {
        viewModelScope.launch {
//            _disasterResponse.value = bencanaRepository.getDisaster()
            val disasterLiveData = bencanaRepository.getDisaster()

            // Observe the LiveData and update the MutableStateFlow accordingly
            disasterLiveData.observeForever { uiState ->
                _disasterResponse.value = uiState
            }
        }
    }

    fun getDisasterByLocation(location: String) {
        viewModelScope.launch {
            _locationDisaster.value = bencanaRepository.getDisasterByLocation(location = location)
            Log.d("testtt", _disasterResponse.value.toString())
        }
    }

    fun getDisasterByType(type: String) {
        viewModelScope.launch {
            _locationDisaster.value = bencanaRepository.getDisasterByLocation(type)
            Log.d("testtt", _disasterResponse.value.toString())
        }
    }

}