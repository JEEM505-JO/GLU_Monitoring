package com.devnic.gmonitoring.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.devnic.gmonitoring.database.model.ModelMonths
import com.devnic.gmonitoring.database.model.ModelWeeks


data class MonthsWeeks(
    @Embedded
    val months: ModelMonths,
    @Relation(
        parentColumn = "IDMONTHS",
        entityColumn = "ID_MONTHS"
    )
    val weeks: List<ModelWeeks>
)
