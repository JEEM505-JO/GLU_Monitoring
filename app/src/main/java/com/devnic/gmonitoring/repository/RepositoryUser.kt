package com.devnic.gmonitoring.repository

import androidx.annotation.WorkerThread
import com.devnic.gmonitoring.database.dao.DaoUser
import com.devnic.gmonitoring.database.model.ModelUser

class RepositoryUser(private val daoUser: DaoUser) {

    @WorkerThread
    suspend fun insert(user: ModelUser) =
        daoUser.insert(user)

    @WorkerThread
    fun authuser(user: String, pass: String): List<ModelUser> {
       return daoUser.getUser(user, pass)
    }
}