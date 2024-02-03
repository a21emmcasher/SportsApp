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

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val eventId = intent.getIntExtra("eventId", -1)
        if (eventId != -1) {
            // Fetch event details using the eventId and display them in the activity
            fetchEventDetails(eventId)
        } else {
            // Handle invalid eventId
            Toast.makeText(this, "Invalid event ID", Toast.LENGTH_SHORT).show()
            finish() // Finish the activity if the eventId is invalid
        }
    }

    private fun fetchEventDetails(eventId: Int) {
        val api = retrofit.create(ApiService::class.java)

        // Make API call to get details for the specific event
        val call = api.getEventDetails(eventId)
        call.enqueue(object : Callback<EventDetailsResponse> {
            override fun onResponse(call: Call<EventDetailsResponse>, response: Response<EventDetailsResponse>) {
                if (response.isSuccessful) {
                    val eventDetails = response.body()?.events?.get(0)

                    // Update UI with fetched details
                    updateUI(eventDetails)
                } else {
                    // Handle error
                    Toast.makeText(this@EventDetailsActivity, "Failed to fetch event details. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<EventDetailsResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@EventDetailsActivity, "Network error", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun updateUI(eventDetails: EventDetails?) {
        val titleTextView: TextView = findViewById(R.id.textEventTitle)
        val descriptionTextView: TextView = findViewById(R.id.textEventDescription)

        // Update UI with real event details
        titleTextView.text = eventDetails?.strEvent ?: "Event Title Not Available"
        descriptionTextView.text = eventDetails?.strDescriptionEN ?: "Event Description Not Available"

        // Add more TextViews or other UI elements to display additional details
    }
}
