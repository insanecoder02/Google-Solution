package com.example.google_solution.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.google_solution.Fragment.Intro1
import com.example.google_solution.Fragment.Intro2
import com.example.google_solution.Fragment.Intro3
import com.example.google_solution.R
import com.example.google_solution.databinding.ActivityGetStartedBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class GetStarted : AppCompatActivity() {
    private lateinit var binding: ActivityGetStartedBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        window.statusBarColor = 0xFF2DCC70.toInt()

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        if (isFirstTimeLaunch()) {
            setupIntroViewPager()
        } else {
            startNextActivity()
        }
    }

    private fun setupIntroViewPager() {
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

        val lastPageIndex = fragmentList.size - 1
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == lastPageIndex) {
                    binding.next.text = "Let's Get Started"
                } else {
                    binding.next.text = "Next"
                }
            }
        })

        binding.next.setOnClickListener {
            if (binding.viewPager.currentItem == lastPageIndex) {
                markFirstTimeLaunch()
                startNextActivity()
            }
            binding.viewPager.currentItem = binding.viewPager.currentItem + 1
        }
    }

    private fun startNextActivity() {
        startActivity(Intent(this@GetStarted, Auth::class.java))
        finish()
    }

    private fun isFirstTimeLaunch(): Boolean {
        return sharedPreferences.getBoolean("isFirstTimeLaunch", true)
    }

    private fun markFirstTimeLaunch() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isFirstTimeLaunch", false)
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