package com.faizan.lab49.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faizan.lab49.databinding.ItemLeaderboardBinding
import com.faizan.lab49.vo.Score
import kotlinx.android.synthetic.main.item_leaderboard.view.*

class LeaderBoardAdapter(val list: List<Score>) :
    RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder>() {

    private lateinit var binding: ItemLeaderboardBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeaderBoardAdapter.LeaderBoardViewHolder {
        binding = ItemLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaderBoardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaderBoardAdapter.LeaderBoardViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class LeaderBoardViewHolder(private val itemView: ItemLeaderboardBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        fun bind(score: Score) {
            itemView.tv_date.text = score.dateAndTime
            itemView.tv_score.text = score.score


        }
    }
}



