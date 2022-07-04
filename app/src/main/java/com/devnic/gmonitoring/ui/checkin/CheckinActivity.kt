package com.devnic.gmonitoring.ui.checkin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.devnic.gmonitoring.R
import com.devnic.gmonitoring.database.DataBaseM
import com.devnic.gmonitoring.databinding.ActivityCheckinBinding
import com.devnic.gmonitoring.repository.RepositoryUser
import com.devnic.gmonitoring.ui.login.LoginActivity
import com.devnic.gmonitoring.ui.viewmodel.CheckInFactory
import com.devnic.gmonitoring.ui.viewmodel.ViewModelCheckIn
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class CheckinActivity : AppCompatActivity() {


    //instancias de binding y bases de datos room y firebase
    private lateinit var binding: ActivityCheckinBinding
    private lateinit var vmcheckin: ViewModelCheckIn
    private val app by lazy { FirebaseApp.initializeApp(this) }
    private lateinit var factorychek: CheckInFactory
    private lateinit var auth: FirebaseAuth
    private val dataBaseM by lazy { DataBaseM.getDatabase(this) }
    private val repositoryUser by lazy { RepositoryUser(dataBaseM.daouser()) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkin)
        binding.lifecycleOwner = this
        auth = app?.let { FirebaseAuth.getInstance(it) }!!
        factorychek = CheckInFactory(auth, repositoryUser, application)
        vmcheckin = ViewModelProvider(this, factorychek)[ViewModelCheckIn::class.java]
        binding.view = vmcheckin

        binding.progressBarCheckin.isVisible = false
        binding.txtprogresschekin.isVisible = false

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Correo registrado")
        builder.setMessage(
            "Su correo ha sido ingresado exitosamente"
        )
        builder.setPositiveButton("Continuar") { _, _ ->
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("Cancelar") { _, _ ->
            binding.progressBarCheckin.isVisible = false
            binding.txtprogresschekin.isVisible = false
        }

        vmcheckin.valid.observeForever {
            if (it) {
                Handler(Looper.myLooper()!!).postDelayed({
                    builder.create().show()
                    binding.progressBarCheckin.isVisible = false
                    binding.txtprogresschekin.isVisible = false
                },2000)
                binding.progressBarCheckin.isVisible = true
                binding.txtprogresschekin.isVisible = true
            }
        }

        vmcheckin.sms.observeForever {
            Snackbar.make(findViewById(R.id.view_checkin), it, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.parseColor("#F10808")).setTextColor(Color.BLACK).show()
        }

        vmcheckin.connection().observeForever { mensaje ->
            Snackbar.make(findViewById(R.id.view_checkin), mensaje, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.parseColor("#36F576")).setTextColor(Color.BLACK).show()
        }
    }

}