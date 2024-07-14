package com.example.myapplication1.modal

import kotlinx.serialization.Serializable

@Serializable
data class DataItem(val country_code: Int = 0,
                    val image: String = "",
                    val register_type: String = "",
                    val password: String = "",
                    val gender: String = "",
                    val date_of_birth: String = "",
                    val firstname: String = "",
                    val lastname: String = "",
                    val emailid: String = "",
                    val phone_number: Int,
                    val UserID: Int = 0 //,
                  //  val token: String = ""
)
