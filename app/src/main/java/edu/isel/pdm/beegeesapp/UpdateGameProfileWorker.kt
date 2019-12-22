package edu.isel.pdm.beegeesapp

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.volley.VolleyError

import edu.isel.pdm.beegeesapp.bgg.primaryActivities.favourites.ProfileGames

const val NOTIFICATION_ID = 100012

class UpdateGameProfileWorker(context : Context, params : WorkerParameters)
    : Worker(context, params) {

    private fun sendNotification(app: BggApplication) {

        val action = PendingIntent.getActivity(
            app, 101,
            ProfileGames.createIntent(app, true), FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat.Builder(app, app.PROFILE_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.game_icon)
            .setContentTitle("New Games available")
            .setContentText("Check that out!")
            .setContentIntent(action)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(app).notify(NOTIFICATION_ID, notification)
    }
    private fun canRecover(error: VolleyError): Boolean {
        val statusCode =
            if (error.networkResponse != null) error.networkResponse.statusCode
            else 0
        return statusCode in 500..599
    }

    override fun doWork(): Result {
        return try {
            val app = applicationContext as BggApplication
            //val quotes = syncFetchTodayQuotes(app)
            //syncSaveTodayQuotesFromDTO(app, app.db, quotes)
            sendNotification(app)
            Result.success()
        }
        catch (error: VolleyError) {
            if (canRecover(error)) Result.retry() else Result.failure()
        }
    }
}