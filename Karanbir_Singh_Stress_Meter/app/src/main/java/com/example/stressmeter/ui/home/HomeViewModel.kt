package com.example.stressmeter.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    // Existing text LiveData
    private val _text = MutableLiveData<String>().apply {
        value = "Touch the image that best captures how stressed you feel right now"
    }
    val text: LiveData<String> = _text

    // New Data to store the list of random images and their scores
    private val _randomImages = MutableLiveData<List<Int>>()
    val randomImages: LiveData<List<Int>> get() = _randomImages

    private val _imageScores = MutableLiveData<Map<Int, Int>>()
    val imageScores: LiveData<Map<Int, Int>> get() = _imageScores

    // Method to set the random images and their scores
    fun setImagesAndScores(images: List<Int>, scores: Map<Int, Int>) {
        _randomImages.value = images
        _imageScores.value = scores
    }
}
