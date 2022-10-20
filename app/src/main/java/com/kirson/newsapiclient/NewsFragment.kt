package com.kirson.newsapiclient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kirson.newsapiclient.data.util.Resource
import com.kirson.newsapiclient.databinding.FragmentNewsBinding
import com.kirson.newsapiclient.presentation.adapter.NewsAdapter
import com.kirson.newsapiclient.presentation.viewmodel.NewsViewModel


class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var fragmentNewsBinding: FragmentNewsBinding
    private var country = "ru"
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsBinding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        initRecyclerView()
        viewNewsList()

    }


    private fun viewNewsList() {

        viewModel.getNewsHeadLines(country, page)
        viewModel.newsHeadLines.observe(
            viewLifecycleOwner, Observer { response ->

                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let {
                            Log.i("MYTAG", it.articles.toString())
                            newsAdapter.differ.submitList(it.articles.toList())
                        }

                    }
                    is Resource.Loading -> {
                        showProgressBar()

                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let {
                            Toast.makeText(activity, "An error occured: $it", Toast.LENGTH_LONG)
                                .show()
                        }

                    }
                }


            })


    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter()
        fragmentNewsBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }


    }

    private fun showProgressBar() {
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }


    private fun hideProgressBar() {
        fragmentNewsBinding.progressBar.visibility = View.INVISIBLE
    }


}