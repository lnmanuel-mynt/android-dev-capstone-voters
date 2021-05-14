package ph.apper.android.capstone.voters

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_find_my_precinct.*
import ph.apper.android.capstone.voters.api.VoterAPIClient
import ph.apper.android.capstone.voters.fragments.SearchResultDialogFragment
import ph.apper.android.capstone.voters.model.FindMyPrecinctRequest
import ph.apper.android.capstone.voters.model.FindMyPrecinctResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class FindMyPrecinctActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_my_precinct)

        btn_search.setOnClickListener(this)
        tv_back.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_search.id -> {
                if (et_first_name.text.isBlank() or et_middle_name.text.isBlank() or et_last_name.text.isBlank())
                    Toast.makeText(applicationContext, "All fields are required", Toast.LENGTH_SHORT).show()
                else {
                    val datePicker = findViewById<DatePicker>(R.id.date_picker_birthday)
                    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val birthDate =
                        LocalDate.of(datePicker.year, datePicker.month + 1, datePicker.dayOfMonth)
                    val searchParams = mapOf(
                        "firstName" to et_first_name.text.toString().toLowerCase(Locale.ROOT),
                        "middleName" to et_middle_name.text.toString().toLowerCase(Locale.ROOT),
                        "lastName" to et_last_name.text.toString().toLowerCase(Locale.ROOT),
                        "birthDate" to birthDate.format(dateTimeFormatter)
                    )
                    findMyPrecinct(searchParams)
                }
            }
            tv_back.id -> {
                val nextActivityIntent = Intent(applicationContext, HomeActivity::class.java)
                finish()
                startActivity(nextActivityIntent)
            }
        }
    }

    override fun onBackPressed() {
        val nextActivityIntent = Intent(applicationContext, HomeActivity::class.java)
        finish()
        startActivity(nextActivityIntent)
    }

    private fun findMyPrecinct(searchParams: Map<String, String>) {
        val request = FindMyPrecinctRequest(
            searchParams["firstName"].toString(),
            searchParams["middleName"].toString(),
            searchParams["lastName"].toString(),
            searchParams["birthDate"].toString()
        )
        val call: Call<FindMyPrecinctResponse> = VoterAPIClient.post.findMyPrecinct(request)

        call.enqueue(object : Callback<FindMyPrecinctResponse> {
            override fun onResponse(call: Call<FindMyPrecinctResponse>, response: Response<FindMyPrecinctResponse>) {
                when (val statusCode = response.code()) {
                    200 -> {
                        val response: FindMyPrecinctResponse = response!!.body()!!
                        val dialogParams = mapOf(
                            "precinctNumber" to response.precinctInfo.precinctNumber,
                            "barangay" to response.precinctInfo.barangay,
                            "city" to response.precinctInfo.city,
                            "province" to response.precinctInfo.province,
                            "pollingPlace" to response.precinctInfo.pollingPlace,
                        )
                        showSearchResult(dialogParams)
                    }
                    404 -> Toast.makeText(applicationContext, "No record found", Toast.LENGTH_LONG).show()
                    else -> Toast.makeText(applicationContext, "Error: $statusCode: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<FindMyPrecinctResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed api call. $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showSearchResult(dialogParams: Map<String, String>) {
        SearchResultDialogFragment.newInstance(dialogParams).show(supportFragmentManager, SearchResultDialogFragment.TAG)
    }
}