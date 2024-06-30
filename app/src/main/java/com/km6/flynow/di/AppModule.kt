package com.km6.flynow.di

import com.km6.flynow.presentation.notification.NotificationViewModel
import android.content.SharedPreferences
import com.km6.flynow.data.datasource.AuthDataSource
import com.km6.flynow.data.datasource.AuthDataSourceImpl
import com.km6.flynow.data.datasource.DetailFlightDataSource
import com.km6.flynow.data.datasource.DetailFlightDataSourceImpl
import com.km6.flynow.data.datasource.FlightApiDataSource
import com.km6.flynow.data.datasource.FlightDataSource
import com.km6.flynow.data.datasource.airport.AirportApiDataSource
import com.km6.flynow.data.datasource.airport.AirportDataSource
import com.km6.flynow.data.datasource.booking.BookingApiDataSource
import com.km6.flynow.data.datasource.booking.BookingDataSource
import com.km6.flynow.data.datasource.destination_history.DestinationHistoryDataSource
import com.km6.flynow.data.datasource.destination_history.DestinationHistoryDatabaseDataSource
import com.km6.flynow.data.datasource.history.HistoryApiDataSource
import com.km6.flynow.data.datasource.history.HistoryDataSource
import com.km6.flynow.data.datasource.notification.NotificationApiDataSource
import com.km6.flynow.data.datasource.payment.PaymentApiDataSource
import com.km6.flynow.data.datasource.payment.PaymentDataSource
import com.km6.flynow.data.datasource.notification.NotificationDatasource
import com.km6.flynow.data.datasource.notification.NotificationDetailApiDataSource
import com.km6.flynow.data.datasource.notification.NotificationDetailDatasource
import com.km6.flynow.data.repository.AirportRepository
import com.km6.flynow.data.repository.AirportRepositoryImpl
import com.km6.flynow.data.repository.AuthRepository
import com.km6.flynow.data.repository.AuthRepositoryImpl
import com.km6.flynow.data.repository.DestinationHistoryRepository
import com.km6.flynow.data.repository.DestinationHistoryRepositoryImpl
import com.km6.flynow.data.repository.FlightRepository
import com.km6.flynow.data.repository.FlightRepositoryImpl
import com.km6.flynow.data.repository.HistoryRepository
import com.km6.flynow.data.repository.HistoryRepositoryImpl
import com.km6.flynow.data.repository.NotificationDetailRepository
import com.km6.flynow.data.repository.NotificationDetailRepositoryImpl
import com.km6.flynow.data.repository.NotificationRepository
import com.km6.flynow.data.repository.NotificationRepositoryImpl
import com.km6.flynow.data.repository.PaymentRepository
import com.km6.flynow.presentation.home.favorite_flight.FavoriteDetailViewModel

import com.km6.flynow.data.repository.UserRepository
import com.km6.flynow.data.repository.UserRepositoryImpl
import com.km6.flynow.data.source.local.database.AppDatabase
import com.km6.flynow.data.source.local.database.dao.DestinationHistoryDao
import com.km6.flynow.data.source.local.pref.UserPreference
import com.km6.flynow.data.source.local.pref.UserPreferenceImpl
import com.km6.flynow.data.source.network.service.FlynowApiService
//import com.km6.flynow.presentation.filter_result.FilterResultViewModel
import com.km6.flynow.presentation.choose_destination.ChooseDestinationViewModel
import com.km6.flynow.presentation.choose_passanger.ChoosePassangerViewModel
import com.km6.flynow.presentation.choose_seat_class.ChooseSeatClassViewModel
import com.km6.flynow.presentation.filter_result.FilterResultViewModel
import com.km6.flynow.presentation.forgotpassword.ForgotPasswordViewModel
import com.km6.flynow.presentation.history.HistoryViewModel
import com.km6.flynow.presentation.history.historydetail.HistoryDetailViewModel
import com.km6.flynow.presentation.home.HomeViewModel
import com.km6.flynow.presentation.login.LoginViewModel
import com.km6.flynow.presentation.main.MainViewModel
import com.km6.flynow.presentation.otp.OtpViewModel
import com.km6.flynow.presentation.payment.PaymentViewModel
import com.km6.flynow.presentation.profile.ProfileViewModel
import com.km6.flynow.presentation.register.RegisterViewModel
import com.km6.flynow.presentation.notification.notification_detail.NotificationDetailViewmodel
import com.km6.flynow.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module


