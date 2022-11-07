package com.devnic.gmonitoring.ui.weeks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devnic.gmonitoring.database.model.ModelWeeks
import com.devnic.gmonitoring.repository.RepositoryWeeks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelAddWeeks(private val repositoryWeeks: RepositoryWeeks) : ViewModel() {
    val initday: MutableLiveData<Int> = MutableLiveData(0)
    val idmonth: MutableLiveData<Long> = MutableLiveData()
    val finday: MutableLiveData<Int> = MutableLiveData(0)
    val sms: MutableLiveData<String> = MutableLiveData()
    val boolean: MutableLiveData<Boolean> = MutableLiveData()
    private var weeksize: MutableLiveData<Int> = MutableLiveData()

    fun weekvalid() {
        if (initday.value == 0 || finday.value == 0) {
            boolean.postValue(false)
            sms.value = "Seleccione ambas fechas"
        } else {
            weeksize.value = finday.value!! - initday.value!!
            if (weeksize.value!! < 6 || weeksize.value!! > 7 ) {
                sms.value = "El semana debe ser no menor y no mayor a 7 Dias"
            } else {
                insert()
                sms.value = "Semana ingresada"
                boolean.postValue(true)
                }
            }
        }
//    private fun sizeselect(): Int {
//     val inicio = initday.value
//     val fin = finday.value
//    val result = fin!! - inicio!!
//        return result
//    }

    fun insert() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repositoryWeeks.insert(
                ModelWeeks(
                    months = idmonth.value!!,
                    init = initday.value!!,
                    finish = finday.value!!,
                    total_day = weeksize.value!!
                )
            )
        } catch (e: Exception) {
            println("ERROR INSERT WEEKS ${e.message}")
        }
    }
}

class AddWeeksFactory(private val repository: RepositoryWeeks) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelAddWeeks::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelAddWeeks(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}