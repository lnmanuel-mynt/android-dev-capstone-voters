package ph.apper.android.capstone.voters.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_local_positions.*
import kotlinx.android.synthetic.main.fragment_local_positions.view.*
import ph.apper.android.capstone.voters.CandidateActivity
import ph.apper.android.capstone.voters.R
import ph.apper.android.capstone.voters.adapters.CandidatePositionsAdapter
import ph.apper.android.capstone.voters.api.CandidateAPIClient
import ph.apper.android.capstone.voters.model.GetCandidateListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LocalPositionsFragment : Fragment(), View.OnClickListener, CandidatePositionsAdapter.OnItemClickListener{
    lateinit var navController: NavController

    companion object {
        private lateinit var positionsAdapter: CandidatePositionsAdapter
    }

    private var localPositionsArray = arrayListOf(
        "GOVERNOR",
        "VICE-GOVERNOR",
        "SANGGUNIANG PANLALAWIGAN",
        "MAYOR",
        "VICE-MAYOR",
        "COUNCILOR",
        "CONGRESSMAN"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local_positions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        btn_natl_positions.setOnClickListener(this)

        view.rv_position_list.layoutManager = LinearLayoutManager(this.requireActivity().applicationContext)
        positionsAdapter = CandidatePositionsAdapter(localPositionsArray, this.requireActivity().applicationContext, this)
        view.rv_position_list.adapter = positionsAdapter
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            btn_natl_positions.id -> {
                navController.navigate(R.id.action_localPositionsFragment_to_nationalPositionsFragment)
            }
        }
    }

    override fun onItemClick(position: Int) {
        getLocalCandidatesList(localPositionsArray[position])
    }

    private fun getLocalCandidatesList(position:String){
        val call: Call<GetCandidateListResponse> = CandidateAPIClient.get.getLocalCandidates(position)
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
                CandidateActivity.candidatePosition = position
                CandidateActivity.populateList(response.body()?.candidateList)
                navController.navigate(R.id.action_localPositionsFragment_to_candidatesFragment)
            }


        })
    }
}