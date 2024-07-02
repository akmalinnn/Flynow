package com.km6.flynow.presentation.checkout.checkout_penumpang

import android.app.DatePickerDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.km6.flynow.R
import com.km6.flynow.data.model.BioPenumpang
import com.km6.flynow.data.model.Passenger
import com.km6.flynow.databinding.ItemFormBiodataPenumpangBinding
import com.xwray.groupie.viewbinding.BindableItem
import java.util.Calendar

class PassengerItem(private val bio: Passenger) :
    BindableItem<ItemFormBiodataPenumpangBinding>() {

    override fun getLayout(): Int {
        return R.layout.item_form_biodata_penumpang
    }

    override fun initializeViewBinding(view: View): ItemFormBiodataPenumpangBinding {
        binding = ItemFormBiodataPenumpangBinding.bind(view)

        return binding
    }

    private lateinit var binding: ItemFormBiodataPenumpangBinding

    override fun bind(viewBinding: ItemFormBiodataPenumpangBinding, position: Int) {
        viewBinding.tvTitleFormPassenger.text = "Passenger ${position + 1} - ${bio.passengerType}"

        // Set up AutoCompleteTitle
        val titles = listOf("Mr.", "Mrs.", "Miss")
        val autoCompleteTitle = viewBinding.autocompleteTitle
        val titleAdapter =
            ArrayAdapter(viewBinding.root.context, R.layout.item_title_biodata, titles)

        autoCompleteTitle.setAdapter(titleAdapter)
        autoCompleteTitle.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val selectedTitle = adapterView.getItemAtPosition(i) as String
                viewBinding.etFullName.setText("$selectedTitle ${viewBinding.etFullName.text}")
//            Toast.makeText(viewBinding.root.context, "Title: $selectedTitle", Toast.LENGTH_SHORT).show()
            }

        // Set up birthDate
        viewBinding.etDate.setOnClickListener {
            showBirthDate(viewBinding)
        }

        // Set up AutoCompleteDocument
        val itemDoc = DocumentType.entries.map { it.displayName }
        val autoCompleteDoc = viewBinding.autoCompleteKtpPaspor
        val adapterdoc =
            ArrayAdapter(viewBinding.root.context, R.layout.item_title_biodata, itemDoc)

        autoCompleteDoc.setAdapter(adapterdoc)
        autoCompleteDoc.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val itemSelected = adapterView.getItemAtPosition(i)
//                Toast.makeText(viewBinding.root.context, "Item : $itemSelected", Toast.LENGTH_SHORT)
//                    .show()
            }

        // Set up AutoCompleteCountry
        val itemCountry = listOf(
            "Afghanistan",
            "Albania",
            "Algeria",
            "Andorra",
            "Angola",
            "Antigua and Barbuda",
            "Argentina",
            "Armenia",
            "Australia",
            "Austria",
            "Azerbaijan",
            "Bahamas",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Belarus",
            "Belgium",
            "Belize",
            "Benin",
            "Bhutan",
            "Bolivia",
            "Bosnia and Herzegovina",
            "Botswana",
            "Brazil",
            "Brunei",
            "Bulgaria",
            "Burkina Faso",
            "Burundi",
            "Cabo Verde",
            "Cambodia",
            "Cameroon",
            "Canada",
            "Central African Republic",
            "Chad",
            "Chile",
            "China",
            "Colombia",
            "Comoros",
            "Congo",
            "Costa Rica",
            "Croatia",
            "Cuba",
            "Cyprus",
            "Czechia",
            "Denmark",
            "Djibouti",
            "Dominica",
            "Dominican Republic",
            "Ecuador",
            "Egypt",
            "El Salvador",
            "Equatorial Guinea",
            "Eritrea",
            "Estonia",
            "Eswatini",
            "Ethiopia",
            "Fiji",
            "Finland",
            "France",
            "Gabon",
            "Gambia",
            "Georgia",
            "Germany",
            "Ghana",
            "Greece",
            "Grenada",
            "Guatemala",
            "Guinea",
            "Guinea-Bissau",
            "Guyana",
            "Haiti",
            "Honduras",
            "Hungary",
            "Iceland",
            "India",
            "Indonesia",
            "Iran",
            "Iraq",
            "Ireland",
            "Israel",
            "Italy",
            "Jamaica",
            "Japan",
            "Jordan",
            "Kazakhstan",
            "Kenya",
            "Kiribati",
            "Korea, North",
            "Korea, South",
            "Kosovo",
            "Kuwait",
            "Kyrgyzstan",
            "Laos",
            "Latvia",
            "Lebanon",
            "Lesotho",
            "Liberia",
            "Libya",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Madagascar",
            "Malawi",
            "Malaysia",
            "Maldives",
            "Mali",
            "Malta",
            "Marshall Islands",
            "Mauritania",
            "Mauritius",
            "Mexico",
            "Micronesia",
            "Moldova",
            "Monaco",
            "Mongolia",
            "Montenegro",
            "Morocco",
            "Mozambique",
            "Myanmar",
            "Namibia",
            "Nauru",
            "Nepal",
            "Netherlands",
            "New Zealand",
            "Nicaragua",
            "Niger",
            "Nigeria",
            "North Macedonia",
            "Norway",
            "Oman",
            "Pakistan",
            "Palau",
            "Palestine",
            "Panama",
            "Papua New Guinea",
            "Paraguay",
            "Peru",
            "Philippines",
            "Poland",
            "Portugal",
            "Qatar",
            "Romania",
            "Russia",
            "Rwanda",
            "Saint Kitts and Nevis",
            "Saint Lucia",
            "Saint Vincent and the Grenadines",
            "Samoa",
            "San Marino",
            "Sao Tome and Principe",
            "Saudi Arabia",
            "Senegal",
            "Serbia",
            "Seychelles",
            "Sierra Leone",
            "Singapore",
            "Slovakia",
            "Slovenia",
            "Solomon Islands",
            "Somalia",
            "South Africa",
            "South Sudan",
            "Spain",
            "Sri Lanka",
            "Sudan",
            "Suriname",
            "Sweden",
            "Switzerland",
            "Syria",
            "Taiwan",
            "Tajikistan",
            "Tanzania",
            "Thailand",
            "Timor-Leste",
            "Togo",
            "Tonga",
            "Trinidad and Tobago",
            "Tunisia",
            "Turkey",
            "Turkmenistan",
            "Tuvalu",
            "Uganda",
            "Ukraine",
            "United Arab Emirates",
            "United Kingdom",
            "United States",
            "Uruguay",
            "Uzbekistan",
            "Vanuatu",
            "Vatican City",
            "Venezuela",
            "Vietnam",
            "Yemen",
            "Zambia",
            "Zimbabwe"
        )
        val autoCompleteCountry = viewBinding.autoCompleteNegaraPenerbit
        val adapterCountry =
            ArrayAdapter(viewBinding.root.context, R.layout.item_title_biodata, itemCountry)

        autoCompleteCountry.setAdapter(adapterCountry)
        autoCompleteCountry.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val itemSelected = adapterView.getItemAtPosition(i)
