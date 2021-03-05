package com.formationsi.bigsi2021.local

import android.app.Application
import android.content.ContentResolver
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.formationsi.bigsi2021.SchoolApplication
import com.formationsi.bigsi2021.db.School
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListLocalPhonesViewModel (application: Application) : AndroidViewModel(application) {
   // private val myrepository = (application as SchoolApplication).goodrepository
    var listLocalPhones = MutableLiveData<List<School>>()
    var templistLocalPhones = MutableLiveData<List<School>>()
    var compteurLocalContact = MutableLiveData<String>()


    fun getLocalContacts(txtsearch: String = "") {
        listLocalPhones.value = templistLocalPhones.value!!.filter {
            it.nom.contains(txtsearch)
        }
    }

    fun prepareLocalContacts(application: Application?) {
        var i = 0
        var j = 0
        val nbrLocalContact: Int
        val listphone = mutableListOf<School>()
        var lastdata = ""
        val cr: ContentResolver? = application?.contentResolver
        val cur = cr?.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        nbrLocalContact = cur?.count!!

        if (nbrLocalContact > 0) {
            while (cur.moveToNext()) {
                val id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))

                val name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )

                    while (pCur!!.moveToNext()) {
                        //  val idd = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID)).toInt()
                        var phoneNo =
                            pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        Log.d("adil", "phoneNo = $phoneNo")
                        phoneNo = phoneNo.replace("+212", "0")
                            .replace("(", "")
                            .replace(")", "")
                            .replace("-", "").replace(" ", "")
                        if (lastdata != phoneNo + name) {
                            lastdata = phoneNo + name
                            i += 1

                            listphone.add(School(name, phoneNo))
                            GlobalScope.launch(Dispatchers.Main) {
                                if (i == 10) {
                                    i = 0
                                    listLocalPhones.value = listphone
                                }
                                compteurLocalContact.value = " $j / $nbrLocalContact"
                                j += 1
                            }
                        }
                    }
                    pCur.close()
                }
            }
        }
        cur.close()
        GlobalScope.launch(Dispatchers.Main) {
            listLocalPhones.value = listphone
            templistLocalPhones.value = listphone
        }

    }

}