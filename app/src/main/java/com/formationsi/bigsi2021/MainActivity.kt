package com.formationsi.bigsi2021

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.formationsi.bigsi2021.adapter.AdapterTabs
import com.formationsi.bigsi2021.others.OtherActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var  drawerLayout:DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawerlayout)
        val actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled =true
        actionBarDrawerToggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_drawer)
        navigationView.setNavigationItemSelectedListener(this)

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
        viewPager2.isUserInputEnabled = false // disable swip of tabs

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.id_menu_other ->{
                Log.d("adil"," other activitie")
                val intent = Intent(this, OtherActivity::class.java)
                startActivity(intent)
            }
            else -> Log.d("adil", "home encore ")
        }
        return true
    }
}