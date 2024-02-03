package com.example.deportes4

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val eventId = intent.getIntExtra("event_id", 0)

        // Retrofit initialization
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        // Fetch event details
        val call = api.getEventDetails(eventId)
        call.enqueue(object : Callback<EventDetailsResponse> {
            override fun onResponse(call: Call<EventDetailsResponse>, response: Response<EventDetailsResponse>) {
                if (response.isSuccessful) {
                    val eventDetails = response.body()?.events?.firstOrNull()
                    if (eventDetails != null) {
                        // Bind data to the views in the layout
                        findViewById<TextView>(R.id.eventNameTextView).text = eventDetails.strEvent
                        findViewById<TextView>(R.id.eventDescriptionTextView).text = eventDetails.strDescriptionEN
                        // Add more bindings as needed
                    }
                } else {
                    // Handle error
                    Toast.makeText(this@EventDetailActivity, "Failed to fetch event details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EventDetailsResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@EventDetailActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
