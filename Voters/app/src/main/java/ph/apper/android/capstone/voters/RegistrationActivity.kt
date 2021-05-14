package ph.apper.android.capstone.voters

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    override fun onBackPressed() {
        val nextActivityIntent = Intent(applicationContext, HomeActivity::class.java)
        finish()
        startActivity(nextActivityIntent)
    }
}