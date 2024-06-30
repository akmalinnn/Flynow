package com.km6.flynow.presentation.notification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.km6.flynow.R
import com.km6.flynow.data.model.notification.Notification
import com.km6.flynow.databinding.FragmentNotificationBinding
import com.km6.flynow.presentation.login.LoginActivity
import com.km6.flynow.presentation.notification.notification_detail.NotificationDetailActivity
import com.km6.flynow.presentation.profile.ProfileViewModel
import com.km6.flynow.utils.GridSpacingItemDecoration
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private val notificationViewModel: NotificationViewModel by viewModel()

    private val notificationAdapter: NotificationAdapter by lazy {
        NotificationAdapter {
            NotificationDetailActivity.startActivity(requireContext(), it.id)
        }
    }

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLoginStatus()
        setupListNotification()
        getNotificationList()
    }

    private fun setupListNotification() {
        val itemDecoration = GridSpacingItemDecoration(1, 12, true)
        binding.rvNotification.apply {
            layoutManager = LinearLayoutManager(context).apply {
                reverseLayout = true
                stackFromEnd = true
            }

            adapter = notificationAdapter
            addItemDecoration(itemDecoration)
        }
    }


    private fun getNotificationList() {
        val token = notificationViewModel.getToken()
        if (token != null) {
            notificationViewModel.getNotification().observe(viewLifecycleOwner) { it ->
                it.proceedWhen(
                    doOnLoading = {
                        binding.rvNotification.isVisible = false
                        binding.pbLoading.isVisible = true
                    },
                    doOnSuccess = {
                        binding.rvNotification.isVisible = true
                        binding.pbLoading.isVisible = false
                        it.payload?.let { data -> bindNotificationList(data) }
                    },
                    doOnError = { error ->
                        Toast.makeText(
                            requireContext(),
                            "Error: ${error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        profileViewModel.logOut()
                        startActivity(
                            Intent(requireContext(), LoginActivity::class.java).apply {
                                flags =
                                    Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            },
                        )
                    },
                    doOnEmpty = { error ->
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.anda_belum_memiliki_notifikasi),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }

    private fun bindNotificationList(data: List<Notification>) {
        notificationAdapter.submitData(data)
    }

    private fun checkLoginStatus() {
        val token = notificationViewModel.getToken()
        if (token == null) {
            showMustLoginFragment()
            setClickListeners()
        } else {
            hideMustLoginFragment()
        }
    }

    private fun setClickListeners() {
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
