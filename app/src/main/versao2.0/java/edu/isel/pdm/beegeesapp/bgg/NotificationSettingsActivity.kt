package edu.isel.pdm.beegeesapp.bgg

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R

class NotificationSettingsActivity : AppCompatActivity() {
    private lateinit var freqSpinner: Spinner
    private lateinit var batSpinner: Spinner
    private lateinit var metSpinner: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_settings)
        supportActionBar?.title = "Notification Settings"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        super.onCreate(savedInstanceState)

        freqSpinner = findViewById(R.id.freq_spinner)
        batSpinner = findViewById(R.id.bat_spinner)
        metSpinner = findViewById(R.id.met_spinner)


        attachAdapterOptions(freqSpinner)
        attachAdapterBinary(batSpinner)
        attachAdapterBinary(metSpinner)
        setSpinnersConfig()

    }

    private fun setSpinnersConfig() {
        /*
        val settings = (application as BggApplication).repo.getCurrentNotificationSettings()
        if(settings != null) {
            freqSpinner.setSelection(settings.frequency)
            batSpinner.setSelection(settings.chargingRequired)
            metSpinner.setSelection(settings.meteredConnection)
        }

         */

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_savechanges, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        saveSpinnersSelection()
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun saveSpinnersSelection() {
        val saveSettings = NotificationSettings(
            freqSpinner.selectedItemPosition,
            batSpinner.selectedItemPosition,
            metSpinner.selectedItemPosition
        )
        //(application as BggApplication).repo.updateCurrentNotificationSettings(saveSettings)
    }

    private fun attachAdapterBinary(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            this,
            R.array.notificationBinaray_Array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

    }

    private fun attachAdapterOptions(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            this,
            R.array.notificationFreq_Array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }
}

@Entity
data class NotificationSettings(
    @PrimaryKey val frequency: Int,
    val chargingRequired: Int,
    val meteredConnection: Int
)