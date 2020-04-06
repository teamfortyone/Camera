package codestart.info.kotlinphoto.activities

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppConfig {

    //Retrofit builder
    val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://6d4cb525.ngrok.io/")
            /*
            .addHeader("Content-Type", "application/json")
            .addHeader("client", "android")
            .addHeader("language", Locale.getDefault().language)
            .addHeader("os", android.os.Build.VERSION.RELEASE)
             */
            .build()

}