package edu.isel.pdm.beegeesapp

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.volley.VolleyError
import edu.isel.pdm.beegeesapp.bgg.favorites.model.GameProfile
import edu.isel.pdm.beegeesapp.bgg.favorites.ui.FavouritesActivity
import edu.isel.pdm.beegeesapp.bgg.favorites.ui.ProfileGameActivity

const val NOTIFICATION_ID = 100012

class UpdateGameProfileWorker(context : Context, params : WorkerParameters)
    : Worker(context, params) {



    private fun sendNotification(
        app: BggApplication,
        listOfProfileChanges: MutableList<GameProfile>
    ) {

        //TODO -> vários profiles com mudanças
        if (listOfProfileChanges.size == 0) return

        lateinit var action : PendingIntent

        action = if (listOfProfileChanges.size == 1) {
            val intent = ProfileGameActivity.createIntent(app, listOfProfileChanges.first())
            PendingIntent.getActivity(app, 101, intent, FLAG_UPDATE_CURRENT)
        } else {
            PendingIntent.getActivity(app, 101, Intent(app, FavouritesActivity::class.java), FLAG_UPDATE_CURRENT)
        }

        //TODO -> INTERNACIONALIZAÇÃO
        val notification = NotificationCompat.Builder(app, app.PROFILE_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.game_icon)
            .setContentTitle("New Games Available ")
            .setContentText("Check those out : ${listOfProfileChanges.forEach { it.name }} ")
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
            Log.v("DEBUG", "DO WORK !!")
            val app = applicationContext as BggApplication
            val gameProfiles = app.repo.syncGetProfiles()
            val listOfProfileChanges = mutableListOf<GameProfile>()
            gameProfiles.forEach {
                val list = app.repo.getGameProfileChanges(it)
                if (it.gamesList.size != list.size) {
                    it.gamesList = list
                    app.repo.updateGameProfile(it)
                    listOfProfileChanges.add(it)
                }
            }

            sendNotification(app, listOfProfileChanges)
            Result.success()
        }
        catch (error: VolleyError) {
            if (canRecover(error)) Result.retry() else Result.failure()
        }
    }
}