package com.photo.bot

import android.Manifest.permission.BLUETOOTH
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.InputDevice
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    val BLUETOOTH_REQUEST_CODE = 343

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        /*
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Connect to Robot with Bluetooth", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

        val bluetoothButton = findViewById<FloatingActionButton>(R.id.fab)
        bluetoothButton.setOnClickListener {
            enableBluetoothConnection()
        }


        /*
        // from https://developer.android.com/guide/topics/connectivity/bluetooth
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(baseContext,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                        PERMISSION_CODE)
            }
        }*/

        //enable_bluetooth_connection()

        val bluetooth_enable = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(bluetooth_enable, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    // sample code from https://developer.android.com/training/game-controllers/controller-input
    fun getGameControllerIds(): List<Int> {
        val gameControllerDeviceIds = mutableListOf<Int>()
        val deviceIds = InputDevice.getDeviceIds()
        deviceIds.forEach { deviceId ->
            InputDevice.getDevice(deviceId).apply {
                // Verify that the device has gamepad buttons, control sticks, or both.
                if (sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD
                        || sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK) {
                    // This device is a game controller. Store its device ID.
                    gameControllerDeviceIds
                            .takeIf { !it.contains(deviceId) }
                            ?.add(deviceId)
                }
            }
        }
        //var elements = Arrays.toString(gameControllerDeviceIds)
        //findViewById<TextView>(R.id.first).setText(gameControllerDeviceIds)
        return gameControllerDeviceIds
    }

    fun enableBluetoothConnection()
    {
        if (checkSelfPermission(BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOfNulls<String>(1)
            val bluetooth = BLUETOOTH
            permissions[0] = bluetooth
            requestPermissions(permissions, BLUETOOTH_REQUEST_CODE)
            return
        }
    }

}