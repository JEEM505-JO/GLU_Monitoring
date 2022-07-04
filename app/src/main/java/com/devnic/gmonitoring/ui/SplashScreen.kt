package com.devnic.gmonitoring.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.devnic.gmonitoring.databinding.ActivitySplashScreenBinding
import com.devnic.gmonitoring.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.ANIMATION_CHANGED,
            WindowManager.LayoutParams.ANIMATION_CHANGED
        )

        val version = applicationContext.packageManager.getPackageInfo(applicationContext.packageName,0).versionName
        binding.version.text = "V $version"
        var proceso = 0


        Thread {
            Looper.getMainLooper()!!.let {
                while (proceso < 100) {
                    proceso += 5
                    Handler(it).post {
                        binding.progressbarsplash
                            .progress = proceso
                        binding.progressN.text =
                            "$proceso / ${binding.progressbarsplash.max}"
                        if (proceso == 100) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    try {
                        Thread.sleep(100)
                    } catch (e: Exception) {
                        println("Error Splash Thread $e")
                    }
                }
            }
        }.start()
    }
}