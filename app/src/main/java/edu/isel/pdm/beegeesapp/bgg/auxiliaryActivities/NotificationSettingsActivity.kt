package edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.isel.pdm.beegeesapp.R

class NotificationSettingsActivity : AppCompatActivity() {
    private var PRIVATE_MODE = 0
    private lateinit var freqSpinner: Spinner
    private lateinit var batSpinner: Spinner
    private lateinit var metSpinner: Spinner
    private val choices = mutableListOf<Int>()


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
/*       val settings = repo.getCurrentSetings
        freqSpinner.setSelection(settings[0])
        batSpinner.setSelection(settings[1])
        metSpinner.setSelection(settings[2])*/

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
        var saveSettings = NotificationSettings(
            freqSpinner.selectedItemPosition,
            batSpinner.selectedItemPosition,
            metSpinner.selectedItemPosition
            )
        //repo.updateNotificationSettings(saveSettings)
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