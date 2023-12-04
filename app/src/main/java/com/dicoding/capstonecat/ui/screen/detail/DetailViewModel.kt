package com.dicoding.capstonecat.ui.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.capstonecat.data.CatRepository
import com.dicoding.capstonecat.data.model.CatModel
import kotlinx.coroutines.launch

class DetailViewModel (private val repository: CatRepository): ViewModel() {

    private val _catDetail = MutableLiveData<CatModel>()
    val catDetail: LiveData<CatModel> get() = _catDetail

    // Fungsi untuk mendapatkan detail kucing dari API
    fun getCatDetail(breed: String) {
        viewModelScope.launch {
            try {
                val catDetail = repository.getCatDetail(breed)
                _catDetail.value = catDetail
            } catch (e: Exception) {

            }
        }
    }

}