package com.faizan.lab49.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.faizan.lab49.LAB49APP
import com.faizan.lab49.R
import com.faizan.lab49.databinding.ActivityHomeBinding
import com.faizan.lab49.utils.*
import com.faizan.lab49.viewmodel.TilesViewModel
import com.faizan.lab49.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val tilesViewModel: TilesViewModel by viewModels()

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.content.btnGo.setOnClickListener {
            startActivity(Intent(this, LeaderBoardActivity::class.java))
//            if (NetworkUtils.isNetworkAvailable(this)) {
//
//                tilesViewModel.getTiles().observe(this, Observer {
//                    when (it.status) {
//                        Resource.Status.LOADING -> {
//                            setLoading(true)
//
//                        }
//                        Resource.Status.SUCCESS -> {
//                            setLoading(false)
//                            val intent = Intent(this, ScreenActivity::class.java)
//                            intent.putParcelableArrayListExtra(Constants.TILES_DATA, it.content)
//                            startActivity(intent)
//
//                        }
//
//                        Resource.Status.ERROR -> {
//                            setLoading(false)
//                            ToastUtil.show(this, it.message)
//                        }
//
//
//                    }
//
//                })
//
//
//            } else {
//                ToastUtil.showInternetError(this)
//            }
        }


    }


    private fun setLoading(loading: Boolean) {
        if (loading) {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    HomeActivity@ this,
                    R.color.colorBackgroundDarkGrey
                )
            )
            binding.root.alpha = 0.7f
            binding.content.progressCircular.show()
        } else {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    HomeActivity@ this,
                    android.R.color.transparent
                )
            )
            binding.root.alpha = 1f
            binding.content.progressCircular.hide()
        }

    }
}