package com.example.newsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.FullArticle
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.models.Articles
import com.example.newsapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_top_headlines.*

@AndroidEntryPoint
class TopHeadlines : AppCompatActivity(), NewsAdapter.ClickHandle {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_headlines)

        viewModel.getNewsFromRepo("TopHeadlines")

        pbTopHeadlines.visibility= View.VISIBLE
        rvTopHeadlines.layoutManager= LinearLayoutManager(this)
        val adapter= NewsAdapter(listOf(),this)
        rvTopHeadlines.adapter= adapter
        viewModel.newsItems.observe(this, {
            pbTopHeadlines.visibility = View.GONE
            if (it.isNotEmpty()) {
                adapter.newsList = it
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(
                        this, "Something went wrong. Check your internet connection.",
                        Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onReadMoreClick(currentArticle: Articles) {
        val intent= Intent(this, FullArticle::class.java).apply {
            putExtra("CurrentArticle", currentArticle)
        }
        startActivity(intent)
    }
}