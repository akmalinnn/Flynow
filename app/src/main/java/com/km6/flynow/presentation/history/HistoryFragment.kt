package com.km6.flynow.presentation.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.km6.flynow.databinding.FragmentHistoryBinding
import com.km6.flynow.databinding.FragmentNotificationBinding
import com.km6.flynow.presentation.login.LoginActivity
import com.km6.flynow.presentation.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    private val viewModel: HistoryViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        checkLoginStatus()
    }


    private fun checkLoginStatus() {
        val token = viewModel.getToken()
        if (token == null) {
            // Token not found, show "must login" fragment
            showMustLoginFragment()
            setClickListeners()
        } else {
            // Token found, hide "must login" fragment
            hideMustLoginFragment()
        }
    }

    private fun setClickListeners() {
        binding.layoutMustLogin.btnLogin.setOnClickListener{
            startActivity(
                Intent(requireContext(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                },
            )
        }
    }

    private fun showMustLoginFragment() {
        binding.rvHistory.visibility = View.GONE
        binding.textView.visibility = View.GONE
        binding.layoutMustLogin.imageView.visibility = View.VISIBLE
        binding.layoutMustLogin.textView2.visibility = View.VISIBLE
        binding.layoutMustLogin.btnLogin.visibility = View.VISIBLE
    }

    private fun hideMustLoginFragment() {
        binding.rvHistory.visibility = View.VISIBLE
        binding.textView.visibility = View.VISIBLE
        binding.layoutMustLogin.imageView.visibility = View.GONE
        binding.layoutMustLogin.textView2.visibility = View.GONE
        binding.layoutMustLogin.btnLogin.visibility = View.GONE
    }

}