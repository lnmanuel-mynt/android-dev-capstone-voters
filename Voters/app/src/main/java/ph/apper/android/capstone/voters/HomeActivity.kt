package ph.apper.android.capstone.voters

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_profile.*
import ph.apper.android.capstone.voters.api.APIClient
import ph.apper.android.capstone.voters.model.GetUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    // Shared Preferences parameters
    private val EMAIL_KEY = "email_key"
    private val FIRST_NAME = "first_name"
    private val USER_ID = "user_id"
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bt_profile.setOnClickListener(this)
        bt_find.setOnClickListener(this)
        bt_registration.setOnClickListener(this)

        val sharedPref = this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val email = sharedPref.getString(EMAIL_KEY, "email")
        val firstName = sharedPref.getString(FIRST_NAME, "user")
        id = sharedPref.getString(USER_ID, "ID").toString()
        Log.d("SHARED USER ID", "User ID: $id")
        Log.d("SHARED EMAIL", "Email: ${email}")
        val toast = Toast.makeText(applicationContext, "Welcome, ${firstName}!", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.BOTTOM, 0,32)
        toast.show()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            bt_profile.id -> {
                val nextActivityIntent = Intent(applicationContext, ProfileActivity::class.java)
                //finish()
                startActivity(nextActivityIntent)
            }
            bt_find.id -> {
                val nextActivityIntent = Intent(applicationContext, MainActivity::class.java)
                //finish()
                startActivity(nextActivityIntent)
            }
            bt_registration.id -> {
                // Get User Data
                getData(id)
            }
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
                    val nextActivityIntent = Intent(applicationContext, RegistrationActivity::class.java)
                    //finish()
                    startActivity(nextActivityIntent)
                } else {
                    Toast.makeText(applicationContext, "User already registered!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                t.message?.let { Log.d("GET USER API FAILURE", it) }
            }

        })
    }
}