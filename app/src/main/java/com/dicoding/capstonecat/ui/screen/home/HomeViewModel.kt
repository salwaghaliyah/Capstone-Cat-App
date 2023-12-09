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

    private var originalCatList: List<Cat> = emptyList()

    fun search(query: String) {
        _query.value = query
        val filteredCats = originalCatList.filter { cat ->
            cat.Ras.contains(query, ignoreCase = true)
        }
        _cats.value = filteredCats
    }

    fun getAllCatsFromApi() {
        viewModelScope.launch {
            try {
                val catsList = repository.getAllCatsFromApi()
                originalCatList = catsList
                _cats.value = catsList
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching cats from API: ${e.message}")
            }
        }
    }
}