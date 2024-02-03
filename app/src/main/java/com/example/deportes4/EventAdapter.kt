package com.example.deportes4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private val events: List<Event>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(event: Event) {
            // Bind data to the views in the item layout
            itemView.findViewById<TextView>(R.id.eventNameTextView).text = event.strEvent
            // Add more bindings as needed
        }
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(events[position])
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(event: Event)
    }
}

