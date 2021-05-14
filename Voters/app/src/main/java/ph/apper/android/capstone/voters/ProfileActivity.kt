package ph.apper.android.capstone.voters

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.tv_back
import ph.apper.android.capstone.voters.api.APIClient
import ph.apper.android.capstone.voters.model.GetUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    private val userId = "user_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tv_back.setOnClickListener(this)

        val sharedPref = this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val id = sharedPref.getString(userId, "")

        if (id != null) {
            getData(id)
        }
    }

    override fun onBackPressed() {
        val nextActivityIntent = Intent(applicationContext, HomeActivity::class.java)
        finish()
        startActivity(nextActivityIntent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun getData(id: String) {
        val call: Call<GetUserResponse> = APIClient.sendPost.getUser(id)
        call.enqueue(object: Callback<GetUserResponse> {
            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {
                if (response.code() == 404) {
                    "Registration Status:  N/A".also { tv_status.text = it }
                    "Voter's ID No:  N/A".also { tv_voters_id.text = it }
                    "Province:  N/A".also { tv_province.text = it }
                    "City:  N/A".also { tv_city.text = it }
                    "Barangay:  N/A".also { tv_barangay.text = it }
                    "Precinct No:  N/A".also { tv_precinct.text = it }
                } else {
                    val response: GetUserResponse = response.body()!!
                    ("Registration Status:  " + response.status.toUpperCase(Locale.ENGLISH)).also { tv_status.text = it }
                    ("Voter's ID No:  " + response.votersId.toUpperCase(Locale.ENGLISH)).also { tv_voters_id.text = it }
                    ("Province:  " + response.province.toUpperCase(Locale.ENGLISH)).also { tv_province.text = it }
                    ("City:  " + response.city.toUpperCase(Locale.ENGLISH)).also { tv_city.text = it }
                    ("Barangay:  " + response.barangay.toUpperCase(Locale.ENGLISH)).also { tv_barangay.text = it }
                    ("Precinct No:  " + response.precinctNumber.toUpperCase(Locale.ENGLISH)).also { tv_precinct.text = it }
                }
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                t.message?.let { Log.d("GET USER API FAILURE", it) }
                Toast.makeText(applicationContext, "User not found", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            tv_back.id -> {
                val nextActivityIntent = Intent(applicationContext, HomeActivity::class.java)
                finish()
                startActivity(nextActivityIntent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }
}