package com.km6.flynow.presentation.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.km6.flynow.databinding.FragmentCalendarBottomSheetBinding
import com.km6.flynow.presentation.choose_destination.ChooseDestinationFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import android.widget.CalendarView


class CalendarBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCalendarBottomSheetBinding

    private var dateSelectedListener: OnDateSelectedListener? = null
    private var source: String? = null

    private var selectedDate= 0L
    private var formattedDate = ""
    private var isoDate = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCalendarBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnSave.setOnClickListener {
            saveDate()
        }
    }

    private fun saveDate() {
        dateSelectedListener?.onDateSelected(selectedDate,formattedDate,isoDate,source ?: "")
        dismiss()
    }

    private fun setupView() {
        source = arguments?.getString(CalendarBottomSheetFragment.ARG_SOURCE)
        val date = arguments?.getLong(CalendarBottomSheetFragment.ARG_SELECTED_DATE)

        selectedDate = if (date != 0L && date != null) {
            date
        } else {
            binding.cvCalendar.date
        }

        formattedDate = getFormattedDate(selectedDate)
        isoDate = getIsoFormattedDate(selectedDate)


        val calendar = Calendar.getInstance()

        val dDate = arguments?.getLong(CalendarBottomSheetFragment.ARG_DEPARTURE_DATE)

        if (source == "returnDate" && dDate != null) {
            binding.cvCalendar.minDate = dDate
        } else {
            binding.cvCalendar.minDate = calendar.timeInMillis
        }


        if (source == "departureDate") {
            binding.tvTitleDate.text = "Tanggal Berangkat"

        } else if (source == "returnDate") {
            binding.tvTitleDate.text = "Tanggal Pulang"
        }

        binding.cvCalendar.date = selectedDate
        binding.tvDate.text = getFormattedDate(selectedDate)
        binding.cvCalendar.setOnDateChangeListener{_, year, month, dayOfMonth ->
            formattedDate = getFormattedDate(year, month, dayOfMonth)
            isoDate = getIsoFormattedDate(year, month, dayOfMonth)
            selectedDate = getTimestamp(year, month, dayOfMonth)
            binding.tvDate.text = formattedDate
        }

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun getIsoFormattedDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, 0, 0, 0) // Setel waktu ke tengah malam
        calendar.set(Calendar.MILLISECOND, 0) // Setel milidetik ke 0 untuk konsistensi
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.format(calendar.time)
    }

    private fun getIsoFormattedDate(milliseconds: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliseconds
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        return getIsoFormattedDate(year, month, dayOfMonth)
    }


    fun getTimestamp(year: Int, month: Int, dayOfMonth: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun getFormattedDate(year: Int, month: Int, dayOfMonth: Int): String {
        return "$dayOfMonth ${getMonthName(month)} $year"
    }

    private fun getFormattedDate(milliseconds: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliseconds
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        return getFormattedDate(year, month, dayOfMonth)
    }

    private fun getMonthName(month: Int): String {
        val monthNames = arrayOf("Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")
        return monthNames[month]
    }

    fun setOnDateSelectedListener(listener: OnDateSelectedListener) {
        dateSelectedListener = listener
    }

    companion object {
        private const val ARG_SOURCE = "source"
        private const val ARG_SELECTED_DATE = "date"
        private const val ARG_DEPARTURE_DATE = "dDate"

        @JvmStatic
        fun newInstance(source: String, date: Long, dDate: Long) =
            CalendarBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SOURCE, source)
                    putLong(ARG_SELECTED_DATE,date)
                    putLong(ARG_DEPARTURE_DATE, dDate)
                    }
                }
    }
}

interface OnDateSelectedListener {
    fun onDateSelected(selectedDate:Long, formattedDate: String, isoDate: String, source: String)
}