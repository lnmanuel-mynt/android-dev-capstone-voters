package ph.apper.android.capstone.voters

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_profile.*
import ph.apper.android.capstone.voters.api.APIClient
import ph.apper.android.capstone.voters.model.GetUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    // Shared Preferences parameters
    private val USER_ID = "user_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Get User ID
        val sharedPref = this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val id = sharedPref.getString(USER_ID, "ID")
        Log.d("SHARED USER ID", "User ID: $id")

        // Get User Data
        if (id != null) {
            getData(id)
        }

    }

    private fun getData(id: String) {
        val call: Call<GetUserResponse> = APIClient.sendPost.getUser(id)
        call.enqueue(object: Callback<GetUserResponse> {
            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {
                if (response.code() == 404) {
                    "Application Status: N/A".also { tv_status.text = it }
                    "Voter's ID No: N/A".also { tv_voters_id.text = it }
                    "Province: N/A".also { tv_province.text = it }
                    "City: N/A".also { tv_city.text = it }
                    "Barangay: N/A".also { tv_barangay.text = it }
                    "Precinct No: N/A".also { tv_precinct.text = it }
                } else {
                    val response: GetUserResponse = response.body()!!
                    Log.d("RESPONSE BODY", response.toString())
                    ("Application Status: " + response.status).also { tv_status.text = it }
                    ("Voter's ID No: " + response.votersId).also { tv_voters_id.text = it }
                    ("Province: " + response.province).also { tv_province.text = it }
                    ("City: " + response.city).also { tv_city.text = it }
                    ("Barangay: " + response.barangay).also { tv_barangay.text = it }
                    ("Precinct No: " + response.precinctNumber).also { tv_precinct.text = it }
                }
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                t.message?.let { Log.d("GET USER API FAILURE", it) }
            }

        })
    }
}