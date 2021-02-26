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
    private var _datasheet = MutableLiveData<ArrayList<MutableMap<String, String>>>()
   private  lateinit var  myapp:Application
   var i = 0

    val myrepository: SchoolRepository by lazy {
        myapp = (application as SchoolApplication)
        (myapp as SchoolApplication).goodrepository
    }
    init {
        myDataSheet()
    }

    fun getDataSheet(): MutableLiveData<ArrayList<MutableMap<String, String>>> {
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
             i = i + 1
             Log.d("adimou", "i = $i")
             _datasheet =  myrepository.getGoogleSheetData()
             }
         }
}
