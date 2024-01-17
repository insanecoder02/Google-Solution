package com.example.google_solution.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.google_solution.FragmentAdapter
import com.example.google_solution.R
import com.example.google_solution.databinding.ActivityGetStartedBinding
import com.google.android.material.tabs.TabLayout

class GetStarted : AppCompatActivity() {
    private lateinit var binding:  ActivityGetStartedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("first"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("second"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("third"))

        binding.viewPager.adapter = FragmentAdapter(supportFragmentManager,lifecycle)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })

    }
}