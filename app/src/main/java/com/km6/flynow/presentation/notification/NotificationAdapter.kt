package com.km6.flynow.presentation.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.km6.flynow.R
import com.km6.flynow.data.model.notification.Notification
import com.km6.flynow.databinding.ItemNotificationBinding
import com.km6.flynow.utils.toCustomDateFormat
import com.km6.flynow.utils.toCustomTimeFormat

class NotificationAdapter(private val itemClick: (Notification) -> Unit) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Notification>() {
                override fun areItemsTheSame(
                    oldItem: Notification,
                    newItem: Notification,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Notification,
                    newItem: Notification,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Notification>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): NotificationViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding, itemClick)
    }


    override fun onBindViewHolder(
        holder: NotificationViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class NotificationViewHolder(
        private val binding: ItemNotificationBinding,
        val itemClick: (Notification) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Notification) {


            binding.tvTitle.text = item.type
            binding.tvMessage.text = item.message
            binding.tvTime.text = item.createdAt.toCustomTimeFormat()

            if (item.isRead == true) {
                binding.cvNotification.background = ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.bg_status_notification_read
                )
            }

            itemView.setOnClickListener { itemClick(item) }
        }
    }
}
