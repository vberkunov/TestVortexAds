package com.exampl.mytestapp.api

import com.exampl.mytestapp.entity.Links
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("/prod")
    fun getLinks(): Call<Links>


}