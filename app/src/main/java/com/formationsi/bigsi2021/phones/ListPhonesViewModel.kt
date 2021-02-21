package com.formationsi.bigsi2021.phones

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.formationsi.bigsi2021.SchoolApplication
import com.formationsi.bigsi2021.db.School
import com.formationsi.bigsi2021.db.SchoolRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ListPhonesViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var mydata: LiveData<List<School>>
     val myrepository: SchoolRepository by lazy {
         (application as SchoolApplication).goodrepository
     }

    fun getshcools(): LiveData<List<School>> {
        mydata = myrepository.allSchool
        if (mydata.value == null) {
            insert(School("adil", "mourahi", "066666"))
        }
        Log.d("adil", "mydata = ${mydata.value}")
        return mydata
    }

    fun insert(school: School) {
        GlobalScope.launch {
            myrepository.insert(School("kamal", "mourahi", "066666"))
        }
    }
}
