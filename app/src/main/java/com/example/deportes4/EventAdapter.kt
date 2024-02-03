package com.example.deportes4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var events: List<Event>? = null

    fun setEvents(events: List<Event>?) {
        this.events = events
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events?.get(position)
        holder.bind(event)
    }

    override fun getItemCount(): Int {
        return events?.size ?: 0
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventTextView: TextView = itemView.findViewById(R.id.textEvent)

        fun bind(event: Event?) {
            eventTextView.text = "Event: ${event?.strEvent}\nDate: ${event?.dateEvent}\n\n"
        }
    }
}

