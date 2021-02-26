package com.formationsi.bigsi2021.phones

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.formationsi.bigsi2021.SchoolApplication
import com.formationsi.bigsi2021.db.School
import com.formationsi.bigsi2021.db.SchoolRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ListPhonesViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var mydata: LiveData<List<School>>
    private var _datasheet = MutableLiveData<List<School>>()
   private  lateinit var  myapp:Application

    private val myrepository: SchoolRepository by lazy {
        myapp = (application as SchoolApplication)
        (myapp as SchoolApplication).goodrepository
    }
    init {
        myDataSheet()
    }

    fun getDataSheet(): MutableLiveData<List<School>> {
        return _datasheet
    }
    fun getshcools(): LiveData<List<School>> {
        mydata = myrepository.allSchool
        return mydata
    }

    fun insert(school: School) {
        GlobalScope.launch {
           myrepository.insert(school)
        }
    }

     private fun myDataSheet() {
         viewModelScope.launch {
             _datasheet = myrepository.getGoogleSheetData()

             }
         }
}
