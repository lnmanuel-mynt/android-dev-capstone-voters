package ph.apper.android.capstone.voters.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_positions_row.view.*
import ph.apper.android.capstone.voters.R

class CandidatePositionsAdapter(
    val positionList: ArrayList<String>,
    val context: Context,
    val listener: OnItemClickListener):
RecyclerView.Adapter<CandidatePositionsAdapter.PositionViewHolder>(){

    inner class PositionViewHolder(view: View):
        RecyclerView.ViewHolder(view), View.OnClickListener
    {
        val tv_position = view.tv_position

        init{
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?):Boolean {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
            return true
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        return PositionViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_positions_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return positionList.size
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        holder?.tv_position.text = positionList.get(position)
    }
}
