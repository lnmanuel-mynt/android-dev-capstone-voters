package ph.apper.android.capstone.voters

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ph.apper.android.capstone.voters.model.CandidateInfo

class CandidateActivity : AppCompatActivity(){
    companion object{
        var candidateList: ArrayList<CandidateInfo> = ArrayList()

        var candidatePosition: String = ""

        fun clearArray(){
            candidateList.clear()
        }

        fun populateList(items: ArrayList<CandidateInfo>?){
            candidateList = items!!
        }

        var selectedCandidate: CandidateInfo = CandidateInfo()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate)
    }

    override fun onBackPressed() {
        val nextActivityIntent = Intent(applicationContext, HomeActivity::class.java)
        finish()
        startActivity(nextActivityIntent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}