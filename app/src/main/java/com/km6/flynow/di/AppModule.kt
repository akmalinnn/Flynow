package com.km6.flynow.di

import android.content.SharedPreferences
import com.km6.flynow.presentation.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule {
    private val networkModule =
        module {

        }

    private val localModule =
        module {

        }

    private val firebaseModule =
        module {

        }

    private val dataSourceModule =
        module {

        }

    private val repositoryModule =
        module {

        }

    private val viewModelModule =
        module {


            viewModelOf(::MainViewModel)

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
