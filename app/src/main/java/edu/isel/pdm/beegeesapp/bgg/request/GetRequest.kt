package edu.isel.pdm.beegeesapp.bgg.request

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonRequest
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesMapper

class GetRequest(
    url: String,
    success: Response.Listener<GamesMapper>,
    error: Response.ErrorListener
) : JsonRequest<GamesMapper>(Request.Method.GET, url, "", success, error) {

    override fun parseNetworkResponse(response: NetworkResponse): Response<GamesMapper> {
        val mapper = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        val games: GamesMapper = mapper.readValue(String(response.data), GamesMapper::class.java)
        return Response.success(games, null)
    }
}