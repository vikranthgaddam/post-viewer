package com.example.postviewersapp

import android.R
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.postviewersapp.databinding.ActivityMainBinding
import com.example.postviewersapp.models.PostData
import android.widget.ArrayAdapter
import com.example.postviewersapp.models.UIData


private const val TAG="MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel:MainViewModel
   private lateinit var binding:ActivityMainBinding
    private lateinit var postsAdapter: PostsAdapter
    private val displayingPostsOfUser = mutableListOf<PostData>()
    private val totalPostInData= mutableListOf<PostData>()
    private val totalUsers= mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        postsAdapter = PostsAdapter(this, displayingPostsOfUser)
        binding.rvPosts.adapter = postsAdapter
        binding.rvPosts.layoutManager = LinearLayoutManager(this)


    }

    private fun initUI() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.fetchPosts()
        viewModel.userPosts.observe(this, { posts ->
            displayingPostsOfUser.clear()
            displayingPostsOfUser.addAll(posts)
            postsAdapter.notifyDataSetChanged()
            binding.userPosts.text="Selected User Posts ${displayingPostsOfUser.size}"
            binding.rvPosts.visibility=View.VISIBLE
            binding.userPosts.visibility=View.VISIBLE
        })
        viewModel.totalPosts.observe(this,{ item->
            totalPostInData.clear()
            totalPostInData.addAll(item)
            val numElements = totalPostInData.size
            binding.uniqueUsers.text= "Total number of Unique Users"+":${totalPostInData.distinctBy{it.userId}.size}"
            val uniqueUsersList=totalPostInData.distinctBy{it.userId}
            for(iterator in uniqueUsersList)
            {
            totalUsers.add(iterator.userId)
            }

            postsAdapter.notifyDataSetChanged()
            binding.rvPosts.smoothScrollToPosition(numElements)
            binding.numberOfPosts.text="Total number of posts : ${totalPostInData.size}"
            binding.fetchedData= UIData(totalPostInData.distinctBy{it.userId}.size,totalPostInData.size)
            val  arrayAdapter = ArrayAdapter(
                this,
                R.layout.simple_list_item_1, totalUsers
            )
            arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinner1.adapter = arrayAdapter

        })


        var selectedUser=0
        binding.spinner1.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedUser= parent.getItemAtPosition(position) as Int
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //TODO
            }
        }
        binding.button.setOnClickListener{
            viewModel.userSpecificPost(selectedUser)
        }

    }


}