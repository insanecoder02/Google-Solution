package com.example.google_solution.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.google_solution.adapter.ShopAdapter
import com.example.google_solution.viewmodel.ShopViewModel
import com.example.google_solution.databinding.FragmentShopBinding
import com.example.google_solution.dataclass.Shop
import com.google.firebase.firestore.FirebaseFirestore

class Shop : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private var shop:MutableList<Shop> = mutableListOf()
    private lateinit var adapter: ShopAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ShopViewModel::class.java)

        _binding = FragmentShopBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ShopAdapter(requireContext(),shop)

        binding.shopRv.adapter = adapter
        binding.shopRv.layoutManager = GridLayoutManager(requireContext(), 2)

        fetchFromFirestore()
    }

    private fun fetchFromFirestore() {
        binding.logLot.visibility = View.VISIBLE
        shop.clear()
        val db = FirebaseFirestore.getInstance()
        db.collection("shop").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val tit = document.getString("title") ?: ""
                val price = document.getString("price") ?: ""
                val date = document.getString("date") ?: ""
                val img = document.getString("image") ?: ""
                shop.add(Shop(price, tit, date, img))
            }
            adapter.notifyDataSetChanged() // Notify the adapter that the data set has changed
            binding.logLot.visibility = View.GONE
        }.addOnFailureListener { exception ->
            binding.logLot.visibility = View.GONE
            Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}