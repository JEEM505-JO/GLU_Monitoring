package com.devnic.gmonitoring.database.dao

import androidx.room.*
import com.devnic.gmonitoring.database.model.ModelWeeks
import com.devnic.gmonitoring.database.relation.MonthsWeeks
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoWeeks {

    @Transaction
    @Query("SELECT * FROM MONTHS ORDER BY MONTHS DESC")
    fun getweeks(): List<MonthsWeeks>

    @Query(
        "SELECT W.ID_WEEKS,W.ID_MONTHS,W.INIT,W.FINISH,W.TOTAL_DAY " +
                "FROM WEEKS AS W INNER JOIN  MONTHS AS M  ON W.ID_MONTHS == M.IDMONTHS \n" +
                " WHERE M.IDMONTHS = :idmonths"
    )
    fun getall(idmonths: Long): Flow<List<ModelWeeks>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(modelWeeks: ModelWeeks)

}