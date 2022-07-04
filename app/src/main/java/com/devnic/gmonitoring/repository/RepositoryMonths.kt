package com.devnic.gmonitoring.repository

import androidx.annotation.WorkerThread
import com.devnic.gmonitoring.database.dao.DaoMonths
import com.devnic.gmonitoring.database.model.ModelMonths
import kotlinx.coroutines.flow.Flow

class RepositoryMonths(private val dao: DaoMonths) {

    fun getallmonths(): Flow<List<ModelMonths>> = dao.getAllMonths()

    @WorkerThread
    suspend fun insert(months: ModelMonths) =
        dao.insertMonth(months)
}