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

        checkFirstRun()
        setupBottomNav()


    }


    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)

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

}
