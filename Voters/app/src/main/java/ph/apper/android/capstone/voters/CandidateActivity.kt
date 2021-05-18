package ph.apper.android.capstone.voters

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*
import ph.apper.android.capstone.voters.api.APIClient
import ph.apper.android.capstone.voters.model.CandidateInfo
import ph.apper.android.capstone.voters.model.GetUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CandidateActivity : AppCompatActivity(){
    companion object{
        var candidateList: List<CandidateInfo> = ArrayList()
        var candidatePosition: String = ""
        var userProvince: String = ""
        var userMunicipality: String = ""
        var isVerified: Boolean = false

        fun clearArray(){
            candidateList = emptyList()
        }

        fun populateList(items: List<CandidateInfo>?){
            candidateList = items!!
        }

        var selectedCandidate: CandidateInfo = CandidateInfo()
    }

    private val USER_ID = "user_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate)
        val sharedPref = this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val id = sharedPref.getString(USER_ID, "ID")
        Log.d("SHARED USER ID", "User ID: $id")

        if(id != null){
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
                   isVerified = false
                } else {
                    val response: GetUserResponse = response.body()!!
                    userProvince = response.province
                    userMunicipality = response.city
                    isVerified = true
                }
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                t.message?.let { Log.d("GET USER API FAILURE", it) }
            }

        })
    }
}