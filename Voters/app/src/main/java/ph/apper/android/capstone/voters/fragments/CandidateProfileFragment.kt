package ph.apper.android.capstone.voters.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_candidate_profile.*
import kotlinx.android.synthetic.main.fragment_candidate_profile.view.*
import kotlinx.android.synthetic.main.fragment_candidates.view.*
import ph.apper.android.capstone.voters.CandidateActivity
import ph.apper.android.capstone.voters.R
import ph.apper.android.capstone.voters.adapters.CandidateListAdapter
import ph.apper.android.capstone.voters.model.CandidateInfo

class CandidateProfileFragment : Fragment(), CandidateListAdapter.OnItemClickListener {
    companion object {
        lateinit var runningMatesAdapter: CandidateListAdapter
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
        tv_candidate_name.text = CandidateActivity.selectedCandidate.name
        tv_position.text = CandidateActivity.selectedCandidate.position
        tv_party.text = CandidateActivity.selectedCandidate.party
        tv_province.text = CandidateActivity.selectedCandidate.province
        tv_municipality.text = CandidateActivity.selectedCandidate.municipality
        tv_district.text = CandidateActivity.selectedCandidate.district

        CandidateActivity.selectedCandidate.image?.let {
            Picasso
                .with(context)
                .load(it)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .into(img_candidate)
        }

        view.rv_running_mates.layoutManager = GridLayoutManager(this.requireActivity().applicationContext, 2)
        runningMatesAdapter = CandidateListAdapter(CandidateActivity.runningMatesList, this.requireActivity().applicationContext, this)
        view.rv_running_mates.adapter = runningMatesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        CandidateActivity.selectedCandidate = CandidateInfo()
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

}