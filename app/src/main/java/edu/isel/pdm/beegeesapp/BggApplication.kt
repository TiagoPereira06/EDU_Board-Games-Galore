package edu.isel.pdm.beegeesapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.*
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import edu.isel.pdm.beegeesapp.bgg.Repository
import java.util.concurrent.TimeUnit

class BggApplication : Application() {

    val PROFILE_NOTIFICATION_CHANNEL_ID: String = "ProfileNotificationChannelId"
    val DB_UPDATE_JOB_ID : String = "DB_UPDATE_JOB_ID"

    lateinit var repo: Repository
    lateinit var queue: RequestQueue
    lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()
        repo = Repository(this)
        queue = Volley.newRequestQueue(this)

        workManager = WorkManager.getInstance()
        scheduleGamesProfileUpdate()
        createNotificationChannels()
    }


    private fun scheduleGamesProfileUpdate() {
        //todo -> settings guardadas -> val settings = repo.getCurrentNotificationSettings()

        val updateRequest = PeriodicWorkRequestBuilder<UpdateGameProfileWorker>(
            1, TimeUnit.SECONDS
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED)
                    .setRequiresCharging(true)
                    .build())
            .build()

        workManager.enqueueUniquePeriodicWork(DB_UPDATE_JOB_ID, ExistingPeriodicWorkPolicy.KEEP, updateRequest)
    }




    private fun createNotificationChannels() {

        // Create notification channel if we are running on a O+ device
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                PROFILE_NOTIFICATION_CHANNEL_ID,
                "Game Profile Updates",
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Channel for games in Profiles update notification"
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }
}