//                Toast.makeText(viewBinding.root.context, "Item : $itemSelected", Toast.LENGTH_SHORT)
//                    .show()
            }

        // Set up ExpDate
        viewBinding.etExpDate.setOnClickListener {
            showExpDate(viewBinding)
        }
    }

    private fun showBirthDate(binding: ItemFormBiodataPenumpangBinding) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateBirth =
            DatePickerDialog(
                binding.root.context,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Set the selected date to the input
                    val selectedDate = "${selectedYear}-${selectedMonth + 1}-${selectedDay}"
                    binding.etDate.setText(selectedDate)
                },
                year,
                month,
                day
            )
        dateBirth.show()
    }

    private fun showExpDate(binding: ItemFormBiodataPenumpangBinding) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateExp =
            DatePickerDialog(
                binding.root.context,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Set the selected date to the input
                    val selectedDate = "${selectedYear}-${selectedMonth + 1}-${selectedDay}"
                    binding.etExpDate.setText(selectedDate)
                },
                year,
                month,
                day,
            )
        dateExp.show()
    }

    fun validateForm(): Boolean {
        var isValid = true

        if (binding.etFullName.text.toString().isEmpty()) {
            binding.tilFullName.error = "Full Name is required"
            isValid = false
        } else {
            binding.tilFullName.error = null
        }

        if (binding.etDate.text.toString().isEmpty()) {
            binding.tilDate.error = "Date of Birth is required"
            isValid = false
        } else {
            binding.tilDate.error = null
        }

        if (binding.etNoKtpPaspor.text.toString().isEmpty()) {
            binding.tilNoKtpPaspor.error = "Document Number is required"
            isValid = false
        } else {
            binding.tilNoKtpPaspor.error = null
        }

        if (binding.autoCompleteKtpPaspor.text.toString().isEmpty()) {
            binding.tilKtpPaspor.error = "Document Type is required"
            isValid = false
        } else {
            binding.tilKtpPaspor.error = null
        }

        if (binding.autoCompleteNegaraPenerbit.text.toString().isEmpty()) {
            binding.tilNegaraPenerbit.error = "Country is required"
            isValid = false
        } else {
            binding.tilNegaraPenerbit.error = null
        }

        return isValid
    }

    fun getPassengerData(): Passenger {
        return Passenger(
            passengerType = bio.passengerType,
            name = binding.etFullName.text.toString(),
//            lastName = binding.etFamilyName.text.toString(),
            dateOfBirth = binding.etDate.text.toString(),
            nationality = binding.etNationality.text.toString(),
            docType = binding.autoCompleteKtpPaspor.text.toString(),
            docNumber = binding.etNoKtpPaspor.text.toString(),
            issuingCountry = binding.autoCompleteNegaraPenerbit.text.toString(),
            expiryDate = binding.etExpDate.text.toString()
        )
    }
}

enum class DocumentType(val displayName: String) {
    KTP("ktp"),
    Paspor("paspor"),
    Kartu_Keluarga("kartu_keluarga");
}
