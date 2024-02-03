package com.example.deportes4

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventResultsActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_results)

        recyclerView = findViewById(R.id.recyclerViewEventResults)
        adapter = EventResultsAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val eventId = 652890
        if (eventId != null) {
            // Fetch event results for the selected event
            fetchEventResults(eventId)
        } else {
            // Handle invalid eventId
            Toast.makeText(this, "Invalid event ID", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fetchEventResults(eventId: Int) {
        val api = retrofit.create(ApiService::class.java)

        // Make API call to get event results for the specific event
        val call = api.getEventResults(eventId)
        call.enqueue(object : Callback<EventResultsResponse> {
            override fun onResponse(call: Call<EventResultsResponse>, response: Response<EventResultsResponse>) {
                if (response.isSuccessful) {
                    val eventResults = response.body()?.results

                    // Update RecyclerView adapter with fetched event results
                    adapter.setData(eventResults)
                } else {
                    // Handle error
                    Toast.makeText(this@EventResultsActivity, "Failed to fetch event results. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<EventResultsResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@EventResultsActivity, "Network error", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }
}
