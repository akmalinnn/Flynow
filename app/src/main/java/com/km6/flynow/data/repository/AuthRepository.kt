package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.AuthDataSource
import com.km6.flynow.data.source.network.model.login.LoginResponse
import com.km6.flynow.data.source.network.model.otp.VerifyOtpResponse
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<ResultWrapper<Pair<Boolean, LoginResponse>>>
    suspend fun register(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        imageFile: File
    ): Flow<ResultWrapper<Boolean>>
    suspend fun verifyOtp(email: String, otp: String): Flow<ResultWrapper<Boolean>>

    suspend fun resendOtp(email: String): Flow<ResultWrapper<Boolean>>

    suspend fun forgotPassword(email: String): Flow<ResultWrapper<Boolean>>
}

class AuthRepositoryImpl(private val authDataSource: AuthDataSource) : AuthRepository {

    override suspend fun login(email: String, password: String): Flow<ResultWrapper<Pair<Boolean, LoginResponse>>> {
        return proceedFlow {
            val response = authDataSource.login(email, password)
            if (response.data.token.isNotEmpty()) {
                Pair(true, response)
            } else {
                throw Exception("Invalid credentials")
            }
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        imageFile: File
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val response = authDataSource.register(name, email, password, phoneNumber, imageFile)
            if (response.message == "Register Successful") {
                true
            } else {
                throw Exception(response.message)
            }
        }
    }

    override suspend fun verifyOtp(email: String, otp: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val response = authDataSource.verifyOtp(email, otp)
            if (response.message == "User already verified") {
                true
            } else {
                throw Exception(response.message)
            }
        }
    }

    override suspend fun resendOtp(email: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val response = authDataSource.resendOtp(email)
            if (response.message == "New OTP has been sent to your email") {
                true
            } else {
                throw Exception(response.message)
            }
        }
    }

    override suspend fun forgotPassword(email: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val response = authDataSource.forgotPassword(email)
            if (response.message == "Reset Password Link Successfully Sent") {
                true
            } else {
                throw Exception(response.message)
            }
        }
    }


}
