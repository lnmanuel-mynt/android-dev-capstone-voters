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
import ph.apper.android.capstone.voters.R
import ph.apper.android.capstone.voters.adapters.CandidatePositionsAdapter


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
        Log.d("POSITION", "${localPositionsArray[position]}")
    }
}