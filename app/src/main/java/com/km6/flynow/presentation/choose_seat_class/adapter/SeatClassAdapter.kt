package com.km6.flynow.presentation.choose_seat_class.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.km6.flynow.R
import com.km6.flynow.data.model.SeatClass
import com.km6.flynow.databinding.ItemSeatClassBinding
import com.km6.flynow.presentation.choose_seat_class.ChooseSeatClassViewModel

class SeatClassAdapter(
    private val seatClasses: List<SeatClass>,
    private val viewModel: ChooseSeatClassViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<SeatClassAdapter.OptionViewHolder>() {

    init {
        viewModel.selectedPosition.observe(lifecycleOwner, Observer {
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val binding = ItemSeatClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val seatClass = seatClasses[position]
        holder.bind(seatClass, viewModel.selectedPosition.value == position)

        holder.itemView.setOnClickListener {
            viewModel.selectPosition(position)
        }
    }

    override fun getItemCount(): Int {
        return seatClasses.size
    }

    class OptionViewHolder(private val binding: ItemSeatClassBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(seatClass: SeatClass, isSelected: Boolean) {
            binding.tvSeatClass.text = seatClass.name
            binding.tvPrice.text = seatClass.price

            val backgroundColor = ContextCompat.getColor(binding.root.context, if (isSelected) R.color.md_theme_primary else R.color.md_theme_background)
            val textColor = ContextCompat.getColor(binding.root.context, if (isSelected) android.R.color.white else com.google.android.material.R.color.m3_default_color_primary_text)
            binding.root.setCardBackgroundColor(backgroundColor)
            binding.tvSeatClass.setTextColor(textColor)
            binding.tvPrice.setTextColor(textColor)
        }
    }
}
