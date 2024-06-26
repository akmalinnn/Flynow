package com.km6.flynow.presentation.notification.notification_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.km6.flynow.data.model.notification.NotificationDetail
import com.km6.flynow.databinding.ActivityDetailNotificationBinding
import com.km6.flynow.utils.proceedWhen
import com.km6.flynow.utils.toCustomDateFormat
import com.km6.flynow.utils.toCustomTimeFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NotificationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailNotificationBinding

    private val viewModel: NotificationDetailViewmodel by viewModel {
        parametersOf(intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListener()
        viewModel.notificationDetail?.let { getNotificationDetail(it) }
    }

    private fun getNotificationDetail(id: Int) {
        viewModel.readNotification(id).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let {
                        bindNotificationItem(it)
                    }
                },
                doOnLoading = {

                },
                doOnError = {

                },
                doOnEmpty = {

                },
            )
        }
    }


    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }


    private fun bindNotificationItem(notificationItem: NotificationDetail) {

        notificationItem.let {
            binding.tvMessage.text = notificationItem.message
            binding.tvType.text = notificationItem.type
            val dateTime =
                "${notificationItem.createdAt.toCustomDateFormat()}-${notificationItem.createdAt.toCustomTimeFormat()}"
            binding.tvTime.text = dateTime
        }
    }


    companion object {
        const val EXTRA_NOTIFICATION_ITEM = "extra_notification_item"

        fun startActivity(context: Context, id: Int) {
            val intent = Intent(context, NotificationDetailActivity::class.java)
            intent.putExtra(EXTRA_NOTIFICATION_ITEM, id)
            context.startActivity(intent)
        }
    }
}
