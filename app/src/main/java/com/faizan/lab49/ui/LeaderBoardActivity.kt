package com.faizan.lab49.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faizan.lab49.R
import com.faizan.lab49.adapter.LeaderBoardAdapter
import com.faizan.lab49.databinding.ActivityLeaderBoardBinding
import com.faizan.lab49.vo.Score

class LeaderBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeaderBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLeaderBoardBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val list = listOf<Score>(
            Score(score = "30 Seconds", dateAndTime = "6th Jan 2020"),
            Score(score = "32 Seconds", dateAndTime = "5th Jan 2020"),
            Score(score = "39 Seconds", dateAndTime = "4th Jan 2020"),
            Score(score = "40 Seconds", dateAndTime = "3th Jan 2020"),
        )

        val leaderBoardAdapter = LeaderBoardAdapter(list = list)

        binding.recyclerView.adapter = leaderBoardAdapter
    }
}