package codestart.info.kotlinphoto.activities.services


import retrofit2.Call
import retrofit2.http.*

/*
interface caption {

    //fun getCaption():Call<>

    fun sendString(@Body newgenerate: generate): Call<generate>
}
*/

interface APIService {

    @POST("register")
    @FormUrlEncoded
    fun registrationPost(@Field("email") email: String,
                         @Field("password") password: String): Call<Registration>}


//**App Utils**

object ApiUtils {

    val BASE_URL = "your_url"

    val apiService: APIService
        get() = RetrofitClient.getClient(BASE_URL)!!.create(APIService::class.java)

}
