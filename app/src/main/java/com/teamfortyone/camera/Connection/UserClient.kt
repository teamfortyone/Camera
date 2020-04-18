package com.teamfortyone.camera.Connection

import mostafa.projects.multipart.Model.ResponseData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap


interface UserClient {

    @Multipart
    @POST("android1")
    fun uploadFile(
        @Part file:MultipartBody.Part
    ): Call<ResponseData> //Call<ResponseBody>


}