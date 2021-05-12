package ph.apper.android.capstone.voters

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val sharedPreference = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val id = sharedPreference.getString("user_id", "")
        val first_name = sharedPreference.getString("first_name", "")
        val middle_name = sharedPreference.getString("middle_name", "")
        val last_name = sharedPreference.getString("last_name", "")
    }
}