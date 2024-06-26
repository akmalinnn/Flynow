package com.km6.flynow.data.datasource

import com.km6.flynow.data.source.network.model.forget_password.ForgotPasswordRequest
import com.km6.flynow.data.source.network.model.forget_password.ForgotPasswordResponse
import com.km6.flynow.data.source.network.model.login.LoginResponse
import com.km6.flynow.data.source.network.model.otp.ResendOtpRequest
import com.km6.flynow.data.source.network.model.otp.ResendOtpResponse
import com.km6.flynow.data.source.network.model.register.RegisterResponse
import com.km6.flynow.data.source.network.model.otp.VerifyOtpRequest
import com.km6.flynow.data.source.network.model.otp.VerifyOtpResponse
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

    @Throws(exceptionClasses = [Exception::class])
    suspend fun verifyOtp(email: String, otp: String): VerifyOtpResponse

    @Throws(exceptionClasses = [Exception::class])
    suspend fun resendOtp(email: String): ResendOtpResponse

    @Throws(exceptionClasses = [Exception::class])
    suspend fun forgotPassword(email: String): ForgotPasswordResponse
}

class AuthDataSourceImpl(private val service: FlynowApiService) : AuthDataSource {

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

    override suspend fun verifyOtp(email: String, otp: String): VerifyOtpResponse {
        val requestBody = VerifyOtpRequest(email, otp)
        return service.verifyOtp(requestBody)
    }

    override suspend fun resendOtp(email: String): ResendOtpResponse {
        val requestBody = ResendOtpRequest(email)
        return service.resendOtp(requestBody)
    }

    override suspend fun forgotPassword(email: String): ForgotPasswordResponse {
        val requestBody = ForgotPasswordRequest(email)
        return service.forgotPassword(requestBody)
    }
}
