package com.devnic.gmonitoring.ui.weeks

import androidx.lifecycle.*
import com.devnic.gmonitoring.database.model.ModelWeeks
import com.devnic.gmonitoring.repository.RepositoryWeeks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ViewModelWeeks(private val repositoryWeeks: RepositoryWeeks) : ViewModel() {

    var list: LiveData<List<ModelWeeks>> = MutableLiveData()


    fun getlist(id: Long): LiveData<List<ModelWeeks>> {
        runBlocking {
            launch(Dispatchers.IO) {
               list = repositoryWeeks.getlist(id).asLiveData()
            }
        }
        return list
    }

}

class WeeksFactory(private val repository: RepositoryWeeks) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelWeeks::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelWeeks(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}