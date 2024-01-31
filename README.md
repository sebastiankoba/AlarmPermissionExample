# Alarm Permission Example
This is a simple example app that demonstrates how to check for the `SCHEDULE_EXACT_ALARM` permission to set alarms at exact times. The `SCHEDULE_EXACT_ALARM` permission was introduced in Android 12. In Android 13 and above, this permission must be granted by the user by which it must be handled. The app checks if the user has granted the `SCHEDULE_EXACT_ALARM` permission. If the permission is not granted, the app displays a button that redirects the user to the settings screen where they can grant the permission.

## Screenshots
![Alt Text](https://github.com/sebastiankoba/AlarmPermissionExample/blob/main/Screen_recording_20240131_101032%20(1).gif)

## Implementation
The app checks for the `SCHEDULE_EXACT_ALARM` permission using the following code:

```kotlin
fun isAlarmPermissionGranted(): Boolean {
        val alarmManager = getSystemService<AlarmManager>()!!
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }
```
If the permission is not granted, a button is displayed that redirects the user to the settings screen to grant the permission:
```kotlin
grantButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            val uri = Uri.fromParts("package", packageName, null)
            intent.setData(uri)
            alarmPermissionResultLauncher.launch(intent)
        }
```

``ActivityResultLauncher`` is used to recognise when the user has returned from the settings to the app:
```kotlin
val alarmPermissionResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            handleUI(isAlarmPermissionGranted(), grantButton, infoTextView)
        }
```
