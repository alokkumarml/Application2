package com.example.myapplication1.sealed

import com.example.myapplication1.modal.RegisterData

sealed class ApiState {

    object Loading : ApiState()
    object Empty : ApiState()
    class Success(val response: RegisterData) : ApiState()
    class Failure(val error:Throwable) : ApiState()

}