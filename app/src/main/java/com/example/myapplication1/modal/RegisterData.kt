package com.example.myapplication1.modal

import kotlinx.serialization.Serializable

@Serializable
data class RegisterData(val result: String = "",
                        val data: List<DataItem>?,
                        val success: Boolean = false)
