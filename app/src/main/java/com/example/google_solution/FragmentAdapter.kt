package com.example.google_solution

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.google_solution.Fragment.firstFrag
import com.example.google_solution.Fragment.secFrag
import com.example.google_solution.Fragment.thirFrag

class FragmentAdapter(
    lifecycle: FragmentManager, fragmentManager: Lifecycle
) : FragmentStateAdapter(lifecycle, fragmentManager) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                firstFrag()
            }

            1 -> {
                secFrag()
            }

            else -> {
                thirFrag()
            }
        }
    }

}