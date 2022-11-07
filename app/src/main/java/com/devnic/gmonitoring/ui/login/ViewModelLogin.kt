package com.devnic.gmonitoring.ui.login

import android.app.Application
import androidx.lifecycle.*
import com.devnic.gmonitoring.repository.RepositoryUser
import com.devnic.gmonitoring.util.Connection
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelLogin(
    private val instance: FirebaseAuth,
    private val repositoryUser: RepositoryUser,
    private val application: Application
) : ViewModel() {

    val valid: MutableLiveData<Boolean> = MutableLiveData()
    val sms: MutableLiveData<String> = MutableLiveData()
    private val getconnection: Connection by lazy {
        Connection(application)
    }
    val emaillogin: MutableLiveData<String> = MutableLiveData()
    private val _emaillogin: LiveData<String> get() = emaillogin
    val passwordlogin: MutableLiveData<String> = MutableLiveData()
    private val _passwordlogin: LiveData<String> get() = passwordlogin

    fun inputnull() {
        if (_emaillogin.value.isNullOrEmpty() || _passwordlogin.value.isNullOrEmpty()) {
            sms.value = "Rellene todos los campos en pantalla"
            valid.value = false
        } else if (_passwordlogin.value!!.length < 5) {
            sms.value = "Contraseña invalida"
            valid.value = false
        } else {
            getconnection.conexion().observeForever {
                if (it) {
                    login()
                } else {
                    localLogin()
                }
            }

        }
    }

    fun connection(): MutableLiveData<String> {
        val con: MutableLiveData<String> = MutableLiveData()
        getconnection.conexion().observeForever {
            if (it) {
                con.value = "Conectado a internet"
                login()
            } else {
                con.value = "Sin conexion a internet"
                localLogin()
            }
        }
        return con
    }

    private fun localLogin() {
        emaillogin.value?.let { user ->
            passwordlogin.value?.let { pass ->
                viewModelScope.launch(Dispatchers.IO) {
                    val authlocal = repositoryUser.authuser(user, pass)
                    if (authlocal.isEmpty()) {
                        sms.postValue("Usuario o Contraseña Incorrecto")
                        valid.postValue(true)
                    } else {
                        valid.postValue(true)
                    }
                }
            }
        }
    }

    private fun login() {
        emaillogin.value?.let { email ->
            passwordlogin.value?.let { pass ->
                instance.signInWithEmailAndPassword(email.trim(), pass.trim())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            valid.value = true
                        } else {
                            valid.value = false
                            sms.value = it.exception.toString()
                        }
                    }
            }
        }
    }
}

class LoginFactory(
    private val instance: FirebaseAuth,
    private val repositoryUser: RepositoryUser,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelLogin::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelLogin(instance, repositoryUser, application) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}