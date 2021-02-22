package com.formationsi.bigsi2021.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.formationsi.bigsi2021.R
import com.formationsi.bigsi2021.adapter.RecycleAdapterPhones.MyViewHolder
import com.formationsi.bigsi2021.db.School

class RecycleAdapterPhones( private val mylist:List<School>) : RecyclerView.Adapter<MyViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder.create(parent)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val current = mylist[position]
            holder.bind(current)
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val name_school: TextView = itemView.findViewById(R.id.txt_name_school_favoris)
            private val name_director: TextView = itemView.findViewById(R.id.txt_name_director_favoris)
            private val num_phone: TextView = itemView.findViewById(R.id.txt_num_phone)


            fun bind(school: School) {
                name_school.text = school.name_school
                name_director.text = school.name_director
                num_phone.text = school.num_phone
            }

            companion object {
                fun create(parent: ViewGroup): MyViewHolder {
                    val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_recycle_phones, parent, false)
                    return MyViewHolder(view)
                }
            }
        }

    override fun getItemCount(): Int {
        return mylist.size
    }
}