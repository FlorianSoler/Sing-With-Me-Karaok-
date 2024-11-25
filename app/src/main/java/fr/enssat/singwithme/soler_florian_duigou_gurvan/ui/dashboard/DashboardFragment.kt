package fr.enssat.singwithme.soler_florian_duigou_gurvan.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import fr.enssat.singwithme.soler_florian_duigou_gurvan.databinding.FragmentDashboardBinding
import fr.enssat.singwithme.soler_florian_duigou_gurvan.ui.adapter.SongAdapter
import fr.enssat.singwithme.soler_florian_duigou_gurvan.ui.data.Song
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        var dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        // Manually create a list of songs
        val fakeSongs = listOf(
            Song(name = "Hey Jude", artist = "The Beatles", locked = true),
            Song(name = "Wake Me Up When September Ends", artist = "Green Day", locked = true),
            Song(name = "Bohemian Rhapsody", artist = "Queen", locked = false)
        )

        lifecycleScope.launch {
            // Fetch and wait for the result
            dashboardViewModel.fetchAndParseJSON()
        }

        // Setup RecyclerView
        val recyclerView = binding.songRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = SongAdapter(fakeSongs)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
