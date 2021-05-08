package ph.apper.android.capstone.voters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_registration_personal.*
import ph.apper.android.capstone.voters.model.CivilStatus
import ph.apper.android.capstone.voters.model.Sex

class RegistrationPersonalFragment: Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration_personal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        var civilStatusArray = arrayOf(
                CivilStatus.SINGLE,
                CivilStatus.MARRIED,
                CivilStatus.DIVORCED,
                CivilStatus.WIDOWED
        )

        val arrayCivilStatusAdapter =
                ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, civilStatusArray)
        spinner_civil_status.adapter = arrayCivilStatusAdapter

        spinner_civil_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                // do nothing
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }

        var sexArray = arrayOf(
                Sex.MALE,
                Sex.FEMALE
        )

        val arraySexAdapter =
                ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, sexArray)
        spinner_sex.adapter = arraySexAdapter

        spinner_sex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                // do nothing
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }

        view.findViewById<Button>(R.id.btn_next).setOnClickListener {
            findNavController().navigate(R.id.action_RegistrationPersonalFragment_to_RegistrationResidenceFragment)
        }

        view.findViewById<TextView>(R.id.tv_back).setOnClickListener {
            findNavController().navigate(R.id.action_RegistrationPersonalFragment_to_FindMyPrecinctFragment)
        }
    }
}