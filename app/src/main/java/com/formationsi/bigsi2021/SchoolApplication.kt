package com.formationsi.bigsi2021

import android.app.Application
import androidx.lifecycle.LiveData
import com.formationsi.bigsi2021.db.SchoolDatabase
import com.formationsi.bigsi2021.db.SchoolRepository
import com.formationsi.bigsi2021.db.TupdateRepository

class SchoolApplication : Application() {

    private val database by lazy { SchoolDatabase.getDatabase(this) }
    val phonesRepository by lazy { SchoolRepository(database.schoolDao()) }
    val updateRepository by lazy { TupdateRepository(database.tupdateDao()) }


init {
    MyConnection.init(this)
}

}