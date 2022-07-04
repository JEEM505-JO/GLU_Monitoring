package com.devnic.gmonitoring.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.devnic.gmonitoring.database.model.ModelUser
import com.devnic.gmonitoring.repository.RepositoryUser
import com.devnic.gmonitoring.util.Connection
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelCheckIn(
    private val instance: FirebaseAuth,
    private val repositoryUser: RepositoryUser,
    private val application: Application
) : ViewModel() {

    //valid
    val valid: MutableLiveData<Boolean> = MutableLiveData()
    val sms: MutableLiveData<String> = MutableLiveData()
    private val getcon: Connection by lazy {
        Connection(application)
    }


    //input user
    val user: MutableLiveData<String> = MutableLiveData()
    private val _user: LiveData<String> get() = user
    val email: MutableLiveData<String> = MutableLiveData()
    private val _email: LiveData<String> get() = user
    val pass: MutableLiveData<String> = MutableLiveData()
    private val _pass: LiveData<String> get() = pass

    private fun createUser() {
        email.value?.let {
            pass.value?.let { it1 ->
                instance.createUserWithEmailAndPassword(it.trim(), it1.trim())
                    .addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            sms.value =
                                "FUE ENVIADO ${_email.value.toString()}  ${_pass.value.toString()}"
                            createLocalUser()
                            valid.value = true
                        } else {
                            sms.value = result.exception?.message
                            valid.value = false
                        }
                    }
            }
        }
    }

    private fun sedVerification() {
        instance.currentUser?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isComplete) {
                println("verificacion IS COMPLETE")
            } else if (it.isSuccessful) {
                println("VERIFICACION ISSUCCEFUL")
            } else {
                println("VERIFICACION ${it.exception.toString()}")
            }
        }
    }

    fun validnull() {
        if (_user.value.isNullOrEmpty() || _email.value.isNullOrEmpty() || _pass.value.isNullOrEmpty()) {
            sms.postValue("Rellene todos los campos en pantalla")
            valid.postValue(false)
        } else
            if (_pass.value?.length.let { it!! <= 5 }) {
                sms.postValue("Su contraseña debe contener al menos 5 caracteres")
                valid.postValue(false)
            } else if (_user.value?.length.let { it!! < 5 }) {
                sms.postValue("El nombre debe contener al menos 5")
                valid.postValue(false)
            } else {
                getcon.conexion().observeForever {
                    if (it) {
                        createUser()
                    } else {
                        createLocalUser()
                    }
                }
            }
    }

    fun connection(): MutableLiveData<String> {
        val conexion: MutableLiveData<String> = MutableLiveData()
        getcon.conexion().observeForever {
            if (it) {
                conexion.value = "Conectado a internet"
            } else {
                conexion.value = "Sin conexion a internet"
            }
        }
        return conexion
    }

    private fun createLocalUser() = viewModelScope.launch(Dispatchers.IO) {
        _email.value?.let { em ->
            _pass.value?.let { pas ->
                _user.value?.let {
                    try {
                        repositoryUser.insert(ModelUser(username = it, email = em, password = pas))
                        valid.postValue(true)
                    } catch (e: Exception) {
                        println("LOCAL_USER ERROR :${e.message}")
                        valid.postValue(false)
                    }
                }
            }
        }
    }
}

class CheckInFactory(
    private val instance: FirebaseAuth,
    private val repositoryUser: RepositoryUser,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelCheckIn::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelCheckIn(instance, repositoryUser, application) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}
