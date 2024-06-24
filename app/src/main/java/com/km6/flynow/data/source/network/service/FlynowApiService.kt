package com.km6.flynow.data.source.network.service

import com.km6.flynow.data.source.network.model.history.HistoryItemResponse
import com.km6.flynow.BuildConfig
import com.km6.flynow.data.source.local.pref.UserPreference
import com.km6.flynow.data.source.network.model.airport.SearchAirportResponse
import com.km6.flynow.data.source.network.model.forget_password.ForgotPasswordRequest
import com.km6.flynow.data.source.network.model.forget_password.ForgotPasswordResponse
import com.km6.flynow.data.source.network.model.login.LoginResponse
import com.km6.flynow.data.source.network.model.otp.ResendOtpRequest
import com.km6.flynow.data.source.network.model.otp.ResendOtpResponse
import com.km6.flynow.data.source.network.model.otp.VerifyOtpRequest
import com.km6.flynow.data.source.network.model.otp.VerifyOtpResponse
import com.km6.flynow.data.source.network.model.payment.PaymentRequest

import com.km6.flynow.data.source.network.model.payment.PaymentResponse
import com.km6.flynow.data.source.network.model.register.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface FlynowApiService {
    @Multipart
    @POST("auth/login")
    suspend fun login(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
    ): LoginResponse

    @Multipart
    @POST("auth/register")
    suspend fun register(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part image: MultipartBody.Part
    ): RegisterResponse

    @GET("airports/search")
    suspend fun searchAirport(
        @Header("Authorization") token: String,
        @Query("keyword") keyword: String? = null
    ): SearchAirportResponse
    @PUT("auth/verify-otp")
    suspend fun verifyOtp(
        @Body requestBody: VerifyOtpRequest
    ): VerifyOtpResponse

    @PUT("auth/resend-otp")
    suspend fun resendOtp(
        @Body requestBody: ResendOtpRequest
    ): ResendOtpResponse

    @PUT("auth/forgot-password")
    suspend fun forgotPassword(
        @Body requestBody: ForgotPasswordRequest
    ): ForgotPasswordResponse

    @GET("history")
    suspend fun getHistory(
    ): HistoryItemResponse

    @GET("payment")
    suspend fun createPayment(
        @Body requestBody: PaymentRequest
    ): PaymentResponse


    companion object {
        @JvmStatic
        operator fun invoke(preference: UserPreference): FlynowApiService {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient =
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor { chain ->
                        val request =
                            chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer ${preference.getToken()}")
                        chain.proceed(request.build())
                    }
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(FlynowApiService::class.java)
        }
    }
}
