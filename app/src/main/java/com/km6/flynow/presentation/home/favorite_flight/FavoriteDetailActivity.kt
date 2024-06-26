package com.km6.flynow.presentation.home.favorite_flight

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.km6.flynow.R
import com.km6.flynow.data.model.FavoriteFlight
import com.km6.flynow.data.model.history.History
import com.km6.flynow.databinding.ActivityDetailFavoriteFlightBinding
import com.km6.flynow.utils.toCustomDateFormat
import com.km6.flynow.utils.toIDRFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFavoriteFlightBinding
    private val viewModel: FavoriteDetailViewModel by viewModel {
        parametersOf(intent.extras)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFavoriteFlightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
        setClickListener()
    }

    private fun observeViewModel() {

        viewModel.favoriteItem.observe(this) { item ->
            item?.let { bindFlightFavoriteItem(it) } ?: finish()
        }

    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun bindFlightFavoriteItem(item: FavoriteFlight) {
        with(binding) {


            val destinationText =
                "${item.departureCity} -> ${item.arrivalCity}"
            tvFlightDestination.text = destinationText
            tvFlightDate.text = item.departureTime.toCustomDateFormat()
            tvFlightAirline.text = item.airline
            tvMessage.text = item.description
            tvFlightPrice.text = item.price.toIDRFormat()
            binding.ivDestinationsImage.load(item.image) {
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }
            binding.tvDiscount.text = getString(R.string.discount, item.discount)
            }

    }

    companion object {
        const val EXTRA_FAVORITE_FLIGHT_ITEM = "extra_favorite_flight_item"

        fun startActivity(context: Context, item: FavoriteFlight) {
            val intent = Intent(context, FavoriteDetailActivity::class.java)
            intent.putExtra(EXTRA_FAVORITE_FLIGHT_ITEM, item)
            context.startActivity(intent)
        }
    }
}
