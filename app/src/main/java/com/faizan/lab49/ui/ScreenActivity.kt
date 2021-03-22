package com.faizan.lab49.ui

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.faizan.lab49.R
import com.faizan.lab49.databinding.ActivityScreenBinding
import com.faizan.lab49.utils.*
import com.faizan.lab49.viewmodel.TilesViewModel
import com.faizan.lab49.vo.Resource
import com.faizan.lab49.vo.Tile
import com.faizan.lab49.vo.Tiles
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ScreenActivity : AppCompatActivity() {
    private val tilesViewModel: TilesViewModel by viewModels()
    private var data: ArrayList<Tiles>? = null
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var binding: ActivityScreenBinding
    private val TAG = ScreenActivity::class.java.simpleName
    private var currentTiles: Int = 0
    private var gameScore = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = intent.extras?.getParcelableArrayList(Constants.TILES_DATA)


        //There will be a fixed set of four items to locate so doing like that otherwise a recyclerview can be used
        binding.includeTiles1.tvTilesName.text = data?.get(0)?.name
        binding.includeTiles2.tvTilesName.text = data?.get(1)?.name
        binding.includeTiles3.tvTilesName.text = data?.get(2)?.name
        binding.includeTiles4.tvTilesName.text = data?.get(3)?.name

        binding.includeTiles1.tilesContainer.setOnClickListener {
            currentTiles = 1;
            dispatchTakePictureIntent()


        }

        binding.includeTiles2.tilesContainer.setOnClickListener {
            currentTiles = 2
            dispatchTakePictureIntent()


        }
        binding.includeTiles3.tilesContainer.setOnClickListener {
            currentTiles = 3
            dispatchTakePictureIntent()


        }
        binding.includeTiles4.tilesContainer.setOnClickListener {
            currentTiles = 4
            dispatchTakePictureIntent()


        }


        startCountDown()

//        binding.tvTime.setOnClickListener {
//            startActivity(Intent(this, LeaderBoardActivity::class.java))
//        }
    }

    private fun startCountDown() {
        object : CountDownTimer(120000, 1000) {
            override fun onFinish() {
                showGameOverDialog()
            }

            override fun onTick(millisUntilFinished: Long) {
                binding.tvTime.text = String.format(
                    "%02d : %02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(
                                    millisUntilFinished
                                )
                            )
                )
            }
        }.start()
    }

    private fun showGameOverDialog() {
        val msg = if (gameScore == 4) {
            getString(R.string.dialog_game_win)
        } else {
            getString(R.string.dialog_game_loss)
        }
        val positiveButton = if (gameScore == 4) {
            getString(R.string.play_again)
        } else {
            getString(R.string.restart)
        }
        val builder: AlertDialog.Builder =
            AlertDialog.Builder(this)

        builder.setMessage(msg)
            .setTitle(R.string.dialog_title)
        builder.apply {
            setPositiveButton(positiveButton,
                DialogInterface.OnClickListener { dialog, id ->
                    resetGame()
                })
            setNegativeButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, id ->
                    finish()
                })
        }
        builder.setCancelable(false)

        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun resetGame() {
        currentTiles = 0
        binding.includeTiles1.tilesContainer.setBackgroundResource(R.mipmap.ic_tile_default)
        binding.includeTiles1.ivTiles.setImageBitmap(null)
        binding.includeTiles1.tvRetry.hide()
        binding.includeTiles2.tilesContainer.setBackgroundResource(R.mipmap.ic_tile_default)
        binding.includeTiles2.ivTiles.setImageBitmap(null)
        binding.includeTiles2.tvRetry.hide()
        binding.includeTiles3.tilesContainer.setBackgroundResource(R.mipmap.ic_tile_default)
        binding.includeTiles3.ivTiles.setImageBitmap(null)
        binding.includeTiles3.tvRetry.hide()
        binding.includeTiles4.tilesContainer.setBackgroundResource(R.mipmap.ic_tile_default)
        binding.includeTiles4.ivTiles.setImageBitmap(null)
        binding.includeTiles4.tvRetry.hide()
        startCountDown()
    }

    //Todo using thumbnail for the sake of small tiles
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Log.d(TAG, e.message.toString())
            ToastUtil.show(this, getString(R.string.error_camera))
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            handleTileImage(bitmap = imageBitmap)

        } else {
            ToastUtil.show(this, getString(R.string.cancelled))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun convert(bitmap: Bitmap): String? {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }

    private fun handleTileImage(bitmap: Bitmap) {
        if (currentTiles in 1..4) {
            var label: String = ""
            when (currentTiles) {
                1 -> {
                    binding.includeTiles1.ivTiles.setImageBitmap(bitmap)
                    label = binding.includeTiles1.tvTilesName.text.toString()
                }
                2 -> {
                    binding.includeTiles2.ivTiles.setImageBitmap(bitmap)
                    label = binding.includeTiles2.tvTilesName.text.toString()
                }
                3 -> {
                    binding.includeTiles3.ivTiles.setImageBitmap(bitmap)
                    label = binding.includeTiles3.tvTilesName.text.toString()
                }
                4 -> {
                    binding.includeTiles4.ivTiles.setImageBitmap(bitmap)
                    label = binding.includeTiles4.tvTilesName.text.toString()
                }
            }
            val base64 = convert(bitmap)
            Log.d(TAG, "" + base64)
            val tile: Tile = Tile(ImageLabel = label, image = base64)
            pushPayload(tile)
        }
    }


    private fun handleLoading(show: Boolean) {
        when (currentTiles) {
            1 -> {
                if (show) {
                    binding.includeTiles1.progressCircular.show()
                    binding.includeTiles1.srcOverlay.show()
                } else {
                    binding.includeTiles1.progressCircular.hide()
                    binding.includeTiles1.srcOverlay.hide()
                }
            }
            2 -> {
                if (show) {
                    binding.includeTiles2.progressCircular.show()
                    binding.includeTiles2.srcOverlay.show()
                } else {
                    binding.includeTiles2.progressCircular.hide()
                    binding.includeTiles2.srcOverlay.hide()
                }
            }
            3 -> {
                if (show) {
                    binding.includeTiles3.progressCircular.show()
                    binding.includeTiles3.srcOverlay.show()
                } else {
                    binding.includeTiles3.progressCircular.hide()
                    binding.includeTiles3.srcOverlay.hide()
                }
            }
            4 -> {
                if (show) {
                    binding.includeTiles4.progressCircular.show()
                    binding.includeTiles4.srcOverlay.show()
                } else {
                    binding.includeTiles4.progressCircular.hide()
                    binding.includeTiles4.srcOverlay.hide()
                }
            }

        }
    }

    private fun handleStatus(status: Boolean) {
        when (currentTiles) {
            1 -> {
                if (status) {
                    binding.includeTiles1.tilesContainer.setBackgroundResource(R.mipmap.ic_tile_success)
                    binding.includeTiles1.tvRetry.hide()
                    if (gameScore < 4)
                        gameScore++
                } else {
                    binding.includeTiles1.tilesContainer.setBackgroundResource(R.mipmap.ic_tile_incorrect)
                    binding.includeTiles1.tvRetry.show()
                    if (gameScore > 0)
                        gameScore--
                }
            }
            2 -> {
                if (status) {
                    binding.includeTiles2.tilesContainer.setBackgroundResource(R.mipmap.ic_tile_success)
                    binding.includeTiles2.tvRetry.hide()
                    if (gameScore < 4)
                        gameScore++
                } else {
                    binding.includeTiles2.tilesContainer.setBackgroundResource(R.mipmap.ic_tile_incorrect)
                    binding.includeTiles2.tvRetry.show()
                    if (gameScore > 0)
                        gameScore--
                }
            }
            3 -> {
                if (status) {

                    binding.includeTiles3.tilesContainer.setBackgroundResource(R.mipmap.ic_tile_success)
                    binding.includeTiles3.tvRetry.hide()
                    if (gameScore < 4)
                        gameScore++
                } else {
                    binding.includeTiles3.tilesContainer.setBackgroundResource(R.mipmap.ic_tile_incorrect)
                    binding.includeTiles3.tvRetry.show()
                    if (gameScore > 0)
                        gameScore--
                }
            }
            4 -> {
                if (status) {
                    binding.includeTiles4.tilesContainer.setBackgroundResource(R.mipmap.ic_tile_success)
                    binding.includeTiles4.tvRetry.hide()
                    if (gameScore < 4)
                        gameScore++
                } else {
                    binding.includeTiles4.tilesContainer.setBackgroundResource(R.mipmap.ic_tile_incorrect)
                    binding.includeTiles4.tvRetry.show()
                    if (gameScore > 0)
                        gameScore--
                }
            }


        }
    }


    private fun pushPayload(tile: Tile) {
        if (NetworkUtils.isNetworkAvailable(this)) {
            tilesViewModel.verifyTile(tile)
                .observe(ScreenActivity@ this, {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            handleLoading(true)

                        }
                        Resource.Status.SUCCESS -> {
                            handleLoading(false)
                            it.content?.matched?.let { it1
                                ->
                                handleStatus(it1)
                            }
                        }

                        Resource.Status.ERROR -> {
                            handleLoading(false)
                            it.content?.matched?.let { it1
                                ->
                                handleStatus(it1)
                            }
                            ToastUtil.show(this, it.message)
                        }


                    }

                })


        } else {
            ToastUtil.showInternetError(this)
        }

    }
}