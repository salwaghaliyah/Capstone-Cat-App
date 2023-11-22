package com.dicoding.capstonecat

import com.dicoding.capstonecat.data.CatRepository

class ViewModelFactory(private val repository: CatRepository) {

//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return when {
//            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
//                MainViewModel(repository) as T
//            }
//            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
//                DetailViewModel(repository) as T
//            }
//            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
//                FavoriteViewModel(repository) as T
//            }
//            modelClass.isAssignableFrom(CameraViewModel::class.java) -> {
//                CameraViewModel(repository) as T
//            }
//            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//        }
//    }

}