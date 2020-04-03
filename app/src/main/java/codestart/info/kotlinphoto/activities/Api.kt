package codestart.info.kotlinphoto.activities

import codestart.info.kotlinphoto.activities.model.Caption
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    //@GET("android")
    //fun getCaption():Call<List<Caption>>        //Call acts as return parameter

    @POST("android")
    fun sendBase64(
            @Body caption: Caption
    ): Call<List<Caption>>

}