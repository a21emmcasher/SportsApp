package com.example.deportes4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), EventAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter
    private val eventsList = mutableListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = EventAdapter(eventsList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Retrofit initialization
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        // Example team ID and season (replace with actual values)
        val teamId = 4328
        val season = "2014-2015"

        // Fetching events for a specific season
        val call = api.getSeasonEvents(teamId, season)
        call.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val eventResponse = response.body()
                    eventsList.addAll(eventResponse?.events ?: emptyList())
                    adapter.notifyDataSetChanged()
                } else {
                    // Handle error
                    Toast.makeText(this@MainActivity, "Failed to fetch events. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.e("MainActivity", "API call failed with code: ${response.code()}")
                    Log.e("MainActivity", "Error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@MainActivity, "Network error", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "API call failed", t)
            }
        })

    }
    override fun onItemClick(event: Event) {
        // Handle item click here
        val intent = Intent(this@MainActivity, EventDetailsActivity::class.java)
        intent.putExtra("eventId", event.idEvent) // Assuming idEvent is the unique identifier for the event
        startActivity(intent)
    }

}


