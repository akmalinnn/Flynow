package com.km6.flynow.presentation.notification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.km6.flynow.databinding.FragmentNotificationBinding
import com.km6.flynow.presentation.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private val viewModel: NotificationViewModel by viewModel()
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = NotificationAdapter(emptyList()) // Initialize adapter with empty list
        binding.rvNotification.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotification.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.notifications.observe(viewLifecycleOwner, Observer { notifications ->
            notifications?.let {
                adapter = NotificationAdapter(it) // Update adapter with new list
                binding.rvNotification.adapter = adapter
            }
        })

        viewModel.getToken()?.let { token ->
            if (token.isEmpty()) {
                showMustLoginFragment()
            } else {
                hideMustLoginFragment()
                viewModel.loadNotifications() // Load notifications if user is logged in
            }
        } ?: showMustLoginFragment()
    }

    private fun setupClickListeners() {
        binding.layoutMustLogin.btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }
    }

    private fun showMustLoginFragment() {
        binding.rvNotification.visibility = View.GONE
        binding.textView.visibility = View.GONE
        binding.layoutMustLogin.imageView.visibility = View.VISIBLE
        binding.layoutMustLogin.textView2.visibility = View.VISIBLE
        binding.layoutMustLogin.btnLogin.visibility = View.VISIBLE
    }

    private fun hideMustLoginFragment() {
        binding.rvNotification.visibility = View.VISIBLE
        binding.textView.visibility = View.VISIBLE
        binding.layoutMustLogin.imageView.visibility = View.GONE
        binding.layoutMustLogin.textView2.visibility = View.GONE
        binding.layoutMustLogin.btnLogin.visibility = View.GONE
    }
}
