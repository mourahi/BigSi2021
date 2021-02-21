package com.formationsi.bigsi2021

import android.app.Application
import com.formationsi.bigsi2021.db.SchoolDatabase
import com.formationsi.bigsi2021.db.SchoolRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SchoolApplication : Application() {

        val database by lazy { SchoolDatabase.getDatabase(this) }
        val goodrepository by lazy { SchoolRepository(database.schoolDao()) }

    // No need to cancel this scope as it'll be torn down with the process

}