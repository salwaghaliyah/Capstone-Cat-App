package com.dicoding.capstonecat.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dicoding.capstonecat.data.CatRepository

class HomeViewModel(
    private val repository: CatRepository
): ViewModel() {

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
    }
}