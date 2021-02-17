package com.formationsi.bigsi2021.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.formationsi.bigsi2021.R
import com.formationsi.bigsi2021.adapter.RecycleAdapterPhones.*
import com.formationsi.bigsi2021.phones.Phone

class RecycleAdapterPhones( val data: List<Phone>): RecyclerView.Adapter<RecycleAdapterPhones.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecycleAdapterPhones.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_phones,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecycleAdapterPhones.ViewHolder, position: Int) {
        val i = data.get(position)
        holder.txt_age.text = i.age.toString()
        holder.txt_name.text = i.name.toString()
        Log.d("adil","je suis dans bind")
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        lateinit var txt_name:TextView
        lateinit var txt_age:TextView

            init {
                 txt_name = v.findViewById<TextView>(R.id.txt_name)
                 txt_age = v.findViewById<TextView>(R.id.txt_age)
            }
    }
}