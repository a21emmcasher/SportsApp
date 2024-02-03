package com.example.deportes4

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        recyclerView = findViewById(R.id.recyclerViewEvents)
        eventAdapter = EventAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = eventAdapter

        val teamId = 4328
        if (teamId != null) {
            fetchEventsForTeamAndSeason(4328, "2014-2015")
        } else {
            Toast.makeText(this, "Invalid team ID", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fetchEventsForTeamAndSeason(teamId: Int, season: String) {
        val api = retrofit.create(ApiService::class.java)

        val call = api.getEventsForSeason(teamId, season)
        call.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.events
                    updateUI(events)
                } else {
                    Toast.makeText(this@EventDetailsActivity, "Failed to fetch events. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Toast.makeText(this@EventDetailsActivity, "Network error", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun updateUI(events: List<Event>?) {
        // Update UI with real events for the selected team and season
        eventAdapter.setEvents(events)

    }
}
