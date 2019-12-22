package edu.uoc.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.squareup.picasso.Picasso

import edu.uoc.android.models.Element

/**
 */
class MyMuseumsRecyclerViewAdapter(private val mValues: List<Element>) : RecyclerView.Adapter<MyMuseumsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_museums, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mIdView.text = mValues[position].adrecaNom
        Picasso.get().load(holder.mItem!!.imatge[0]).into(holder.mLogoView)


        /*        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });*/
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView
        val mLogoView: ImageView
        var mItem: Element? = null

        init {
            mIdView = mView.findViewById<View>(R.id.item_number) as TextView
            mLogoView = mView.findViewById<View>(R.id.logoMuseum) as ImageView
        }

        override fun toString(): String {
            return super.toString() + " '" + mIdView.text + "'"
        }
    }
}
