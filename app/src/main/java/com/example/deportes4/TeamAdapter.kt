package com.example.deportes4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.example.deportes4.R

class TeamAdapter(
    private val teams: List<Team>,
    private val onItemClick: (Team) -> Unit // Add this parameter for item click handling
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teams[position])
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTeamName: TextView = itemView.findViewById(R.id.textTeamName)
        private val imageViewTeamLogo: ImageView = itemView.findViewById(R.id.imageViewTeamLogo)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(teams[position])
                }
            }
        }

        fun bind(team: Team) {
            textViewTeamName.text = team.strTeam

            // Load team logo with Glide
            Glide.with(itemView.context)
                .load("https://www.thesportsdb.com/images/media/league/fanart/${team.idTeam}.png/tiny")
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(imageViewTeamLogo)
        }
    }
}