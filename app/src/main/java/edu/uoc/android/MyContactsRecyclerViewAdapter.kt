package edu.uoc.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.squareup.picasso.Picasso

import edu.uoc.android.models.Contact

/**
 * [RecyclerView.Adapter] that can display a [Contact] and makes a call to the
 *
 * TODO: Replace the implementation with code for your data type.
 */
class MyContactsRecyclerViewAdapter(private val mValues: List<Contact>) : RecyclerView.Adapter<MyContactsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_contacts, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mContentView.text = mValues[position].name

        Picasso.get().load(holder.mItem!!.imageUrl).into(holder.mAvatar)


    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView
        val mAvatar: ImageView

        var mItem: Contact? = null

        init {
            mContentView = mView.findViewById<View>(R.id.content) as TextView
            mAvatar = mView.findViewById<View>(R.id.avatar) as ImageView
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
