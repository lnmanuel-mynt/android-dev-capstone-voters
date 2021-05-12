package ph.apper.android.capstone.voters

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
        LocalPositionsFragment.positionsAdapter = CandidatePositionsAdapter(localPositionsArray, this.requireActivity().applicationContext, this)
        view.rv_position_list.adapter = LocalPositionsFragment.positionsAdapter
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