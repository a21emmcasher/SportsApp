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
            // Fetch events for the selected team and season
            fetchEventsForTeamAndSeason(4328, "2014-2015")
        } else {
            // Handle invalid teamId
            Toast.makeText(this, "Invalid team ID", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fetchEventsForTeamAndSeason(teamId: Int, season: String) {
        val api = retrofit.create(ApiService::class.java)

        // Make API call to get events for the specific team and season
        val call = api.getEventsForSeason(teamId, season)
        call.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.events

                    // Update UI with fetched events
                    updateUI(events)
                } else {
                    // Handle error
                    Toast.makeText(this@EventDetailsActivity, "Failed to fetch events. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@EventDetailsActivity, "Network error", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun updateUI(events: List<Event>?) {
        // Update UI with real events for the selected team and season
        // Modify as needed based on the structure of the API response
        eventAdapter.setEvents(events)

        // Add more TextViews or other UI elements to display additional details
    }
}
