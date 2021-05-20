package ph.apper.android.capstone.voters.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_candidates.view.*
import ph.apper.android.capstone.voters.CandidateActivity
import ph.apper.android.capstone.voters.R
import ph.apper.android.capstone.voters.adapters.CandidateListAdapter
import ph.apper.android.capstone.voters.api.CandidateAPIClient
import ph.apper.android.capstone.voters.model.GetCandidateListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CandidatesFragment : Fragment(), CandidateListAdapter.OnItemClickListener {
    lateinit var navController: NavController
    companion object {
        lateinit var candidatesAdapter: CandidateListAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_candidates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        view.tv_position.text = CandidateActivity.candidatePosition
        view.rv_candidate_list.layoutManager = GridLayoutManager(this.requireActivity().applicationContext, 2)
        candidatesAdapter = CandidateListAdapter(CandidateActivity.candidateList, this.requireActivity().applicationContext, this)
        view.rv_candidate_list.adapter = candidatesAdapter
    }

    override fun onItemClick(position: Int) {
        CandidateActivity.selectedCandidate = CandidateActivity.candidateList[position]
        if( CandidateActivity.candidateList[position].is_national == "0") {
            getRunningMatesList(
                CandidateActivity.candidateList[position].party,
                CandidateActivity.candidateList[position].province,
                CandidateActivity.candidateList[position].municipality
            )
        }else{
            navController.navigate(R.id.action_candidatesFragment_to_candidateProfileFragment)
        }

    }

    private fun getRunningMatesList(party:String, province:String, municipality:String){
        val call: Call<GetCandidateListResponse> =
            CandidateAPIClient.get.getRunningMates(
                party,
                province,
                municipality
            )
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
                    navController.navigate(R.id.action_candidatesFragment_to_candidateProfileFragment)
                    return
                }
                CandidateActivity.populateRunningMatesList(response.body()?.candidateList)
                navController.navigate(R.id.action_candidatesFragment_to_candidateProfileFragment)
            }
        })
    }
}