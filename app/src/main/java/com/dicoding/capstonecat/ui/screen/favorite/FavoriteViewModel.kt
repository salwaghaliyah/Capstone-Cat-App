package com.dicoding.capstonecat.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.capstonecat.data.CatRepository
import com.dicoding.capstonecat.data.entity.CatFavEntity
import com.dicoding.capstonecat.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: CatRepository): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<CatFavEntity>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<CatFavEntity>>> get() = _uiState

    fun getFavCats() {
        viewModelScope.launch {
            repository.getFavCats()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}