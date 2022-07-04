package com.devnic.gmonitoring.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.devnic.gmonitoring.R
import com.devnic.gmonitoring.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        val navconfig = findNavController(R.id.nav_host_fragment)
        val config = AppBarConfiguration(navconfig.graph)
        toolbar.setupWithNavController(navconfig,config)


    }
}