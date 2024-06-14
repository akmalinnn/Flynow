package com.km6.flynow.presentation.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.km6.flynow.R // Replace with your actual R class import
import com.km6.flynow.data.model.Notification // Replace with your actual Notification model

class NotificationAdapter(private val notifications: List<Notification>) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        holder.bind(notification)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.iv_icon)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.tv_date)
        private val messageTextView: TextView = itemView.findViewById(R.id.tv_message)
        private val additionalTextView: TextView = itemView.findViewById(R.id.tv_additional)

        fun bind(notification: Notification) {
            titleTextView.text = notification.title
            dateTextView.text = notification.date
            messageTextView.text = notification.message
            additionalTextView.text = notification.additionalInfo

            // Replace ic_tab_notification with actual drawable resource based on notification type
            iconImageView.setImageResource(R.drawable.ic_tab_notification)
        }
    }
}
