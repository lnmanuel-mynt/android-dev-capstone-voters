package ph.apper.android.capstone.voters.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_local_candidates_by_position.view.*
import ph.apper.android.capstone.voters.CandidateActivity
import ph.apper.android.capstone.voters.R
import ph.apper.android.capstone.voters.adapters.CandidateListByPositionAdapter

class LocalCandidatesByPositionFragment: Fragment(), CandidateListByPositionAdapter.OnItemClickListener {

    companion object {
        private lateinit var listAdapter: CandidateListByPositionAdapter
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_local_candidates_by_position, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.rv_local.layoutManager = LinearLayoutManager(this.requireActivity().applicationContext)
        listAdapter = CandidateListByPositionAdapter(CandidateActivity.candidateList, this.requireActivity().applicationContext, this)
        view.rv_local.adapter = listAdapter
    }

    override fun onItemClick(position: Int) {
    }
}