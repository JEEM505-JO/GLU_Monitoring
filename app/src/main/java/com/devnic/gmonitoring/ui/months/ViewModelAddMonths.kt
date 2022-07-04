package com.devnic.gmonitoring.ui.months

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devnic.gmonitoring.database.model.ModelMonths
import com.devnic.gmonitoring.repository.RepositoryMonths
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelAddMonths(private val repository: RepositoryMonths) : ViewModel() {
    val year: MutableLiveData<Int> = MutableLiveData(0)
    val months: MutableLiveData<Int> = MutableLiveData(0)
    val day: MutableLiveData<Int> = MutableLiveData(0)
    val description: MutableLiveData<String> = MutableLiveData()
    val sms: MutableLiveData<String> = MutableLiveData()
    val boolean : MutableLiveData<Boolean> = MutableLiveData()

    fun valid() {
        if (year.value == 0 || months.value == 0 || day.value == 0 || description.value.isNullOrEmpty()) {
            sms.value = "CAMPOS NULOS"
            println("year = ${year.value}")
            boolean.value = false
        } else {
            sms.value = "Mes registrado con exito ${year.value.toString()}"
            println("year = ${year.value}")
                insert()
            boolean.value = true
        }
    }


   fun insert() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.insert(
                ModelMonths(
                    year = year.value!!.toInt(),
                    months = months.value!!.toInt(),
                    day = day.value!!.toInt(),
                    description = description.value!!.toString()
                )
            )
        }catch (e : Exception){
            println("ERROR ${e.message}, ${e.localizedMessage}")
        }
    }

}

class AddMonthsFactory(private val repository: RepositoryMonths) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelAddMonths::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelAddMonths(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}