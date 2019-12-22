package edu.uoc.android

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.uoc.android.baas.FirebaseContactManager
import kotlinx.android.synthetic.main.activity_contacts.*

class ContactsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        myRecyclerView4Contacts!!.setHasFixedSize(true)

        // use a linear layout manager
        myRecyclerView4Contacts!!.layoutManager = LinearLayoutManager(this)

        val contactList = FirebaseContactManager.getInstance().allContacts
        myRecyclerView4Contacts!!.adapter = MyContactsRecyclerViewAdapter(contactList)


    }


}
