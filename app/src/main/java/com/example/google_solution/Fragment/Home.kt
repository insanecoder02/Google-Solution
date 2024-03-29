package com.example.google_solution.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.google_solution.R
import com.example.google_solution.adapter.NewsAdapter
import com.example.google_solution.viewmodel.HomeViewModel
import com.example.google_solution.databinding.FragmentHomeBinding
import com.example.google_solution.dataclass.Article
import com.example.google_solution.dataclass.NewsResponse
import com.example.google_solution.utils.NewsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var newsAdapter: NewsAdapter
    private var articles: MutableList<Article> = mutableListOf()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.showText.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_event)
        }

        binding.ishantFabButtonx.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_fabButt)
        }



        binding.newsRv.layoutManager = LinearLayoutManager(requireContext())
        newsAdapter = NewsAdapter(requireContext(), articles)
        binding.newsRv.adapter = newsAdapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val newsAPI = retrofit.create(NewsAPI::class.java)
        val apiKey = "efd7144b4c2c4bd496b6723defa72d6e"
        val call = newsAPI.getTopHeadlines(apiKey, "environment")
        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    articles = response.body()?.articles?.take(10)?.toMutableList() ?: mutableListOf()
                    // Update the adapter with the new data
                    newsAdapter.updateData(articles)
                    binding.lottieAnimationView.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.newsRv.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT).show()
                binding.lottieAnimationView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.newsRv.visibility = View.GONE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}