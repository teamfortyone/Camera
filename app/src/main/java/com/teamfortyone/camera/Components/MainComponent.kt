package com.teamfortyone.camera.Components

import android.content.Context
import android.content.ServiceConnection
import dagger.Component
import mostafa.projects.doctorjobs.Modules.Settings
import javax.inject.Singleton

@Singleton
@Component (modules = [Settings::class])
interface MainComponent {
    fun connect() :Settings
}