import com.km6.flynow.data.datasource.favorite_flight.FavoriteFlightDataSource
import com.km6.flynow.data.datasource.favorite_flight.FavoriteFlightApiDataSource
import com.km6.flynow.data.datasource.seat.SeatApiDataSource
import com.km6.flynow.data.datasource.seat.SeatDataSource
import com.km6.flynow.data.repository.BookingRepository
import com.km6.flynow.data.repository.DetailFlightRepository
import com.km6.flynow.data.repository.DetailFlightRepositoryImpl
import com.km6.flynow.data.repository.FavoriteFlightRepository
import com.km6.flynow.data.repository.FavoriteFlightRepositoryImpl
import com.km6.flynow.data.repository.SeatRepository
import com.km6.flynow.data.repository.SeatRepositoryImpl
import com.km6.flynow.presentation.checkout.chooseseat.SelectPassengerSeatViewModel
import com.km6.flynow.presentation.flight_detail.FlightDetailViewModel

object AppModule {
    private val networkModule =
        module {
            single<FlynowApiService> { FlynowApiService.invoke(get()) }
        }

    private val localModule =
        module {
            single<AppDatabase> { AppDatabase.createInstance(androidContext()) }
            single<DestinationHistoryDao> { get<AppDatabase>().destinationHistoryDao() }
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
            single<FlightDataSource> { FlightApiDataSource(get()) }
            single<AirportDataSource> {AirportApiDataSource(get())}
            single<HistoryDataSource> { HistoryApiDataSource(get()) }
            single<FavoriteFlightDataSource> { FavoriteFlightApiDataSource(get()) }
            single<DestinationHistoryDataSource> {DestinationHistoryDatabaseDataSource(get())}
            single<PaymentDataSource> { PaymentApiDataSource(get()) }
            single<BookingDataSource> { BookingApiDataSource(get()) }
            single<SeatDataSource> { SeatApiDataSource(get()) }
            single<DetailFlightDataSource> { DetailFlightDataSourceImpl(get()) }
            single<NotificationDatasource> { NotificationApiDataSource(get()) }
            single<NotificationDetailDatasource> { NotificationDetailApiDataSource(get()) }

        }

    private val repositoryModule =
        module {
            single<AuthRepository> { AuthRepositoryImpl(get()) }
            single<UserRepository> { UserRepositoryImpl(get()) }
            single<AirportRepository> { AirportRepositoryImpl(get())}
            single<HistoryRepository> { HistoryRepositoryImpl(get()) }
            single<FavoriteFlightRepository> { FavoriteFlightRepositoryImpl(get()) }
            single<DestinationHistoryRepository> { DestinationHistoryRepositoryImpl(get())}
            single<FlightRepository> { FlightRepositoryImpl(get()) }
            single<PaymentRepository> { PaymentRepository(get()) }
            single<BookingRepository> { BookingRepository(get()) }
            single<DetailFlightRepository> { DetailFlightRepositoryImpl(get()) }
            single<NotificationRepository> { NotificationRepositoryImpl(get()) }
            single<SeatRepository> { SeatRepositoryImpl(get()) }
            single<NotificationDetailRepository> { NotificationDetailRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::LoginViewModel)
            viewModelOf(::MainViewModel)
            viewModelOf(::RegisterViewModel)
            viewModelOf(::ProfileViewModel)
            viewModelOf(::HistoryViewModel)
            viewModelOf(::NotificationViewModel)
            viewModelOf(::ChooseDestinationViewModel)
            viewModel { params ->
                ChoosePassangerViewModel(
                    extras = params.get()
                )
            }
            viewModelOf(::HomeViewModel)
            viewModelOf(::ChooseSeatClassViewModel)
            viewModelOf(::OtpViewModel)
            viewModelOf(::ForgotPasswordViewModel)
            viewModelOf(::FilterResultViewModel)
            viewModelOf(::FlightDetailViewModel)
            viewModelOf(::HistoryDetailViewModel)
            viewModelOf(::PaymentViewModel)
            viewModelOf(::NotificationDetailViewmodel)
            viewModelOf(::FavoriteDetailViewModel)
            viewModelOf(::SelectPassengerSeatViewModel)
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
