package edu.isel.pdm.beegeesapp.bgg.Request

import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonRequest
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesMapper

class GetRequest(
    url: String,
    success: Response.Listener<GamesMapper>,
    error: Response.ErrorListener
) : JsonRequest<GamesMapper>(Request.Method.GET, url, "", success, error) {

    override fun parseNetworkResponse(response: NetworkResponse): Response<GamesMapper> {
        Log.v("BeeGeesApp", "parseNetworkResponse on thread ${Thread.currentThread().name}")
        val mapper = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        val games: GamesMapper = mapper.readValue(response.data)
        return Response.success(games, null)
    }
}