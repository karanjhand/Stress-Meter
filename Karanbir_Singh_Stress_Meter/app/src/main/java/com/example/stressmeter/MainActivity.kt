package com.example.stressmeter

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.stressmeter.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // new thread for the vibration
        thread {
            vibratePhone()
        }

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_results
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Vibrate the phone based on the device's Android version in a separate thread
    private fun vibratePhone() {
        // Get the Vibrator service
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Check for the Android version and vibrate accordingly
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For Android O and above, use VibrationEffect
            vibrator.vibrate(VibrationEffect.createOneShot(3000, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            // For older Android versions, use the deprecated vibrate method
            vibrator.vibrate(3000) // Vibrate for 3000 milliseconds
        }

        // Show a Toast in the UI thread indicating that vibration has been triggered,
        //this will be displayed on the main thread
        runOnUiThread {
            Toast.makeText(this, "Vibration triggered", Toast.LENGTH_SHORT).show()
        }
    }
}
