package ph.apper.android.capstone.voters.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ph.apper.android.capstone.voters.HomeActivity
import ph.apper.android.capstone.voters.R
import ph.apper.android.capstone.voters.RegistrationActivity

class RegistrationProcessingFragment: Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration_processing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tv_close).setOnClickListener {
            val nextActivityIntent = Intent(requireContext(), HomeActivity::class.java)
            nextActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            activity?.finish()
            startActivity(nextActivityIntent)
        }
    }
}