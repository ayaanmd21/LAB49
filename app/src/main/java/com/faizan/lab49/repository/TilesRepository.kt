package com.faizan.lab49.repository

import com.faizan.lab49.api.ApiService
import com.faizan.lab49.vo.Tile
import javax.inject.Inject

class TilesRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getTiles() = apiService.getTiles()


    suspend fun verifyTile(tile: Tile) = apiService.verifyTile(tile)

}