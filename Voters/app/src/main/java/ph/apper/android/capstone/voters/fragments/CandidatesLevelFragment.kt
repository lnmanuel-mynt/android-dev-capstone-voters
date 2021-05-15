package ph.apper.android.capstone.voters.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_candidates_level.*
import ph.apper.android.capstone.voters.CandidateActivity
import ph.apper.android.capstone.voters.HomeActivity
import ph.apper.android.capstone.voters.R


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

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nextActivityIntent = Intent(requireContext(), HomeActivity::class.java)
                activity?.finish()
                startActivity(nextActivityIntent)
                activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            btn_local_candidates.id -> {
                if(CandidateActivity.isVerified) {
                    navController.navigate(R.id.action_candidatesLevelFragment_to_localPositionsFragment)
                }else{
                    Toast.makeText(this.requireContext(), "Register first to access local candidates", Toast.LENGTH_SHORT).show()
                }
            }
            btn_natl_candidates.id -> {
                navController.navigate(R.id.action_candidatesLevelFragment_to_nationalPositionsFragment)
            }
        }
    }
}