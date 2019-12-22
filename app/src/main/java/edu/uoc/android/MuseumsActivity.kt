package edu.uoc.android

import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import edu.uoc.android.models.Museums
import edu.uoc.android.rest.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_museums.*


class MuseumsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museums)


        //if (recyclerView!=null) Log.d("PEC3", ""+recyclerView);
        //else Log.d("PEC3", "recycler es null");

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        myRecyclerViewForMuseums!!.setHasFixedSize(true)

        // use a linear layout manager
        myRecyclerViewForMuseums!!.layoutManager = LinearLayoutManager(this)

        showProgress(true)
        getMuseums()

    }

    protected fun showProgress(show: Boolean) {
        this.myProgressBar!!.visibility = if (show) View.VISIBLE else View.GONE
    }


    private fun getMuseums() {
        val museumAPI = RetrofitFactory.getMuseumAPI()

        showProgress(true)
        val callList = museumAPI.museums("1", "5")

        callList.enqueue(object : Callback<Museums> {
            override fun onResponse(call: Call<Museums>, response: Response<Museums>) {
                val statusCode = response.code()
                if (response.isSuccessful) {
                    val museums = response.body()
                    showProgress(false)
                    myRecyclerViewForMuseums!!.adapter = MyMuseumsRecyclerViewAdapter(museums!!.elements)
                }
            }

            override fun onFailure(call: Call<Museums>, t: Throwable) {
                Log.d("PEC3", "  onFAILURE!!!!: msg:" + t.message)
            }
        })
    }


}
