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

class FindMyPrecinctActivity : AppCompatActivity(), View.OnClickListener {

    var precinctNumber = ""
    var barangay = ""
    var city = ""
    var province = ""
    var pollingPlace = ""

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
                val firstName = et_first_name.text.toString()
                val middleName = et_middle_name.text.toString()
                val lastName = et_last_name.text.toString()
                val datePicker = findViewById<DatePicker>(R.id.date_picker_birthday)
                val dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
                val date = LocalDate.of(datePicker.year, datePicker.month + 1, datePicker.dayOfMonth)
                val birthDate: String = date.format(dateTimeFormatter)
                findMyPrecinct(firstName, middleName, lastName, birthDate)
            }
            tv_back.id -> {
                var nextActivityIntent: Intent = Intent(applicationContext, HomeActivity::class.java)
                // finish()
                startActivity(nextActivityIntent)
            }
        }
    }

    private fun findMyPrecinct(firstName: String, middleName: String, lastName: String, birthDate: String) {
        var request = FindMyPrecinctRequest(firstName, middleName, lastName, birthDate)
        val call: Call<FindMyPrecinctResponse> = VoterAPIClient.post.findMyPrecinct(request)

        call.enqueue(object : Callback<FindMyPrecinctResponse> {
            override fun onResponse(call: Call<FindMyPrecinctResponse>, response: Response<FindMyPrecinctResponse>) {
                val statusCode = response.code()
                if (statusCode == 200) {
                    var response: FindMyPrecinctResponse = response!!.body()!!
                    precinctNumber = response.precinctInfo.precinctNumber
                    barangay = response.precinctInfo.barangay
                    city = response.precinctInfo.city
                    province = response.precinctInfo.province
                    pollingPlace = response.precinctInfo.pollingPlace
                    showSearchResult(precinctNumber, barangay, city, province, pollingPlace)
                } else {
                    // todo no record available
                    Toast.makeText(applicationContext, "Error $statusCode: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FindMyPrecinctResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed api call. $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showSearchResult(precinctNumber: String, barangay: String, city: String, province: String, pollingPlace: String) {
        SearchResultDialogFragment.newInstance(precinctNumber, barangay, city, province, pollingPlace).show(supportFragmentManager, SearchResultDialogFragment.TAG)
    }
}