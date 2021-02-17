package com.formationsi.bigsi2021

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.formationsi.bigsi2021.adapter.AdapterTabs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout:DrawerLayout = findViewById(R.id.drawerlayout)
        val actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled =true
        actionBarDrawerToggle.syncState()

        val adapterTabs = AdapterTabs(this)
        val viewPager2:ViewPager2 = findViewById(R.id.home_viewpager)
        viewPager2.adapter = adapterTabs
        val tablayout = findViewById<TabLayout>(R.id.home_tab)
        TabLayoutMediator(tablayout,viewPager2){ tab, pos ->
            when(pos){
                1-> tab.text = "Favoris"
                2-> tab.text = "Groups"
                else -> tab.text = "Phones"
            }
        }.attach()


    }
}