package ph.apper.android.capstone.voters.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_candidates_level.*
import ph.apper.android.capstone.voters.CandidateActivity
import ph.apper.android.capstone.voters.HomeActivity
import ph.apper.android.capstone.voters.R
import ph.apper.android.capstone.voters.api.CandidateAPIClient
import ph.apper.android.capstone.voters.model.CandidateInfo
import ph.apper.android.capstone.voters.model.GetCandidateListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CandidatesLevelFragment : Fragment(), View.OnClickListener {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_candidates_level, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btn_local_candidates.setOnClickListener(this)
        btn_natl_candidates.setOnClickListener(this)
        btn_parties.setOnClickListener(this)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nextActivityIntent = Intent(requireContext(), HomeActivity::class.java)
                activity?.finish()
                startActivity(nextActivityIntent)
                activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            btn_local_candidates.id -> {
                if(CandidateActivity.isVerified) {
                    val province = CandidateActivity.userProvince
                    var municipality = CandidateActivity.userMunicipality

                    if(CandidateActivity.userMunicipality == "")
                        municipality = "null"

                    getLocalCandidatesListByPosition(province, municipality)
                    // navController.navigate(R.id.action_candidatesLevelFragment_to_localPositionsFragment)
                }else{
                    Toast.makeText(this.requireContext(), "Register first to access local candidates", Toast.LENGTH_SHORT).show()
                }
            }
            btn_natl_candidates.id -> {
                navController.navigate(R.id.action_candidatesLevelFragment_to_nationalPositionsFragment)
            }
            btn_parties.id -> {
                getCandidatesList()
            }
        }
    }

    private fun getCandidatesList() {
        val call: Call<GetCandidateListResponse> = CandidateAPIClient.get.getCandidates()
        call.enqueue(object : Callback<GetCandidateListResponse> {
            override fun onFailure(call: Call<GetCandidateListResponse>, t: Throwable) {
                Log.d("GET REQUEST: ", "FAILED + ${t.message}")
            }

            override fun onResponse(
              call: Call<GetCandidateListResponse>,
              response: Response<GetCandidateListResponse>
             ) {
                if(response.code() == 404){
                    Log.d("GET REQUEST: ", "NO DATA")
                    return
                }
                val sortedList = response.body()?.candidateList
                sortedList?.removeAll { it.position == "PartyList" }
                val finalList = sortedList?.sortedWith(compareBy({it.party}, {it.positionNumber.toInt()}))
                CandidateActivity.populateList(finalList)
                navController.navigate(R.id.action_candidatesLevelFragment_to_partiesFragment)
            }
        })
    }
   private fun getLocalCandidatesListByPosition(province: String, municipality: String) {
        val call: Call<GetCandidateListResponse> =
                CandidateAPIClient.get.getLocalCandidatesByPosition(province, municipality)
        call.enqueue(object : Callback<GetCandidateListResponse> {
            override fun onFailure(call: Call<GetCandidateListResponse>, t: Throwable) {
                Log.d("GET REQUEST: ", "FAILED + ${t.message}")
            }

            override fun onResponse(
                    call: Call<GetCandidateListResponse>,
                    response: Response<GetCandidateListResponse>
            ) {
                if(response.code() == 404){
                    Log.d("GET REQUEST: ", "NO DATA")
                    return
                }
                CandidateActivity.populateList(response.body()?.candidateList)
                navController.navigate(R.id.action_level_to_new_local_fragment)
            }
        })
    }
}