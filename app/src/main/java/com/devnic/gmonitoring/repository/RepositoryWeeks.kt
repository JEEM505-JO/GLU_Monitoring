package com.devnic.gmonitoring.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devnic.gmonitoring.database.dao.DaoWeeks
import com.devnic.gmonitoring.database.model.ModelWeeks
import com.devnic.gmonitoring.database.relation.MonthsWeeks
import kotlinx.coroutines.flow.Flow

class RepositoryWeeks(private val dao: DaoWeeks) {

    @WorkerThread
    fun getallweeks() : List<MonthsWeeks> = dao.getweeks()

    fun getlist(idmonth: Long): Flow<List<ModelWeeks>>{
        return dao.getall(idmonth)
    }

    suspend fun insert(modelWeeks: ModelWeeks) =
        dao.insert(modelWeeks)
}