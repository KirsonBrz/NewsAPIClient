package com.kirson.newsapiclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kirson.newsapiclient.data.util.Resource
import com.kirson.newsapiclient.databinding.FragmentNewsBinding
import com.kirson.newsapiclient.presentation.adapter.NewsAdapter
import com.kirson.newsapiclient.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var fragmentNewsBinding: FragmentNewsBinding
    private var country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0


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
        newsAdapter = (activity as MainActivity).newsAdapter

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment,
                bundle
            )
        }

        initRecyclerView()
        viewNewsList()
        setSearchView()

    }


    private fun viewNewsList() {

        viewModel.getNewsHeadLines(country, page)
        viewModel.newsHeadLines.observe(
            viewLifecycleOwner, Observer { response ->

                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let {
                            newsAdapter.differ.submitList(it.articles.toList())
                            pages = if (it.totalResults % 20 == 0) {
                                it.totalResults / 20
                            } else {
                                it.totalResults / 20 + 1
                            }
                            isLastPage = page == pages
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
        // newsAdapter = NewsAdapter()
        fragmentNewsBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }


    }

    private fun showProgressBar() {
        isLoading = true
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }


    private fun hideProgressBar() {
        isLoading = false
        fragmentNewsBinding.progressBar.visibility = View.GONE
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = fragmentNewsBinding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if (shouldPaginate) {
                page++
                viewModel.getNewsHeadLines(country, page)
                isScrolling = false
            }

        }
    }


    private fun setSearchView() {
        fragmentNewsBinding.svNews.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {


                override fun onQueryTextSubmit(p0: String?): Boolean {
                    viewModel.getSearchedNews(country, p0.toString(), page)
                    viewSearchedNews()
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    MainScope().launch {
                        delay(4000)
                        viewModel.getSearchedNews(country, p0.toString(), page)
                        viewSearchedNews()
                    }
                    return false
                }


            })

        fragmentNewsBinding.svNews.setOnCloseListener {
            viewNewsList()
            false
        }


    }


    //search
    fun viewSearchedNews() {

        viewModel.searchedNews.observe(
            viewLifecycleOwner, Observer { response ->

                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let {
                            newsAdapter.differ.submitList(it.articles.toList())
                            pages = if (it.totalResults % 20 == 0) {
                                it.totalResults / 20
                            } else {
                                it.totalResults / 20 + 1
                            }
                            isLastPage = page == pages
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


}