package com.formationsi.bigsi2021.phones

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.formationsi.bigsi2021.SchoolApplication
import com.formationsi.bigsi2021.db.School
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ListPhonesViewModel(application: Application) : AndroidViewModel(application) {
    private var _dataphone = MutableLiveData<List<School>>()
    private val myrepository = (application as SchoolApplication).goodrepository

    fun getData(): MutableLiveData<List<School>> {
        _dataphone = myrepository.getData()
        Log.d("adil","resultat = "+_dataphone.value)
        return _dataphone
    }

    fun insert(school: School) {
        GlobalScope.launch {
           myrepository.insert(school)
        }
    }
}
