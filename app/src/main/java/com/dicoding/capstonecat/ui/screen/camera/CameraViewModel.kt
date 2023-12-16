package com.dicoding.capstonecat.ui.screen.camera

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.capstonecat.data.CatRepository
import com.dicoding.capstonecat.data.model.CatPredictionResult
import kotlinx.coroutines.launch

class CameraViewModel(private val repository: CatRepository) : ViewModel() {
    private val _predictionResult = MutableLiveData<CatPredictionResult>()
    val predictionResult: LiveData<CatPredictionResult>
        get() = _predictionResult

    fun sendImageAndGetPrediction(imageUri: Uri?) {
        viewModelScope.launch {
            try {
                val prediction = repository.predictCatType(imageUri)
                _predictionResult.postValue(prediction)
            } catch (e: Exception) {
                // Tangani kesalahan jika prediksi gagal
            }
        }
    }
}