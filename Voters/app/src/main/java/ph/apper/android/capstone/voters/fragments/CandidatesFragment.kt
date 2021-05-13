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
        navController.navigate(R.id.action_candidatesFragment_to_candidateProfileFragment)
    }
}