package teamfortyone.projects.doctorjobs.Modules

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import teamfortyone.projects.doctorjobs.Helper.Constants
import teamfortyone.projects.multipart.Connection.UserClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@Module
class Settings @Inject constructor(){
    lateinit var userClient: UserClient
    lateinit var retrofit: Retrofit

    @Provides
    fun getService(): UserClient {
        userClient = getClient().create(UserClient::class.java)
        return userClient
    }

    @Provides
    fun provideLinearLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context)
    }

    @Provides
    fun getClient(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        return retrofit

    }
}