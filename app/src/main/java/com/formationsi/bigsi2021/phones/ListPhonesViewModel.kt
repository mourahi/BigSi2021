package com.formationsi.bigsi2021.phones

import android.app.Application
import android.util.Log
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
    lateinit var datasheet : MutableLiveData<ArrayList<MutableMap<String, String>>>

    val myrepository: SchoolRepository by lazy {
        (application as SchoolApplication).goodrepository
    }

    fun getshcools(): LiveData<List<School>> {
        //mydata.value = listOf(School("","",""))
        mydata = myrepository.allSchool
        return mydata
    }

    fun insert(school: School) {
        GlobalScope.launch {
            myrepository.insert(school)
        }
    }

     fun getDataSheet() {
         GlobalScope.launch {
             datasheet.value = arrayListOf(
                 mutableMapOf(
                     "nom" to "adil",
                     "ecole" to "hassan 2",
                     "tel" to "06666"
                 )
             )
         }

/*         GlobalScope.launch {
             datasheet.value = myrepository.getGoogleSheetData()
         }*/
        Log.d("adil", "dans gaasheet = ${datasheet.value}")
    }

}
