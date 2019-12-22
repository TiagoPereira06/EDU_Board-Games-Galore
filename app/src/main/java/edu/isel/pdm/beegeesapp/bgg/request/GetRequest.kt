package edu.isel.pdm.beegeesapp.bgg.request

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonRequest
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import edu.isel.pdm.beegeesapp.bgg.database.CategoriesMapper
import edu.isel.pdm.beegeesapp.bgg.database.MechanicsMapper
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

class GetCategoriesRequest(
    url: String,
    success: Response.Listener<CategoriesMapper>,
    error: Response.ErrorListener
) : JsonRequest<CategoriesMapper>(Method.GET, url, "", success, error) {

    override fun parseNetworkResponse(response: NetworkResponse): Response<CategoriesMapper> {
        val mapper = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        val categories: CategoriesMapper = mapper.readValue(String(response.data), CategoriesMapper::class.java)
        return Response.success(categories, null)
    }
}

class GetMechanicsRequest(
    url: String,
    success: Response.Listener<MechanicsMapper>,
    error: Response.ErrorListener
) : JsonRequest<MechanicsMapper>(Method.GET, url, "", success, error) {

    override fun parseNetworkResponse(response: NetworkResponse): Response<MechanicsMapper> {
        val mapper = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        val mechanics: MechanicsMapper = mapper.readValue(String(response.data), MechanicsMapper::class.java)
        return Response.success(mechanics, null)
    }
}