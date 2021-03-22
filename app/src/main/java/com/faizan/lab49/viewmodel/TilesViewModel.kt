package com.faizan.lab49.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.faizan.lab49.repository.TilesRepository
import com.faizan.lab49.vo.Resource
import com.faizan.lab49.vo.Tile
import com.faizan.lab49.vo.TilePayloadResponse
import kotlinx.coroutines.Dispatchers

class TilesViewModel @ViewModelInject constructor(
    private val repository: TilesRepository,
) :
    ViewModel() {


    //Get Tiles from repository over network
    fun getTiles() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
//        var data: ArrayList<Tiles>? = null

        try {
            val response = repository.getTiles()
//            data = response.body();

            when {
                response.code() == 200 -> {
                    emit(Resource.success(response.body()))
                }
                else -> {
                    emit(
                        Resource.error(
                            message = response.message() ?: "Error"
                        )
                    )
                }
            }

        } catch (exception: Exception) {
            emit(
                Resource.error(
                    message = exception.message ?: "Something went wrong"
                )
            )
        }
    }

    //push payload tiles over network
    fun verifyTile(tile: Tile) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        var payloadResponse: TilePayloadResponse? = null
        try {
            val response = repository.verifyTile(tile)
            payloadResponse = response.body()

            when {
                response.code() == 200 -> {
                    emit(Resource.success(response.body()))
                }
                else -> {
                    emit(
                        Resource.error(
                            message = response.message() ?: "Incorrect"
                        )
                    )
                }
            }

        } catch (exception: Exception) {
            emit(
                Resource.error(
                    message = exception.message ?: "Something went wrong"
                )
            )
        }
    }

}