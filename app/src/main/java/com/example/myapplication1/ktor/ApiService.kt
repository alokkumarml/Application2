package com.example.myapplication1.ktor


import android.util.Log
import com.example.myapplication1.modal.RegisterData
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.util.concurrent.Flow


class ApiService {

    // copy link
    // https://github.com/nameisjayant/Ktor-Http-Request-Android/blob/master/app/src/main/java/com/codingwithjks/ktorhttprequest/data/Network/ApiService.kt

    private val NETWORK_TIME_OUT = 6_000L

    val httpClientAndroid = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    useAlternativeNames = true
                    ignoreUnknownKeys = true
                    encodeDefaults = false
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = NETWORK_TIME_OUT
            connectTimeoutMillis = NETWORK_TIME_OUT
            socketTimeoutMillis = NETWORK_TIME_OUT
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
           // level = LogLevel.BODY
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

    }


    @OptIn(InternalAPI::class)
    suspend fun getPost(loginRequest: JsonObject): RegisterData {
//        try {
//            val response: HttpResponse =
//                httpClientAndroid.post("http://192.168.1.60:5000/loginuser") {
//                    contentType(ContentType.Application.Json)
//                    body = loginRequest.toString() // Convert JSON object to string
//                }
//            println("Response: ${response.status}")
//        } catch (e: Exception) {
//            println("Error: ${e.message}")
//
//        }


//        val response: HttpResponse =
//                httpClientAndroid.post("http://192.168.1.60:5000/loginuser") {
//                    contentType(ContentType.Application.Json)
//                    body = loginRequest.toString() // Convert JSON object to string
//                }
//
//        val responseBody = response.bodyAsText()
//        Json.decodeFromString<RegisterData>(responseBody) // Deserialize JSON response
//        println("Response: ${response.status}")


            val response = httpClientAndroid.post("http://192.168.1.86:5000/loginuser") {
                contentType(ContentType.Application.Json)
                body = loginRequest.toString()
            }
            val responseBody = response.bodyAsText()

      //  try {
            val data = Json{ ignoreUnknownKeys = true }.decodeFromString<RegisterData>(responseBody)
            println(data)
//        } catch (e: SerializationException) {
//            e.printStackTrace()
//            Log.e("aaaaa",e.printStackTrace().toString())
//        }




            return  data// Deserialize JSON response



    }
}


