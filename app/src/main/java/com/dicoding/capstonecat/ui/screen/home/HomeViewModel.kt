package com.dicoding.capstonecat.ui.screen.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.capstonecat.data.CatRepository
import com.dicoding.capstonecat.data.model.Cat
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CatRepository): ViewModel()
{

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _cats = MutableLiveData<List<Cat>>()
    val cats: LiveData<List<Cat>> get() = _cats

    fun search(newQuery: String) {
        _query.value = newQuery
    }

    fun getAllCatsFromApi() {
        viewModelScope.launch {
            try {
                val catsList = repository.getAllCatsFromApi()
                _cats.value = catsList
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching cats from API: ${e.message}")
            }
        }
    }
}