package com.example.myapplication1

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val someString: String
) : ViewModel() {

    fun getSomeString(): String {
        return someString
    }
}