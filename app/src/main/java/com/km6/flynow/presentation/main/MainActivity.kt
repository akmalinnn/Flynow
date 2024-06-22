package com.km6.flynow.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.km6.flynow.R
import com.km6.flynow.data.source.network.service.FlynowApiService
import com.km6.flynow.databinding.ActivityMainBinding
import com.km6.flynow.presentation.filter_result.FilterResultActivity
import com.km6.flynow.presentation.intro.MyAppIntroActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getDataFromApi()
        val intent = Intent(this, FilterResultActivity::class.java)
        startActivity(intent)
        checkFirstRun()
        setupBottomNav()


//        testCrash()
    }
//
//    //crashlytics ok
//    private fun testCrash() {
//        throw RuntimeException("Test Crash")
//    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            when (destination.id) {
//                R.id.menu_tab_account-> {
//                    if (!mainViewModel.isLoggedIn()) {
////                        navigateToLogin()
//                        controller.popBackStack(R.id.menu_tab_home, false)
//                    }
//                }
//            }
//        }
    }

    private fun checkFirstRun() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        // delete negation, Intro just showing at the first time run
        if (isFirstRun) {
            startActivity(Intent(this, MyAppIntroActivity::class.java))
            // Set isFirstRun to false to indicate that the app has been launched before
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
        }
    }

    private fun getDataFromApi() {
        val apiService = FlynowApiService.invoke()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.searchFlight("CGK", "DPS", "2024-08-02", "2024-08-02","1","1","0","economy","price-asc")
                Log.d("Coins", "Response: $response")
            } catch (e: Exception) {
                if (e is retrofit2.HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    Log.e("Coins Error", "HTTP Error: ${e.code()} - ${e.message()}\nBody: $errorBody", e)
                } else {
                    Log.e("Coins Error", "Error: ${e.message}", e)
                }
            }
        }
    }

//    private fun navigateToLogin() {
//        startActivity(Intent(this, LoginActivity::class.java))
//    }
}
