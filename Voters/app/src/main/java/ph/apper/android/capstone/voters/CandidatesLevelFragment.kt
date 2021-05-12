package ph.apper.android.capstone.voters

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_candidates_level.*


class CandidatesLevelFragment : Fragment(), View.OnClickListener {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_candidates_level, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btn_local_candidates.setOnClickListener(this)
        btn_natl_candidates.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            btn_local_candidates.id -> {
                navController.navigate(R.id.action_candidatesLevelFragment_to_localPositionsFragment)
            }
            btn_natl_candidates.id -> {
                navController.navigate(R.id.action_candidatesLevelFragment_to_nationalPositionsFragment)

            }
        }
    }
}