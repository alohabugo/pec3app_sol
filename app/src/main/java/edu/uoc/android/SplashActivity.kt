package edu.uoc.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import edu.uoc.android.baas.FirebaseContactManager
import edu.uoc.android.models.Contact

class SplashActivity : AppCompatActivity(), ValueEventListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        FirebaseContactManager.getInstance().getContactFromServer(this)
    }

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        for (contact in dataSnapshot.children) {
            FirebaseContactManager.getInstance().addContactHashMap(contact.getValue(Contact::class.java))
        }
        startMainActivity()
    }

    override fun onCancelled(databaseError: DatabaseError) {
        startMainActivity()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        startActivity(Intent(this@SplashActivity, MainActivity::class.java).apply{
        })

        finish()
    }
}
