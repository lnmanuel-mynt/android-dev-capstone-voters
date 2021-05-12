package ph.apper.android.capstone.voters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_national_positions.*
import kotlinx.android.synthetic.main.fragment_national_positions.view.*
import ph.apper.android.capstone.voters.adapters.CandidatePositionsAdapter

class NationalPositionsFragment : Fragment(), View.OnClickListener{
    lateinit var navController: NavController

    companion object{
        private lateinit var positionsAdapter: CandidatePositionsAdapter
    }

    private var nationalPositionsArray =  arrayListOf(
        "PRESIDENT",
        "VICE-PRESIDENT",
        "SENATOR",
        "PARTYLIST",
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_national_positions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        btn_local_positions.setOnClickListener(this)

        view.rv_position_list.layoutManager = LinearLayoutManager(this.requireActivity().applicationContext)
        positionsAdapter = CandidatePositionsAdapter(nationalPositionsArray, this.requireActivity().applicationContext)
        view.rv_position_list.adapter =positionsAdapter
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            btn_local_positions.id -> {
                navController.navigate(R.id.action_nationalPositionsFragment_to_localPositionsFragment)
            }
        }
    }

}