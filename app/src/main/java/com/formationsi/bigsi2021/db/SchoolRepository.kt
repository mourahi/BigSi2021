package com.formationsi.bigsi2021.db

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.formationsi.bigsi2021.MyConnection
import com.formationsi.bigsi2021.getJSONArrayFromInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL

class TupdateRepository(private val tupdateDao:TupdateDao){
    //var dataTupdate = tupdateDao.getAlltUpdate()
    fun getStateUpdate(){

            try {
                val aa = tupdateDao.getAlltUpdate()
            }catch (e:Exception){
                Log.d("adil","erreur = $e")
            }

            getJSONArrayFromInternet(3).observeForever{
                if(!it.isNullOrEmpty()) {
                    val a = mapToTupdate(it).value
                    Log.d("adil"," a=$a")
                    insertMultiple(a!!)
                }
            }



    }
    @WorkerThread
    fun insertMultiple(s: List<Tupdate>) {
        tupdateDao.insertMutliple(s)
    }

    private fun mapToTupdate(src: ArrayList<MutableMap<String, String>>): MutableLiveData<List<Tupdate>>{
        Log.d("adil","src = $src")
        val resulat = MutableLiveData<List<Tupdate>>()
        val li = mutableListOf<Tupdate>()
        if (src.size > 0) {
            src.forEach {
                li.add(
                    Tupdate(
                        it["numero"].toString(),
                        it["title"].toString()
                    )
                )
            }
        }
        resulat.value = li
        return resulat
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(tupdate: Tupdate) {
        tupdateDao.insert(tupdate)
    }

}


class SchoolRepository(private val schoolDao: SchoolDao) {
    private var _datasheet = MutableLiveData<List<School>>()

    fun getData(txtsearch: String = ""): MutableLiveData<List<School>> {
        if (txtsearch != "") {
            schoolDao.getDataQuery(txtsearch).observeForever {
                _datasheet.value = it
            }
        } else {
            schoolDao.getAllSchool().observeForever { roo ->
                if (!roo.isNullOrEmpty()) {
                    Log.d("adimou", " resulat cherche dans le ROOM")
                    _datasheet.value = roo
                } else if (MyConnection.isInternetOn()) {
                    getJSONArrayFromInternet().observeForever {
                        val a = mapToSchool(it).value
                       if(!a.isNullOrEmpty()) {
                           _datasheet.value = a!!
                           insertMultiple(a)
                       }
                    }
                }
            }
        }
        return _datasheet
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(school: School) {
        schoolDao.insert(school)
    }

    @WorkerThread
    fun insertMultiple(s: List<School>) {
        schoolDao.insertMutliple(s)
    }




    private fun mapToSchool(src: ArrayList<MutableMap<String, String>>): MutableLiveData<List<School>>{
        Log.d("adil","src = $src")
        val resulat = MutableLiveData<List<School>>()
        val li = mutableListOf<School>()
        if (src.size > 0) {
            src.forEach {
                li.add(
                    School(
                        it["nom"].toString(),
                        it["tel"].toString(),
                        it["ecole"].toString(),
                        it["gresa"].toString(),
                        it["commune"].toString(),
                        it["fonction"].toString(),
                        it["cycle"].toString(),
                        it["email"].toString(),
                        it["geo"].toString()
                    )
                )
            }
        }
        resulat.value = li
        return resulat
    }

}