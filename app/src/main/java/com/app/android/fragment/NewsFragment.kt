package com.app.android.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.android.adapter.NewsListAdapter
import com.app.android.api.RetrofitService
import com.app.android.api.WeatherRetrofitService
import com.app.android.databinding.FragmentNewsBinding
import com.app.android.repository.MainRepository
import com.app.android.viewModel.MainViewModel
import com.app.android.viewModel.ViewModelFactory

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsViewModel: MainViewModel
    private lateinit var newsListAdapter: NewsListAdapter

    private val retrofitService = RetrofitService.getInstance()
    private val weatherRetrofitService = WeatherRetrofitService.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        initView()
        return binding.root

    }

    private fun initView(){
        binding.progressBar.visibility = View.VISIBLE
        //get viewModel instance using ViewModelProvider.Factory
        newsViewModel = ViewModelProvider(this, ViewModelFactory(MainRepository(retrofitService,weatherRetrofitService))).get(MainViewModel::class.java)


        //set adapter in recyclerview
        newsListAdapter = NewsListAdapter()


        newsViewModel.getNewsList()
        getNewsListResponse()

        binding.newsRecyclerview.adapter = newsListAdapter


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed in this case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed in this case
            }

            override fun afterTextChanged(s: Editable?) {
                searchNews(s.toString())
            }
        })

    }



    private fun getNewsListResponse(){
        //the observer will only receive events if the owner(activity) is in active state
        //invoked when newsList data changes
        newsViewModel.newsList.observe(viewLifecycleOwner) {
            Log.d(TAG, "newsList: $it")
            binding.progressBar.visibility = View.GONE
            if (it != null) {

                newsListAdapter.setNewsList(it.results)
            }

        }

        //invoked when a network exception occurred
        newsViewModel.errorMessage.observe(viewLifecycleOwner) {
            Log.d(TAG, "errorMessage: $it")
        }
    }


    private fun searchNews(search: String) {
        val filteredList = newsViewModel.newsList.value?.results?.filter {
            it.title.contains(search, ignoreCase = true)
        }
        newsListAdapter.setNewsList(filteredList ?: emptyList())
    }
}