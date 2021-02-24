package com.formationsi.bigsi2021.db

import android.util.Log
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


/*private data class Ecole
    (val nom:String,
    val gresa:String,
    val commune:String,
    val ecole:String,
    val tel:String,
    val fonction:String,
    val cycle:String,
    val email:String,
    val geo:String
)*/
class GoogleSheet(val idSheet: String = "1F49X3Jo823vUJ9hrr1vheCeCI2LhCIN_gf9sxMrgK5k") {
    private lateinit var _col:MutableLiveData<List<String>>
    private lateinit var _state:MutableLiveData<String>
    private lateinit var _datasheet : MutableLiveData<ArrayList<MutableMap<String, String>>>


    fun getCol(): MutableLiveData<List<String>> {
        if(_state.value != "SUCCES") _col.value = listOf()
        return _col
    }
    fun getState():MutableLiveData<String>{
         if(_state.value.isNullOrEmpty()) _state.value = ""
        return _state
    }
    fun getDataSheet(): MutableLiveData<ArrayList<MutableMap<String, String>>> {
        getJSONArrayFromInternet()
        return _datasheet
    }

    fun getJSONArrayFromInternet() {
            _state.value = "START"
            val myLocalList: ArrayList<MutableMap<String, String>> = ArrayList()

            val url =
                URL("https://spreadsheets.google.com/feeds/list/$idSheet/1/public/values?alt=json")
            val urlConnection = url.openConnection() as HttpURLConnection
            try {
                _state.value = "CONNECTED"
                val da = BufferedInputStream(urlConnection.inputStream)
                val bufferedReader = BufferedReader(InputStreamReader(da) as Reader?)
                val stringBuilder = StringBuilder()
                bufferedReader.forEachLine {
                    stringBuilder.append(it)
                }
                val res =
                    JSONObject(stringBuilder.toString()).getJSONObject("feed").getJSONArray("entry")
                Log.d("adil", "recherche connexion  length=" + res.length())
                var mycol = mutableListOf<String>()
                for (i in 0 until res.length()) {
                    val obj: MutableMap<String, String> = mutableMapOf()
                    if (i == 0) {
                        res.getJSONObject(i).keys().forEach {
                            if (it.startsWith("gsx$") && !mycol.contains(it)) mycol.add(it)
                        }
                        mycol = mycol.map { it.replace("gsx$", "") }.toMutableList()
                        _col.value = mycol
                    }
                    //Log.d("adil", "i= $i  mycol = $mycol ")

                    var temp = ""
                    for (c in mycol) {
                        obj[c] = res.getJSONObject(i).getJSONObject("gsx$$c")["\$t"].toString()
                    }
                    myLocalList.add(obj)
                }
                //   Log.d("mourahi", "final = $myLocalList")
                _state.value = "SUCCES"
                _datasheet.value = myLocalList
            } catch (e: Exception) {
                Log.d("adil", "probleme recupereation depuis internet exception = " + e.message)
                _state.value = "Erreur Serveur"
                _datasheet.value = arrayListOf(mutableMapOf<String, String>())
            } finally {
                urlConnection.disconnect()
                Log.d("adil", "deconnnexion")
                _datasheet.value = myLocalList
            }
    }
}