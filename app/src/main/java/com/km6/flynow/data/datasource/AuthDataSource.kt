package com.km6.flynow.data.datasource

import com.km6.flynow.data.source.network.model.login.LoginResponse
import com.km6.flynow.data.source.network.model.register.RegisterResponse
import com.km6.flynow.data.source.network.service.FlynowApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

interface AuthDataSource {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun login(email: String, password: String) : LoginResponse

    @Throws(exceptionClasses = [Exception::class])
    suspend fun register(name: String,
                         email: String,
                         password: String,
                         phoneNumber: String,
                         imageFile: File
    ) : RegisterResponse
}

class AuthDataSourceImpl(private val service: FlynowApiService) :AuthDataSource {

    override suspend fun login(email: String, password: String): LoginResponse {
        val emailRequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val passwordRequestBody = password.toRequestBody("text/plain".toMediaTypeOrNull())
        return service.login(emailRequestBody, passwordRequestBody)
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        imageFile: File
    ): RegisterResponse {
        val nameRequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val emailRequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val passwordRequestBody = password.toRequestBody("text/plain".toMediaTypeOrNull())
        val phoneNumberRequestBody = phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())

        val imageRequestBody = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, imageRequestBody)

        return service.register(
            nameRequestBody,
            emailRequestBody,
            passwordRequestBody,
            phoneNumberRequestBody,
            imagePart
        )
    }
}