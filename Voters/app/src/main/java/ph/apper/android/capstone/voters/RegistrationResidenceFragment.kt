package ph.apper.android.capstone.voters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class RegistrationResidenceFragment: Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration_residence, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_submit).setOnClickListener {
            findNavController().navigate(R.id.action_RegistrationResidenceFragment_to_RegistrationProcessingFragment)
        }
        view.findViewById<TextView>(R.id.tv_back).setOnClickListener {
            findNavController().navigate(R.id.action_RegistrationResidenceFragment_to_RegistrationPersonalFragment)
        }

    }
}