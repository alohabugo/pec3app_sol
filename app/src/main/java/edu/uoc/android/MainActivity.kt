package edu.uoc.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*;


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.rellay_maps.setOnClickListener {
            val i = Intent(this@MainActivity, MapsActivity::class.java)
            startActivity(i)
        }

        this.rellay_contacts.setOnClickListener {
            val i = Intent(this@MainActivity, ContactsActivity::class.java)
            startActivity(i)
        }

        this.rellay_schools.setOnClickListener {
            Log.d("PEC3", "SCHOOLS")
            val i = Intent(this@MainActivity, MuseumsActivity::class.java)
            startActivity(i)
        }
        this.rellay_settings.setOnClickListener{
            Log.d("PEC3", "SETTING");
            val i = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(i)
        }

    }
}
