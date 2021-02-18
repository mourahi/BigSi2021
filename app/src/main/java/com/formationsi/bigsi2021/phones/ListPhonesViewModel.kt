package com.formationsi.bigsi2021.phones

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class ListPhonesViewModel : ViewModel() {
    private var data = MutableLiveData<Phone>()
    fun getData():LiveData<Phone>{
        return data
    }

}

data class Phone(val name:String, val age: Int)