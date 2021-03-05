package com.formationsi.bigsi2021

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
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

/*fun isConnected(context: Context): Boolean {
    var result = false
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                result = true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                result = true
            }
        }
    } else {
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                result = true
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                result = true
            }
        }
    }
    return result
}*/

object MyConnection : LiveData<Boolean>() {

    private var broadcastReceiver: BroadcastReceiver? = null
    private lateinit var application: Application

    fun init(application: Application) {
        this.application = application
    }

    fun isInternetOn(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    override fun onActive() {
        registerBroadCastReceiver()
    }

    override fun onInactive() {
        unRegisterBroadCastReceiver()
    }

    private fun registerBroadCastReceiver() {
        if (broadcastReceiver == null) {
            val filter = IntentFilter()
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)

            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(_context: Context, intent: Intent) {
                    val extras = intent.extras
                    val info = extras!!.getParcelable<NetworkInfo>("networkInfo")
                    value = info!!.state == NetworkInfo.State.CONNECTED
                }
            }

            application.registerReceiver(broadcastReceiver, filter)
        }
    }

    private fun unRegisterBroadCastReceiver() {
        if (broadcastReceiver != null) {
            application.unregisterReceiver(broadcastReceiver)
            broadcastReceiver = null
        }
    }
}

 fun getJSONArrayFromInternet(numpage:Int = 1,
    idSheet: String = "1F49X3Jo823vUJ9hrr1vheCeCI2LhCIN_gf9sxMrgK5k"
): MutableLiveData<ArrayList<MutableMap<String, String>>> {
    Log.d("adimou", "getJSONArrayFromInternet")
    //_state.value = "START"
    var myLocalList: ArrayList<MutableMap<String, String>> = ArrayList()
    val livedataMyLocalList = MutableLiveData<ArrayList<MutableMap<String, String>>>()
    val url =
        URL("https://spreadsheets.google.com/feeds/list/$idSheet/$numpage/public/values?alt=json")
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
                myLocalList = arrayListOf()
                livedataMyLocalList.value = myLocalList
            }
        } finally {
            urlConnection.disconnect()
            Log.d("adil", "deconnnexion")
            GlobalScope.launch(Dispatchers.Main) {
                livedataMyLocalList.value = myLocalList
            }
        }
    }
    return livedataMyLocalList
}