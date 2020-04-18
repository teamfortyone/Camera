package teamfortyone.projects.dagger2.Component

import dagger.Component
import teamfortyone.projects.doctorjobs.Modules.Settings
import javax.inject.Singleton

@Singleton
@Component (modules = [Settings::class])
interface MainComponent {
    fun connect() :Settings
}