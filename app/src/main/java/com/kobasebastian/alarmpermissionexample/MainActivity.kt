package com.kobasebastian.alarmpermissionexample

import android.app.AlarmManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.getSystemService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val infoTextView = findViewById<TextView>(R.id.info_text)
        val grantButton = findViewById<Button>(R.id.grant_button)

        handleUI(isAlarmPermissionGranted(), grantButton, infoTextView)

        val alarmPermissionResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            handleUI(isAlarmPermissionGranted(), grantButton, infoTextView)
        }

        grantButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            val uri = Uri.fromParts("package", packageName, null)
            intent.setData(uri)
            alarmPermissionResultLauncher.launch(intent)
        }
    }

    fun handleUI(permissionGranted: Boolean, grantButton: Button, infoTextView: TextView) {
        if(permissionGranted) {
            infoTextView.text = "SCHEDULE_EXACT_ALARM permission granted"
            grantButton.isEnabled = false
        } else {
            infoTextView.text = "SCHEDULE_EXACT_ALARM permission not granted"
            grantButton.isEnabled = true
        }
    }

    fun isAlarmPermissionGranted(): Boolean {
        val alarmManager = getSystemService<AlarmManager>()!!
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }
}