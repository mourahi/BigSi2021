package com.formationsi.bigsi2021

import android.app.Application
import android.content.ContentResolver
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.formationsi.bigsi2021.db.School
import com.formationsi.bigsi2021.db.Tupdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PrincipalViewModel(application: Application) : AndroidViewModel(application) {
    private val _phonesRepository = (application as SchoolApplication).phonesRepository
    private val _updateRepository = (application as SchoolApplication).updateRepository

    var dataphone = MutableLiveData<List<School>>()
   // lateinit var dataupdate:LiveData<List<Tupdate>>


    fun getDataPhones(txtsearch: String = "") {
        dataphone = _phonesRepository.getData(txtsearch)
    }

    fun getDataTupdate() {
        MyConnection.observeForever {
            if(it){
                _updateRepository.getStateUpdate()
                Log.d("adil","je suis dans getDataTupdate")
            }
        }

    }







    // Room
    fun insert(school: School) {
        GlobalScope.launch {
            _phonesRepository.insert(school)
        }
    }


}