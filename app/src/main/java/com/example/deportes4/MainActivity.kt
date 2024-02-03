package com.example.deportes4

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
    private lateinit var adapter: TeamAdapter
    private val teamsList = mutableListOf<Team>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewTeams)
        adapter = TeamAdapter(teamsList) { team ->
            // Handle item click, navigate to EventDetailsActivity for the selected team
            val intent = Intent(this@MainActivity, EventDetailsActivity::class.java)
            intent.putExtra("teamId", team.idTeam)
            startActivity(intent)
        }


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Retrofit initialization
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        // Fetch all teams in a league (example: Soccer in Spain)
        val call = api.getAllTeams("Soccer", "Spain")
        call.enqueue(object : Callback<TeamResponse> {
            override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                if (response.isSuccessful) {
                    val teamResponse = response.body()
                    teamsList.addAll(teamResponse?.teams ?: emptyList())
                    adapter.notifyDataSetChanged()
                } else {
                    // Handle error
                    Toast.makeText(this@MainActivity, "Failed to fetch teams. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@MainActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
        val btnViewEvents: Button = findViewById(R.id.btnViewEvents)
        btnViewEvents.setOnClickListener {
            // Navigate to EventDetailsActivity
            val intent = Intent(this@MainActivity, EventDetailsActivity::class.java)
            // Pass any necessary data using intent extras
            startActivity(intent)
        }
        val btnViewEventResults: Button = findViewById(R.id.btnViewEventResults)
        btnViewEventResults.setOnClickListener {
            // Navigate to EventResultsActivity
            val intent = Intent(this@MainActivity, EventResultsActivity::class.java)
            // Pass any necessary data using intent extras
            startActivity(intent)
        }
    }

}



