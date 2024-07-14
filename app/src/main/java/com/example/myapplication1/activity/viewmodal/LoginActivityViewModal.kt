package com.example.myapplication1.activity.viewmodal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.MyApplication
import com.example.myapplication1.ktor.MainRepository
import com.example.myapplication1.preferencesDataStore.DataStoreManager
import com.example.myapplication1.sealed.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject

class LoginActivityViewModal:ViewModel() {

    private val _apiStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val apiStateFlow : StateFlow<ApiState> = _apiStateFlow

    val dataStoreManager = DataStoreManager(MyApplication.context!!)

    val ISLOGGINEDFlow = dataStoreManager.ISLOGGINEDFlow.asLiveData()

    fun getPost(loginRequest: JsonObject) = viewModelScope.launch {
        MainRepository().getPost(loginRequest)
            .onStart {
                _apiStateFlow.value = ApiState.Loading
            }
            .catch { e->
                _apiStateFlow.value = ApiState.Failure(e)
            }.collect { response->


                if (response.result.equals("Login Success")) {

                    saveExampleData(
                        true, response?.data?.get(0)!!.UserID,
                        response?.data?.get(0)!!.firstname + " " + response?.data?.get(
                            0
                        )!!.lastname
                    )


                }else{
                  //  error.value=response.body()?.result

                    _apiStateFlow.value = ApiState.Success(response)

                }


            }
    }

    fun saveExampleData(exampleBoolean: Boolean, exampleInt: Int, exampleString: String) {

//        viewModelScope.launch {
//            dataStoreManager.ISLOGGINEDFlow.collect {
//
//                Log.e("checkcollect","checkcollect")
//
//            }
//        }


        viewModelScope.launch {
            dataStoreManager.saveExampleData(exampleBoolean, exampleInt, exampleString)
        }
    }

}