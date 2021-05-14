package ph.apper.android.capstone.voters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*
import ph.apper.android.capstone.voters.api.APIClient
import ph.apper.android.capstone.voters.model.SignupRequest
import ph.apper.android.capstone.voters.model.SignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SignupActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        bt_signup_signup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            bt_signup_signup.id -> {
                val firstName = et_signup_firstname.text.toString().toLowerCase(Locale.ENGLISH)
                val middleName = et_signup_middlename.text.toString().toLowerCase(Locale.ENGLISH)
                val lastName = et_signup_surname.text.toString().toLowerCase(Locale.ENGLISH)
                val email = et_signup_email.text.toString()
                val password = et_signup_password.text.toString()
                val confirmPassword = et_signup_confirm.text.toString()
                when {
                    firstName.isEmpty() or middleName.isEmpty() or lastName.isEmpty() or email.isEmpty() or password.isEmpty() ->
                        Toast.makeText(applicationContext, "All fields are required", Toast.LENGTH_SHORT).show()
                    password.length < 8 ->
                        Toast.makeText(applicationContext, "Insufficient password length", Toast.LENGTH_SHORT).show()
                    password != confirmPassword ->
                        Toast.makeText(applicationContext, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    else -> signup(firstName, middleName, lastName, email, password)
                }
            }
        }
    }

    override fun onBackPressed() {
        val nextActivityIntent = Intent(applicationContext, LoginActivity::class.java)
        finish()
        startActivity(nextActivityIntent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun signup(firstName: String, middleName: String, lastName: String, email: String, password: String) {
        val request = SignupRequest(firstName, middleName, lastName, email, password)
        val call: Call<SignupResponse> = APIClient.sendPost.signup(request)
        call.enqueue(object: Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                if (response.code() == 400) {
                    Toast.makeText(applicationContext, "Failed to create user", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Signup successful. Log-in to proceed",
                        Toast.LENGTH_SHORT
                    ).show()
                    val nextActivityIntent = Intent(applicationContext, LoginActivity::class.java)
                    finish()
                    startActivity(nextActivityIntent)
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                t.message?.let { Log.d("SIGNUP API FAILURE", it) }
                Toast.makeText(applicationContext, "Failed to connect to server", Toast.LENGTH_SHORT).show()
            }

        })
    }
}