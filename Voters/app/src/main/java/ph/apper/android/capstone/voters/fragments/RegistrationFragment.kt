package ph.apper.android.capstone.voters.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_registration.*
import ph.apper.android.capstone.voters.HomeActivity
import ph.apper.android.capstone.voters.R
import ph.apper.android.capstone.voters.api.VoterAPIClient
import ph.apper.android.capstone.voters.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class RegistrationFragment: Fragment() {

    var id: String = ""
    var firstName: String = ""
    var middleName: String = ""
    var lastName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        val sharedPreference = requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        id = sharedPreference.getString("user_id", "").toString()
        firstName = sharedPreference.getString("first_name", "").toString()
        middleName = sharedPreference.getString("middle_name", "").toString()
        lastName = sharedPreference.getString("last_name", "").toString()
        tv_name?.text = "${lastName.toUpperCase(Locale.ROOT)}, " +
                "${firstName.toUpperCase(Locale.ROOT)} " +
                "(${middleName.toUpperCase(Locale.ROOT)})"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(view: View) {
        val datePicker = view.findViewById<DatePicker>(R.id.date_picker_birthday)

        val civilStatusArray = arrayOf(
            CivilStatus.SINGLE,
            CivilStatus.MARRIED,
            CivilStatus.DIVORCED,
            CivilStatus.WIDOWED
        )

        val arrayCivilStatusAdapter =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, civilStatusArray)
        spinner_civil_status.adapter = arrayCivilStatusAdapter

        spinner_civil_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val sexArray = arrayOf(
            Sex.MALE,
            Sex.FEMALE
        )

        val arraySexAdapter =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, sexArray)
        spinner_sex.adapter = arraySexAdapter

        spinner_sex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        view.findViewById<CheckBox>(R.id.cb_agree).setOnCheckedChangeListener { _, isChecked ->
            btn_submit.isEnabled = isChecked
        }

        view.findViewById<Button>(R.id.btn_submit).setOnClickListener {
            val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val birthDate = LocalDate.of(datePicker.year, datePicker.month + 1, datePicker.dayOfMonth)
            val dateNow = LocalDate.now()

            when {
                et_birth_province.text.isBlank() or et_birth_city.text.isBlank() or
                        et_street.text.isBlank() or et_barangay.text.isBlank() or
                        et_city.text.isBlank() or et_province.text.isBlank() or
                        et_years_in_city.text.isBlank() or et_years_in_philippines.text.isBlank() -> {
                    Toast.makeText(requireContext(), "All fields are required. Use N/A for blank fields", Toast.LENGTH_SHORT).show()
                }
                ChronoUnit.YEARS.between(dateNow, birthDate) < 18 -> Toast.makeText(requireContext(), "You must be at least 18 years of age to register", Toast.LENGTH_SHORT).show()
                else -> {
                    val registerParams = mapOf<String, String>(
                        "id" to id,
                        "firstName" to firstName.toLowerCase(Locale.ROOT),
                        "middleName" to middleName.toLowerCase(Locale.ROOT),
                        "lastName" to lastName.toLowerCase(Locale.ROOT),
                        "birthDate" to birthDate.format(dateTimeFormatter),
                        "birthProvince" to et_birth_province.text.toString().toLowerCase(Locale.ROOT),
                        "birthCity" to  et_birth_city.text.toString().toLowerCase(Locale.ROOT),
                        "civilStatus" to spinner_civil_status.selectedItem.toString(),
                        "sex" to spinner_sex.selectedItem.toString(),
                        "street" to et_street.text.toString().toLowerCase(Locale.ROOT),
                        "barangay" to et_barangay.text.toString().toLowerCase(Locale.ROOT),
                        "subdivision" to et_subdivision.text.toString().toLowerCase(Locale.ROOT),
                        "city" to et_city.text.toString().toLowerCase(Locale.ROOT),
                        "province" to et_province.text.toString().toLowerCase(Locale.ROOT),
                        "yearsInCity" to et_years_in_city.text.toString(),
                        "yearsInPH" to et_years_in_philippines.text.toString(),
                        "dateRegistered" to dateNow.format(dateTimeFormatter)
                    )
                    register(registerParams)
                }
            }
        }

        view.findViewById<TextView>(R.id.tv_back).setOnClickListener {
            val nextActivityIntent = Intent(requireContext(), HomeActivity::class.java)
            // finish()
            startActivity(nextActivityIntent)
        }
    }

    private fun register(registerParams: Map<String, String>) {
        val request = RegisterRequest(
            registerParams["id"].toString(),
            registerParams["firstName"].toString(),
            registerParams["middleName"].toString(),
            registerParams["lastName"].toString(),
            registerParams["birthDate"].toString(),
            registerParams[" birthProvince"].toString(),
            registerParams["birthCity"].toString(),
            registerParams["civilStatus"].toString(),
            registerParams["sex"].toString(),
            registerParams["street"].toString(),
            registerParams["subdivision"].toString(),
            registerParams["barangay"].toString(),
            registerParams["city"].toString(),
            registerParams["province"].toString(),
            registerParams["yearsInCity"].toString(),
            registerParams["yearsInPH"].toString(),
            registerParams["dateRegistered"].toString()
        )

        val call: Call<VoterRegistrationResponse> = VoterAPIClient.post.register(request)

        call.enqueue(object : Callback<VoterRegistrationResponse> {
            override fun onResponse(
                call: Call<VoterRegistrationResponse>,
                responseVoter: Response<VoterRegistrationResponse>
            ) {
                val statusCode = responseVoter.code()
                if (statusCode == 201) {
                    findNavController().navigate(R.id.action_RegistrationFragment_to_RegistrationProcessingFragment)
                } else
                    Toast.makeText(requireContext(), "Error $statusCode: ${responseVoter.message()}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<VoterRegistrationResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to connect to server", Toast.LENGTH_SHORT).show()
            }
        })
    }
}