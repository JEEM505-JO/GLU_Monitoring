package com.devnic.gmonitoring.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.devnic.gmonitoring.R
import com.devnic.gmonitoring.database.DataBaseM
import com.devnic.gmonitoring.databinding.ActivityLoginBinding
import com.devnic.gmonitoring.repository.RepositoryUser
import com.devnic.gmonitoring.ui.checkin.CheckinActivity
import com.devnic.gmonitoring.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var vmlogin: ViewModelLogin
    private lateinit var faccheckin: LoginFactory
    private lateinit var auth: FirebaseAuth
    private val dataBaseM by lazy { DataBaseM.getDatabase(this) }
    private val repositoryUser by lazy { RepositoryUser(dataBaseM.daouser()) }
    private val app by lazy { FirebaseApp.initializeApp(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        auth = app?.let { FirebaseAuth.getInstance(it) }!!
        faccheckin = LoginFactory(auth, repositoryUser, application)
        vmlogin = ViewModelProvider(this, faccheckin)[ViewModelLogin::class.java]
        binding.view = vmlogin
        binding.progressbarlogin.isVisible = false
        binding.btnregistrarseLogin.setOnClickListener {
            val intent = Intent(this, CheckinActivity::class.java)
            startActivity(intent)
        }

        vmlogin.valid.observeForever {
            if (it) {
                Handler(Looper.myLooper()!!).postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    binding.progressbarlogin.isVisible = false
                }, 2000)
                binding.progressbarlogin.isVisible = true
            }
        }

        vmlogin.sms.observeForever {
            Snackbar.make(findViewById(R.id.view_login), it, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.parseColor("#F10808")).setTextColor(Color.BLACK).show()
        }


        vmlogin.connection().observeForever {
            Snackbar.make(findViewById(R.id.view_login), it, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.parseColor("#36F576")).setTextColor(Color.BLACK).show()
        }
    }
}