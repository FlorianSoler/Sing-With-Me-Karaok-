package fr.enssat.singwithme.soler_florian_duigou_gurvan.ui.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.enssat.singwithme.soler_florian_duigou_gurvan.R
import fr.enssat.singwithme.soler_florian_duigou_gurvan.ui.data.Song

class SongAdapter(private val songList: List<Song>) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.songName)
        val artistTextView: TextView = view.findViewById(R.id.songArtist)
        val lockedTextView: TextView = view.findViewById(R.id.songLocked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.song_item, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songList[position]
        holder.nameTextView.text = song.name
        holder.artistTextView.text = song.artist
        holder.lockedTextView.text = if (song.locked) "Locked" else "Unlocked"
    }

    override fun getItemCount() = songList.size
}
