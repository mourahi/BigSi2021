package com.formationsi.bigsi2021.phones

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.formationsi.bigsi2021.SchoolApplication
import com.formationsi.bigsi2021.db.School
import com.formationsi.bigsi2021.db.SchoolRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ListPhonesViewModel(application: Application) : AndroidViewModel(application) {
    //private lateinit var mydata: LiveData<List<School>>
    private var _datasheet = MutableLiveData<List<School>>()
    private  lateinit var  myapp:Application

    private val myrepository: SchoolRepository by lazy {
        myapp = (application as SchoolApplication)
        (myapp as SchoolApplication).goodrepository
    }

    fun getData(): MutableLiveData<List<School>> {
        _datasheet = myrepository.getData() as MutableLiveData<List<School>>
        return _datasheet
    }


    fun insert(school: School) {
        GlobalScope.launch {
           myrepository.insert(school)
        }
    }
}
