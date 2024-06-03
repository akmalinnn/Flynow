package com.km6.flynow.di

import android.content.SharedPreferences
import com.km6.flynow.data.datasource.AuthDataSource
import com.km6.flynow.data.datasource.AuthDataSourceImpl
import com.km6.flynow.data.repository.AuthRepository
import com.km6.flynow.data.repository.AuthRepositoryImpl
import com.km6.flynow.data.repository.UserRepository
import com.km6.flynow.data.repository.UserRepositoryImpl
import com.km6.flynow.data.source.local.pref.UserPreference
import com.km6.flynow.data.source.local.pref.UserPreferenceImpl
import com.km6.flynow.data.source.network.service.FlynowApiService
import com.km6.flynow.presentation.history.HistoryViewModel
import com.km6.flynow.presentation.login.LoginViewModel
import com.km6.flynow.presentation.main.MainViewModel
import com.km6.flynow.presentation.notification.NotificationViewModel
import com.km6.flynow.presentation.profile.ProfileViewModel
import com.km6.flynow.presentation.register.RegisterViewModel
import com.km6.flynow.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule {
    private val networkModule =
        module {
            single<FlynowApiService> { FlynowApiService.invoke() }
        }

    private val localModule =
        module {
            single<SharedPreferences> {
                SharedPreferenceUtils.createPreference(
                    androidContext(),
                    UserPreferenceImpl.PREF_NAME
                )
            }
            single<UserPreference> { UserPreferenceImpl(get()) }
        }

    private val firebaseModule =
        module {

        }

    private val dataSourceModule =
        module {
            single<AuthDataSource> { AuthDataSourceImpl(get()) }
        }

    private val repositoryModule =
        module {
            single<AuthRepository> { AuthRepositoryImpl(get()) }
            single<UserRepository> { UserRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::LoginViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::RegisterViewModel)
            viewModelOf(::ProfileViewModel)
            viewModelOf(::HistoryViewModel)
            viewModelOf(::NotificationViewModel)

        }

    val modules =
        listOf<Module>(
            networkModule,
            localModule,
            firebaseModule,
            dataSourceModule,
            repositoryModule,
            viewModelModule,
        )
}
