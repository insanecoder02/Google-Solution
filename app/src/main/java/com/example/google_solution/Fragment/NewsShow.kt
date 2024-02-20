package com.example.google_solution.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import com.example.google_solution.R

class NewsShow : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = view.findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true

        val args = arguments
        val articleUrl = args?.getString("article_url")

        val back = view.findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        if (!articleUrl.isNullOrEmpty()) {
            webView.webViewClient = WebViewClient()
            webView.loadUrl(articleUrl)
        }
    }
}