package com.faizan.lab49.api

import com.faizan.lab49.utils.Constants
import com.faizan.lab49.vo.Tile
import com.faizan.lab49.vo.TilePayloadResponse
import com.faizan.lab49.vo.Tiles
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET(Constants.tiles)
    suspend fun getTiles(): Response<ArrayList<Tiles>>

    @POST(Constants.tiles)
    suspend fun verifyTile(@Body tiles: Tile): Response<TilePayloadResponse>


}