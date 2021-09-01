package com.example.tryretrofit.service

import com.example.tryretrofit.model.Ex2Item
import retrofit2.Call
import retrofit2.http.GET

interface DataAPI {

    @GET("orgs")
    fun getData():Call<List<Ex2Item>>

}