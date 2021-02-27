package com.formationsi.bigsi2021

import android.app.Application
import com.formationsi.bigsi2021.db.SchoolDatabase
import com.formationsi.bigsi2021.db.SchoolRepository

class SchoolApplication : Application() {

        private val database by lazy { SchoolDatabase.getDatabase(this) }
        val goodrepository by lazy {
                SchoolRepository(database.schoolDao(),this)
        }

}