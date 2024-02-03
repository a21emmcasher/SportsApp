package com.example.deportes4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventResultsAdapter : RecyclerView.Adapter<EventResultsAdapter.ViewHolder>() {

    private var eventResults: List<EventResults>? = null

    fun setData(eventResults: List<EventResults>?) {
        this.eventResults = eventResults
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val result1TextView: TextView = itemView.findViewById(R.id.textResult1)
        val result2TextView: TextView = itemView.findViewById(R.id.textResult2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eventResult = eventResults?.get(position)

        holder.result1TextView.text = "Result 1: ${eventResult?.result1}"
        holder.result2TextView.text = "Result 2: ${eventResult?.result2}"
    }

    override fun getItemCount(): Int {
        return eventResults?.size ?: 0
    }
}
