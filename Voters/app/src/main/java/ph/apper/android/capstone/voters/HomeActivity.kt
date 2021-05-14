package ph.apper.android.capstone.voters

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_home.*
import ph.apper.android.capstone.voters.api.APIClient
import ph.apper.android.capstone.voters.model.GetUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

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
        bt_candidates.setOnClickListener(this)

        val sharedPref = this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val email = sharedPref.getString(EMAIL_KEY, "email")
        val firstName = sharedPref.getString(FIRST_NAME, "user")?.capitalize(Locale.ENGLISH)
        id = sharedPref.getString(USER_ID, "ID").toString()
        Log.d("SHARED USER ID", "User ID: $id")
        Log.d("SHARED EMAIL", "Email: ${email}")
        "Hello, $firstName!".also { tv_hello.text = it }
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            bt_profile.id -> {
                val nextActivityIntent = Intent(applicationContext, ProfileActivity::class.java)
                //finish()
                startActivity(nextActivityIntent)
            }
            bt_find.id -> {
                val nextActivityIntent = Intent(applicationContext, FindMyPrecinctActivity::class.java)
                finish()
                startActivity(nextActivityIntent)
            }
            bt_registration.id -> {
                // Get User Data
                getData(id)
            }
            bt_candidates.id -> {
                val nextActivityIntent = Intent(applicationContext, CandidateActivity::class.java)
                startActivity(nextActivityIntent)
            }
        }
    }

    override fun onBackPressed() {

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are you sure you want to log out?")
        dialogBuilder.setCancelable(false)
        dialogBuilder.setPositiveButton("OK") { _, _ ->
            run {
                val nextActivityIntent = Intent(applicationContext, LoginActivity::class.java)
                finish()
                startActivity(nextActivityIntent)
            }
        }
        dialogBuilder.setNegativeButton("CANCEL") { dialog, _ ->
            dialog.cancel()
        }

        val logoutDialog = dialogBuilder.create()
        logoutDialog.show()

        val view: Window? = logoutDialog.window
        val message: TextView = view!!.findViewById(android.R.id.message)
        val btn1: Button = view!!.findViewById(android.R.id.button1)
        val btn2: Button = view!!.findViewById(android.R.id.button2)

        val poppins = Typeface.createFromAsset(assets,"poppins.otf")

        message.typeface = poppins
        btn1.typeface = poppins
        btn2.typeface = poppins
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
                    // finish()
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