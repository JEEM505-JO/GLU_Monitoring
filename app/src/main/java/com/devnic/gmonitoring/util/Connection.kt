package com.devnic.gmonitoring.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData

class Connection(private val application: Application) {

    private val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val con: MutableLiveData<Boolean> = MutableLiveData()
    fun conexion(): MutableLiveData<Boolean> {
        con.postValue(false)
        connectivityManager.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                con.postValue(
                    when {
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            true
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            true
                        }
                        else -> false
                    }
                )
            }
        }
        return con
    }
}


