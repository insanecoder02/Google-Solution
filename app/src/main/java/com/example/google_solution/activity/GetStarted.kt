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

        window.statusBarColor = 0xFF2DCC70.toInt()

        if (isFirstTimeLaunch()) {
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
                    // Mark that the app has been launched once
                    markFirstTimeLaunch()
                    startActivity(Intent(this@GetStarted, Auth::class.java))
                    finish() // Close the current activity after launching Auth activity
                }
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            }
        } else {
            // If not the first time launch, directly start Auth activity
            startActivity(Intent(this@GetStarted, Auth::class.java))
            finish() // Close the current activity after launching Auth activity
        }
    }

    private fun isFirstTimeLaunch(): Boolean {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        return sharedPreferences.getBoolean("firstTimeLaunch", true)
    }

    private fun markFirstTimeLaunch() {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("firstTimeLaunch", false)
        editor.apply()
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