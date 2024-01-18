package com.example.google_solution.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.google_solution.Fragment.Intro1
import com.example.google_solution.Fragment.Intro2
import com.example.google_solution.Fragment.Intro3
import com.example.google_solution.R
import com.example.google_solution.databinding.ActivityGetStartedBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class GetStarted : AppCompatActivity() {
    private lateinit var binding: ActivityGetStartedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentList = arrayListOf<Fragment>(
            Intro1(),
            Intro2(),
            Intro3()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter

        val indicator = findViewById<DotsIndicator>(R.id.dots_indicator)
        indicator.attachTo(binding.viewPager)

        binding.next.setOnClickListener {
            if (binding.viewPager.currentItem == 2) {
//                startActivity(Intent(this@GetStarted, Auth::class.java))
            }
            binding.viewPager.currentItem = binding.viewPager.currentItem + 1
        }
    }

    class ViewPagerAdapter(
        private val fragmentList: ArrayList<Fragment>,
        fm: FragmentManager,
        lifecycle: Lifecycle
    ) :
        FragmentStateAdapter(fm, lifecycle) {

        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }
}