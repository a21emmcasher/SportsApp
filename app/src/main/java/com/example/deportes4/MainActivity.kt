package com.example.deportes4

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

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter
    private val eventsList = mutableListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = EventAdapter(eventsList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Retrofit initialization
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        // Fetching last events
        val call = api.getLastEvents(4328)
        call.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val eventResponse = response.body()
                    Log.d("MainActivity", "API Response: $eventResponse")

                    eventsList.addAll(eventResponse?.events ?: emptyList())
                    adapter.notifyDataSetChanged()

                    Log.d("MainActivity", "Number of events: ${eventsList.size}")
                } else {
                    // Handle error
                    Toast.makeText(this@MainActivity, "Failed to fetch events", Toast.LENGTH_SHORT).show()
                    Log.e("MainActivity", "API call failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@MainActivity, "Network error", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "API call failed", t)
            }
        })
    }
}
