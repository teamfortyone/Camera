package teamfortyone.projects.dagger2.Component

//import android.content.Context
//import android.content.ServiceConnection
import dagger.Component
import teamfortyone.projects.doctorjobs.Modules.Settings
import javax.inject.Singleton

@Singleton
@Component (modules = [Settings::class])
interface MainComponent {
    fun connect() :Settings
}