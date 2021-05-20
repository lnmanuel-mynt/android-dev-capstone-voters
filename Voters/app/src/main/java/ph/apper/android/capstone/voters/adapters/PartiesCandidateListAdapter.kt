package ph.apper.android.capstone.voters.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_candidate_card_alt.view.*
import ph.apper.android.capstone.voters.R
import ph.apper.android.capstone.voters.fragments.PartiesFragment
import ph.apper.android.capstone.voters.model.CandidateInfo
import java.util.*

class PartiesCandidateListAdapter(
    val candidateList: List<CandidateInfo>,
    val context: Context,
    val listener: OnItemClickListener
)
    :RecyclerView.Adapter<PartiesCandidateListAdapter.CandidateViewHolder>(){

    var current: String = ""
    var prev: String = ""
    var color: Int = 0

    inner class CandidateViewHolder(view: View):
        RecyclerView.ViewHolder(view), View.OnClickListener
        {
            val img_candidate = view.img_candidate
            val tv_candidate_name = view.tv_candidate_name
            val tv_candidate_party = view.tv_candidate_party
            val tv_candidate_position = view.tv_candidate_position
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
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        return CandidateViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_candidate_card_alt, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        holder.tv_candidate_name.text = candidateList.get(position).name
        holder.tv_candidate_party.text = candidateList.get(position).party
        holder.tv_candidate_position.text = candidateList.get(position).position
        candidateList.get(position).image?.let{
            Picasso
                .with(this.context)
                .load(it)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .into(holder.img_candidate)
        }
        current = candidateList.get(position).party
        if (current != prev) {
            val rnd = Random()
            color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            holder.itemView.setBackgroundColor(color)
        } else {
            holder.itemView.setBackgroundColor(color)
        }
        prev = current
    }

    override fun getItemCount(): Int {
        return candidateList.size
    }

}