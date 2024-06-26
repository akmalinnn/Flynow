package com.km6.flynow.presentation.filter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.km6.flynow.R
import com.km6.flynow.data.model.Filter
import com.km6.flynow.data.model.SeatClass
import com.km6.flynow.databinding.ItemFilterBinding
import com.km6.flynow.databinding.ItemSeatClassBinding
import com.km6.flynow.presentation.choose_seat_class.ChooseSeatClassViewModel
import com.km6.flynow.presentation.choose_seat_class.adapter.SeatClassAdapter
import com.km6.flynow.presentation.filter.FilterViewModel

class FilterAdapter(
    private val filters: List<Filter>,
    private val viewModel: FilterViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<FilterAdapter.OptionViewHolder>() {

    init {
        viewModel.selectedPosition.observe(lifecycleOwner, Observer {
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val filter = filters [position]
        holder.bind(filter, viewModel.selectedPosition.value == position)

        holder.itemView.setOnClickListener {
            viewModel.selectPosition(position)
        }
    }

    override fun getItemCount(): Int {
        return filters.size
    }

    class OptionViewHolder(private val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(filter: Filter, isSelected: Boolean) {
            binding.tvFilter1.text = filter.category
            binding.tvFilter2.text = filter.desc

            val backgroundColor = ContextCompat.getColor(binding.root.context, if (isSelected) R.color.md_theme_primary else R.color.md_theme_background)
            val textColor = ContextCompat.getColor(binding.root.context, if (isSelected) android.R.color.white else com.google.android.material.R.color.m3_default_color_primary_text)
            binding.root.setCardBackgroundColor(backgroundColor)
            binding.tvFilter1.setTextColor(textColor)
            binding.tvFilter2.setTextColor(textColor)
        }
    }
}
