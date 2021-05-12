package ph.apper.android.capstone.voters

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    // Shared Preferences parameters
    private val EMAIL_KEY = "email_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bt_profile.setOnClickListener(this)
        bt_find.setOnClickListener(this)

        val sharedPref = this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val email = sharedPref.getString(EMAIL_KEY, "user")
        Log.d("SHARED EMAIL", "email: ${email}")
        Toast.makeText(applicationContext, "Welcome, ${email}!", Toast.LENGTH_LONG).show()
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
        }
    }
}