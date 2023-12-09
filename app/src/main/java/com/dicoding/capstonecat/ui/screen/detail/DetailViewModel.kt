package com.dicoding.capstonecat.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.capstonecat.data.CatRepository
import com.dicoding.capstonecat.data.entity.CatFavEntity
import com.dicoding.capstonecat.data.response.CatResponseItem
import com.dicoding.capstonecat.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel (private val repository: CatRepository): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<CatResponseItem>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CatResponseItem>> get() = _uiState

    private val _catFav: MutableStateFlow<UiState<Int>> = MutableStateFlow(UiState.Loading)
    val catFav: StateFlow<UiState<Int>> get() = _catFav

    // Fungsi untuk mendapatkan detail kucing dari API
    fun getCatByBreed(breed: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getCatByBreed(breed))
        }
    }

    fun getFavCatByBreed(breed: String) {
        viewModelScope.launch {
            repository.getFavCatByBreed(breed)
                .catch {
                    _catFav.value = UiState.Error(it.message.toString())
                }
                .collect{
                    _catFav.value = UiState.Success(it)
                }
        }
    }

    fun saveFavCat(cat: CatFavEntity) {
        viewModelScope.launch {
            repository.insertFavCat(cat)
        }
    }

    fun deleteFavCat(breed: String) {
        viewModelScope.launch {
            repository.deleteFavCat(breed)
        }
    }
}