package com.dicoding.capstonecat.ui.screen.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.capstonecat.data.CatRepository
import com.dicoding.capstonecat.data.response.ScanResponse
import com.dicoding.capstonecat.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class CameraViewModel(private val repository: CatRepository) : ViewModel() {

    private val _predictionResult: MutableStateFlow<UiState<ScanResponse>> = MutableStateFlow(UiState.Loading)
    val predictionResult: StateFlow<UiState<ScanResponse>> get() = _predictionResult

    fun sendImageAndGetPrediction(imageFile: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                val prediction = repository.predictCatType(imageFile)
                _predictionResult.value = UiState.Success(prediction)
            } catch (e: Exception) {
                _predictionResult.value = UiState.Error(e.message.toString())
            }
        }
    }
}