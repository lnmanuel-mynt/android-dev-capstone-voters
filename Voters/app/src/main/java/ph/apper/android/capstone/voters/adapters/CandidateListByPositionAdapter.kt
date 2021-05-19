package ph.apper.android.capstone.voters.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_local_candidates_by_position.view.*
import ph.apper.android.capstone.voters.R
import ph.apper.android.capstone.voters.model.CandidateInfo

class CandidateListByPositionAdapter (
        val candidateList: List<CandidateInfo>,
        val context: Context,
        val listener: OnItemClickListener)
    :RecyclerView.Adapter<CandidateListByPositionAdapter.CandidateViewHolder>() {

    inner class CandidateViewHolder(view: View):
            RecyclerView.ViewHolder(view), View.OnClickListener
    {
        val tv_name = view.tv_name
        val tv_position = view.tv_position

        init{
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        return CandidateViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.item_local_candidates_by_position, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        holder?.tv_name.text = candidateList[position].name
        holder?.tv_position.text = candidateList[position].position
    }

    override fun getItemCount(): Int {
        return candidateList.size
    }
}