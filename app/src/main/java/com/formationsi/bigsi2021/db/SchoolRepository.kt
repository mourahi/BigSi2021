package com.formationsi.bigsi2021.db

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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


class SchoolRepository(private val schoolDao: SchoolDao, private val context: Context) {
    private var _datasheet = MutableLiveData<List<School>>()

    fun getData(): LiveData<List<School>> {
        schoolDao.getAllSchool().observeForever { t ->
            if (!t.isNullOrEmpty()) {
                _datasheet.value = t
            } else if (isConnected()) {
                getGoogleSheetData().observeForever { i ->
                    if (!i.isNullOrEmpty()) {
                       insertMultiple(i)
                        _datasheet.value = i
                        Log.d("adil", "c'est OK POUR INSERT MUTIPLE it=$i")
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


    private fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun getGoogleSheetData(): MutableLiveData<List<School>> {
        getJSONArrayFromInternet()
        return _datasheet
    }

    private fun getJSONArrayFromInternet(
        idSheet: String = "1F49X3Jo823vUJ9hrr1vheCeCI2LhCIN_gf9sxMrgK5k"
    ) {
        Log.d("adimou", "getJSONArrayFromInternet")
        //_state.value = "START"
        val myLocalList: ArrayList<MutableMap<String, String>> = ArrayList()
        val url =
            URL("https://spreadsheets.google.com/feeds/list/$idSheet/1/public/values?alt=json")
        val urlConnection = url.openConnection() as HttpURLConnection
        GlobalScope.launch(Dispatchers.IO) {
            try {
                //_state.value = "CONNECTED"
                val da = BufferedInputStream(urlConnection.inputStream)
                val bufferedReader = BufferedReader(InputStreamReader(da) as Reader?)
                val stringBuilder = StringBuilder()
                bufferedReader.forEachLine {
                    stringBuilder.append(it)
                }
                val res =
                    JSONObject(stringBuilder.toString()).getJSONObject("feed").getJSONArray("entry")
                Log.d("adil", "RECHERCHE connexion  length=" + res.length())
                var mycol = mutableListOf<String>()
                for (i in 0 until res.length()) {
                    val obj: MutableMap<String, String> = mutableMapOf()
                    if (i == 0) {
                        res.getJSONObject(i).keys().forEach {
                            if (it.startsWith("gsx$") && !mycol.contains(it)) mycol.add(it)
                        }
                        mycol = mycol.map { it.replace("gsx$", "") }.toMutableList()
                        //_col.value = mycol
                    }
                    //Log.d("adil", "i= $i  mycol = $mycol ")
                    for (c in mycol) {
                        obj[c] = res.getJSONObject(i).getJSONObject("gsx$$c")["\$t"].toString()
                    }
                    myLocalList.add(obj)
                }

            } catch (e: Exception) {
                Log.d("adil", "PROBLEME récuperation depuis internet exception = " + e.message)
                // _state.value = "Erreur Serveur"
                GlobalScope.launch(Dispatchers.Main) {
                    _datasheet.value = listOf()
                }
            } finally {
                urlConnection.disconnect()
                Log.d("adil", "deconnnexion")
                GlobalScope.launch(Dispatchers.Main) {
                    _datasheet.value = mapToObjet(myLocalList)
                    // Log.d("adil", "resultat du serveur === $myLocalList")
                }
            }
        }
    }

    private fun mapToObjet(src: ArrayList<MutableMap<String, String>>): List<School> {
        val resulat: MutableList<School> = mutableListOf()
        if (src.size > 0) {
            src.forEach {
                resulat.add(
                    School(
                        it["nom"].toString(),
                        it["tel"].toString(),
                        it["ecole"].toString()
                    )
                )
            }
        }
        return resulat
    }

}
