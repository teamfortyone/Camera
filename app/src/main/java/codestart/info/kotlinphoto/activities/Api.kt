package codestart.info.kotlinphoto.activities

import codestart.info.kotlinphoto.activities.model.Caption
import retrofit2.Call
import retrofit2.http.*
import okhttp3.RequestBody

interface Api {

    //@GET("android")
    //fun getCaption():Call<List<Caption>>        //Call acts as return parameter

    //@Headers({"Accept: application/json"})
    //@Headers({"Content-Type : application/json"})
    //@Multipart
    @POST("android")
    fun sendBase64(
            @Body caption: Caption
            //@Header({"Content-Type : application/json"}),
            // "Authorization") String authorization})
            //@PartMap map: Map<String,RequestBody >
    ): Call<List<Caption>>

}