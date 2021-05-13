package ph.apper.android.capstone.voters.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_candidate_profile.*
import ph.apper.android.capstone.voters.CandidateActivity
import ph.apper.android.capstone.voters.R
import ph.apper.android.capstone.voters.model.CandidateInfo

class CandidateProfileFragment : Fragment() {

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        CandidateActivity.selectedCandidate = CandidateInfo()
    }

}