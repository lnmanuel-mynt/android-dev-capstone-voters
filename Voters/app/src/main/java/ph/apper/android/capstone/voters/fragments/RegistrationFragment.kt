package ph.apper.android.capstone.voters.fragments

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_registration.*
import ph.apper.android.capstone.voters.FindMyPrecinctActivity
import ph.apper.android.capstone.voters.HomeActivity
import ph.apper.android.capstone.voters.R
import ph.apper.android.capstone.voters.api.VoterAPIClient
import ph.apper.android.capstone.voters.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        val sharedPreference = requireActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        id = sharedPreference.getString("user_id", "").toString()
        firstName = sharedPreference.getString("first_name", "").toString()
        middleName = sharedPreference.getString("middle_name", "").toString()
        lastName = sharedPreference.getString("last_name", "").toString()
        tv_name?.text = "${lastName.toUpperCase()}, ${firstName.toUpperCase()} (${middleName.toUpperCase()})"

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(view: View) {
        val datePicker = view.findViewById<DatePicker>(R.id.date_picker_birthday)

        var civilStatusArray = arrayOf(
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
                // do nothing
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }

        var sexArray = arrayOf(
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
                // do nothing
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }

        view.findViewById<Button>(R.id.btn_submit).setOnClickListener {
            val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.of(datePicker.year, datePicker.month + 1, datePicker.dayOfMonth)
            val birthDate: String = date.format(dateTimeFormatter)
            // todo age validation

            val birthProvince = et_birth_province.text.toString()
            val birthCity = et_birth_city.text.toString()
            val civilStatus: String = spinner_civil_status.selectedItem.toString()
            val sex: String = spinner_sex.selectedItem.toString()
            val street = et_street.text.toString()
            val subdivision = et_subdivision.text.toString()
            val barangay = et_barangay.text.toString()
            val city = et_city.text.toString()
            val province = et_province.text.toString()
            val yearsInCity = et_years_in_city.text.toString()
            val yearsInPH = et_years_in_philippines.text.toString()
            val dateNow = LocalDate.now()
            val dateRegistered: String = dateNow.format(dateTimeFormatter)

            var params = mapOf<String, String>(
                    "id" to id,
                    "firstName" to firstName,
                    "middleName" to middleName,
                    "lastName" to lastName,
                    "birthDate" to date.format(dateTimeFormatter),
                    "birthProvince" to et_birth_province.text.toString(),
                    "birthCity" to spinner_civil_status.selectedItem.toString(),
                    "civilStatus" to spinner_civil_status.selectedItem.toString(),
                    "sex" to spinner_sex.selectedItem.toString(),
                    "street" to et_street.text.toString(),
                    "subdivision" to et_subdivision.text.toString(),
                    "city" to et_city.text.toString(),
                    "province" to et_province.text.toString(),
                    "yearsInCity" to et_years_in_city.text.toString(),
                    "yearsInPH" to et_years_in_philippines.text.toString(),
            )

            if (birthProvince.isBlank() or birthCity.isBlank() or street.isBlank() or barangay.isBlank()
                or city.isBlank() or province.isBlank() or yearsInCity.isBlank() or yearsInPH.isBlank()) {
                Snackbar.make(requireView(), "All fields are required", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()

                Log.d("Params", "$params")
                Log.d("Name", "${params["lastName"]}, ${params["lastName"]} (${params["lastName"]})")

            } else register(id, firstName, middleName, lastName, birthDate, birthProvince, birthCity, civilStatus, sex, street, subdivision, barangay, city, province, yearsInCity, yearsInPH, dateRegistered)
        }

        view.findViewById<TextView>(R.id.tv_back).setOnClickListener {
            var nextActivityIntent: Intent = Intent(requireContext(), HomeActivity::class.java)
            // finish()
            startActivity(nextActivityIntent)
        }
    }

    private fun register(id: String, firstName: String, middleName: String, lastName: String, birthDate: String, birthProvince: String,
                         birthCity: String, civilStatus: String, sex: String, street: String, subdivision: String, barangay: String,
                         city: String, province: String, yearsInCity: String, yearsInPH: String, dateRegistered:String) {
        var request = RegisterRequest(id, firstName, middleName, lastName, birthDate, birthProvince, birthCity, civilStatus, sex,
            street, subdivision, barangay, city, province, yearsInCity, yearsInPH, dateRegistered)
        val call: Call<VoterRegistrationResponse> = VoterAPIClient.post.register(request)

        call.enqueue(object : Callback<VoterRegistrationResponse> {
            override fun onResponse(call: Call<VoterRegistrationResponse>, responseVoter: Response<VoterRegistrationResponse>) {
                val statusCode = responseVoter.code()
                if(statusCode == 201) {
                    Log.d("Request", "$request")
                    findNavController().navigate(R.id.action_RegistrationFragment_to_RegistrationProcessingFragment)
                }
                else
                    Snackbar.make(view!!, "Error $statusCode: ${responseVoter.message()}", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
            }

            override fun onFailure(call: Call<VoterRegistrationResponse>, t: Throwable) {
                Snackbar.make(view!!, "Failed api call. $t", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
        })
    }
}