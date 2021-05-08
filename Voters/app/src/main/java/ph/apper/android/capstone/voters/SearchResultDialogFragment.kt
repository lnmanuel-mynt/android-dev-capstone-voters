package ph.apper.android.capstone.voters

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_search_result_dialog.view.*


class SearchResultDialogFragment: DialogFragment() {

    companion object {
        const val TAG = "SearchResultDialog"

        private const val RESULT = "RESULT"

        fun newInstance(result: String): SearchResultDialogFragment {
            val args = Bundle()
            args.putString(RESULT, result)
            val fragment = SearchResultDialogFragment()
            fragment.arguments = args
            return fragment
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

        view.findViewById<TextView>(R.id.tv_close).setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        setDialogSize(90,70)
    }

    private fun setupView(view: View) {
        view.tv_result.text = arguments?.getString(RESULT)
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
}