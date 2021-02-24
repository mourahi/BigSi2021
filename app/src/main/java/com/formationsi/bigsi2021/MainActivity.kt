package com.formationsi.bigsi2021

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.formationsi.bigsi2021.adapter.AdapterTabs
import com.formationsi.bigsi2021.db.SchoolRepository
import com.formationsi.bigsi2021.others.OtherActivity
import com.formationsi.bigsi2021.phones.ListPhonesViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.function.IntConsumer


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var repository: SchoolRepository
    lateinit var myviewmodel: ListPhonesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        repository = (application as SchoolApplication).goodrepository

        myviewmodel = ViewModelProvider(this).get(ListPhonesViewModel::class.java)
        myviewmodel.getshcools().observe(this, {
            Log.d("adil", "it=  $it")
        })


        drawerLayout = findViewById(R.id.drawerlayout)
        val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        actionBarDrawerToggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_drawer)
        navigationView.setNavigationItemSelectedListener(this)

        val adapterTabs = AdapterTabs(this)
        val viewPager2: ViewPager2 = findViewById(R.id.home_viewpager)
        viewPager2.adapter = adapterTabs
        val tablayout = findViewById<TabLayout>(R.id.home_tab)
        TabLayoutMediator(tablayout, viewPager2) { tab, pos ->
            when (pos) {
                1 -> tab.text = "Favoris"
                2 -> tab.text = "Groups"
                else -> tab.text = "Phones"
            }
        }.attach()
        viewPager2.isUserInputEnabled = false // disable swip of tabs

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            //ok
        } else {
            requestPermission();
        }
    }

    val REQUEST_READ_CONTACTS = 10
    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_CONTACTS
            )
        ) {
            // show UI part if you want here to show some rationale !!!
        } else {

            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_READ_CONTACTS
            )
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_CONTACTS
            )
        ) {
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_READ_CONTACTS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_READ_CONTACTS -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // mobileArray = getAllContacts()
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.id_menu_other -> {
                val intent = Intent(this, OtherActivity::class.java)
                startActivity(intent)
            }
            else -> Log.d("adil", "home encore ")
        }
        return true
    }
}