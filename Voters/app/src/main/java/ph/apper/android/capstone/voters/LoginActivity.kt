package ph.apper.android.capstone.voters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import ph.apper.android.capstone.voters.api.APIClient
import ph.apper.android.capstone.voters.model.LoginRequest
import ph.apper.android.capstone.voters.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    // Shared Preferences parameters
    private val USER_ID = "user_id"
    private val EMAIL_KEY = "email_key"
    private val FIRST_NAME = "first_name"
    private val MIDDLE_NAME = "middle_name"
    private val LAST_NAME = "last_name"

    private lateinit var backToast: Toast
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bt_login_login.setOnClickListener(this)
        bt_login_signup.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            bt_login_login.id -> {
                val email = et_login_email.text.toString()
                val password = et_login_password.text.toString()
                if(email.isEmpty() or password.isEmpty()) {
                    Toast.makeText(applicationContext, "All fields are required!", Toast.LENGTH_SHORT).show()
                    return
                }
                // DB Logic
                login(email, password)
            }
            bt_login_signup.id -> {
                val nextActivityIntent = Intent(applicationContext, SignupActivity::class.java)
                finish()
                startActivity(nextActivityIntent)
            }
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel()
            super.onBackPressed()
            return
        } else {
            backToast = Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_SHORT)
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    private fun login(email: String, password: String) {
        val request = LoginRequest(email, password)
        val call: Call<LoginResponse> = APIClient.sendPost.login(request)
        call.enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.code() == 401) {
                    Toast.makeText(applicationContext, "Invalid login credentials!", Toast.LENGTH_LONG).show()
                } else {
                    val response: LoginResponse = response.body()!!
                    val sharedPref = this@LoginActivity.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                    with (sharedPref.edit()) {
                        putString(USER_ID, response.profile.id)
                        putString(EMAIL_KEY, response.profile.email)
                        putString(FIRST_NAME, response.profile.firstName)
                        putString(MIDDLE_NAME, response.profile.middleName)
                        putString(LAST_NAME, response.profile.lastName)
                        apply()
                        commit()
                    }
                    // Go to Home Activity
                    val nextActivityIntent = Intent(applicationContext, HomeActivity::class.java)
                    finish()
                    startActivity(nextActivityIntent)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.message?.let { Log.d("LOGIN API FAILURE", it) }
                Toast.makeText(applicationContext, "Failed to connect to server.", Toast.LENGTH_SHORT).show()
            }

        })
    }
}