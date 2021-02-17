package com.formationsi.bigsi2021.phones

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListPhonesViewModel : ViewModel() {
    private val data = MutableLiveData<Phone>()
    fun getData():LiveData<Phone>{
         data.value = Phone("adil", 22)
        return data
    }

}

data class Phone(val name:String, val age: Int)