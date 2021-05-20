package ph.apper.android.capstone.voters.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_candidate_profile.*
import kotlinx.android.synthetic.main.fragment_candidate_profile.view.*
import kotlinx.android.synthetic.main.fragment_candidates.view.*
import ph.apper.android.capstone.voters.CandidateActivity
import ph.apper.android.capstone.voters.R
import ph.apper.android.capstone.voters.adapters.RunningMateListAdapter
import ph.apper.android.capstone.voters.api.CandidateAPIClient
import ph.apper.android.capstone.voters.model.CandidateInfo
import ph.apper.android.capstone.voters.model.GetCandidateListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CandidateProfileFragment : Fragment(), RunningMateListAdapter.OnItemClickListener {

    companion object {
        lateinit var runningMatesAdapter: RunningMateListAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_candidate_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadInfo(CandidateActivity.selectedCandidate)

        view.rv_running_mates.layoutManager = LinearLayoutManager(this.requireActivity().applicationContext,LinearLayoutManager.HORIZONTAL, false)
        runningMatesAdapter = RunningMateListAdapter(CandidateActivity.runningMatesList, this.requireActivity().applicationContext, this)
        view.rv_running_mates.adapter = runningMatesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        CandidateActivity.clearArray()
    }

    override fun onItemClick(position: Int) {
        CandidateActivity.selectedCandidate = CandidateActivity.runningMatesList[position]
        getRunningMatesList(
                CandidateActivity.runningMatesList[position].party,
                CandidateActivity.runningMatesList[position].province,
                CandidateActivity.runningMatesList[position].municipality
        )
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
                    return
                }
                CandidateActivity.populateRunningMatesList(response.body()?.candidateList)
                runningMatesAdapter.notifyDataSetChanged()
                loadInfo(CandidateActivity.selectedCandidate)
            }
        })
    }

    private fun loadInfo(selectedCandidate: CandidateInfo){
        tv_candidate_name.text = selectedCandidate.name
        tv_position.text = selectedCandidate.position
        tv_party.text = selectedCandidate.party
        tv_province.text = selectedCandidate.province
        tv_municipality.text = selectedCandidate.municipality
        tv_district.text = selectedCandidate.district

        selectedCandidate.image?.let {
            Picasso
                    .with(context)
                    .load(it)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .into(img_candidate)
        }
    }

}