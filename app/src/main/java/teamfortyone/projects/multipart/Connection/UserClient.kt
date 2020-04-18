package teamfortyone.projects.multipart.Connection

import teamfortyone.projects.multipart.Model.ResponseData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface UserClient {

    @Multipart
    @POST("android1")
    fun uploadFile(
        @Part file:MultipartBody.Part
    ): Call<ResponseData> //Call<ResponseBody>


}