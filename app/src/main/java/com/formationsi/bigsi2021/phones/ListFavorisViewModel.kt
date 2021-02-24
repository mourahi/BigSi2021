package com.formationsi.bigsi2021.phones

import android.app.Application
import android.content.ContentResolver
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListFavorisViewModel : ViewModel() {
    var mylistphone = MutableLiveData<List<NumerPhone>>()
    // TODO: Implement the ViewModel

    fun prepareLocalContacts(application: Application?) {
        GlobalScope.launch(Dispatchers.IO) {
            val listphone = mutableListOf<NumerPhone>()

            val cr: ContentResolver? = application?.contentResolver
            val cur = cr?.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null
            )
            Log.d("adil", "count = ${cur?.count}")


            if (cur?.count ?: 0 > 0) {
                while (cur != null && cur.moveToNext()) {
                    val id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID)
                    )
                    val name = cur.getString(
                        cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME
                        )
                    )
                    if (cur.getInt(
                            cur.getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER
                            )
                        ) > 0
                    ) {
                        val pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                        )
                        while (pCur!!.moveToNext()) {
                            val phoneNo = pCur.getString(
                                pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                            )
                            listphone.add(NumerPhone(name, phoneNo))
                            GlobalScope.launch(Dispatchers.Main) { mylistphone.value = listphone }

                        }
                        pCur.close()
                    }
                }
            }
            cur?.close()
//GlobalScope.launch(Dispatchers.Main) { mylistphone.value = listphone }

        }
    }

}