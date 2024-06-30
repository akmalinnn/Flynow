package com.km6.flynow.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.km6.flynow.R
import com.km6.flynow.databinding.FragmentProfileBinding
import com.km6.flynow.presentation.login.LoginActivity
import com.km6.flynow.presentation.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        checkLoginStatus()
        setProfile()


    }

    private fun setClickListeners() {
        binding.ivEdit.setOnClickListener{
                startActivity(
                    Intent(requireContext(), LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    },
                )
        }
        binding.ivLogout.setOnClickListener{
            viewModel.logOut()
            startActivity(
                Intent(requireContext(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                },
            )
        }
        binding.tvLogout.setOnClickListener{
            viewModel.logOut()
            startActivity(
                Intent(requireContext(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                },
            )
        }

        binding.tvSetting.setOnClickListener{
        }
    }

    private fun setProfile() {
        binding.tvProfileNamePage.text = viewModel.getUser()?.name
        binding.tvProfileEmailPage.text = viewModel.getUser()?.email
//        binding.ivProfilePhoto.load(viewModel.getUser()?.image)
//        binding.tvToken.text = viewModel.getToken()
    }

    private fun checkLoginStatus() {
        val token = viewModel.getToken()
        if (token == null) {
            navigateToLogin()
        }
    }



    private fun navigateToLogin() {
        startActivity(
            Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }
}