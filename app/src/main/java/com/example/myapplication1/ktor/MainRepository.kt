package com.example.myapplication1.ktor


import com.example.myapplication1.modal.RegisterData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.JsonObject

class MainRepository {

    var apiService=ApiService()

    fun getPost(loginRequest: JsonObject): Flow<RegisterData> = flow {
//        val idStr: String =emailOrMobileNumber
//            .substring(emailOrMobileNumber.lastIndexOf('+') + 1)
//        val jsonObject = JsonObject()
//        jsonObject.addProperty("emailid", idStr)
//        jsonObject.addProperty("password", password)

        emit(apiService.getPost(loginRequest))
    }.flowOn(Dispatchers.IO)

}