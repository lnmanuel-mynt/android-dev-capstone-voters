package ph.apper.android.capstone.voters.fragments

import android.content.res.Resources
import android.graphics.Rect
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_search_result_dialog.*
import kotlinx.android.synthetic.main.fragment_search_result_dialog.view.*
import ph.apper.android.capstone.voters.R
import java.io.IOException
import java.util.*

class SearchResultDialogFragment: DialogFragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var locationCoordinates: LatLng

    companion object {
        const val TAG = "SearchResultDialog"

        private const val PRECINCT_NUMBER = "PRECINCT NUMBER"
        private const val BARANGAY = "BARANGAY"
        private const val CITY = "CITY"
        private const val PROVINCE = "PROVINCE"
        private const val POLLING_PLACE = "POLLING PLACE"

        fun newInstance(dialogParams: Map<String, String>): SearchResultDialogFragment {
            val args = Bundle()
            args.putString(PRECINCT_NUMBER, dialogParams["precinctNumber"].toString())
            args.putString(BARANGAY, dialogParams["barangay"].toString())
            args.putString(CITY, dialogParams["city"].toString())
            args.putString(PROVINCE, dialogParams["province"].toString())
            args.putString(POLLING_PLACE, dialogParams["pollingPlace"].toString())
            val fragment = SearchResultDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let{
            googleMap = it

            val pollingPlace = LatLng(locationCoordinates.latitude, locationCoordinates.longitude)
            map.addMarker(MarkerOptions().position(pollingPlace).title("Your polling place is here."))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(pollingPlace, 15f), 3000, null)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_result_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)

        mv_google_map.onCreate(savedInstanceState)
        mv_google_map.onResume()

        mv_google_map.getMapAsync(this)

        val coordinates = getCoordinates(tv_polling_place.text.toString())
        if (coordinates != null)
            locationCoordinates = coordinates

        view.findViewById<TextView>(R.id.tv_close).setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        setDialogSize(90,70)
    }

    private fun setupView(view: View) {
        view.tv_precinct_number.text = arguments?.getString(PRECINCT_NUMBER)
        view.tv_barangay.text = arguments?.getString(BARANGAY)
        view.tv_city.text = arguments?.getString(CITY)
        view.tv_province.text = arguments?.getString(PROVINCE)
        view.tv_polling_place.text = arguments?.getString(POLLING_PLACE)
    }

    private fun setDialogSize(widthPercentage: Int, heightPercentage: Int) {
        val percentWidth = widthPercentage.toFloat() / 100
        val percentHeight = heightPercentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run {
            Rect(0, 0, widthPixels, heightPixels)
        }
        val width = rect.width() * percentWidth
        val height = rect.height() * percentHeight
        dialog?.window?.setLayout(width.toInt(), height.toInt())
    }

    private fun getCoordinates(location: String): LatLng? {
        val geocoder = Geocoder(this.requireContext(), Locale.getDefault())
        var p1: LatLng? = null
        try {
            val addressList: List<Address> = geocoder.getFromLocationName(location, 1)
            if ((true) and (addressList.isNotEmpty())) {
                val address: Address = addressList[0]
                p1 = LatLng(address.latitude, address.longitude)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return p1
    }
}