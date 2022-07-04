package com.devnic.gmonitoring.ui.months

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.devnic.gmonitoring.database.model.ModelMonths
import com.devnic.gmonitoring.repository.RepositoryMonths

class ViewModelMonths(private val repository: RepositoryMonths) : ViewModel() {
    val getmonths : LiveData<List<ModelMonths>> = repository.getallmonths().asLiveData()

}
class MonthsFactory(private val repository: RepositoryMonths) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelMonths::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelMonths(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}