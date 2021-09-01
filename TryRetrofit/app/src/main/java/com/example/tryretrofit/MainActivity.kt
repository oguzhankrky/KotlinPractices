package com.example.tryretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.tryretrofit.databinding.ActivityMainBinding
import com.example.tryretrofit.model.Ex2Item
import com.example.tryretrofit.service.DataAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


import com.squareup.picasso.Picasso

import java.net.URL



class MainActivity : AppCompatActivity() {
    private val BaseUrl="https://api.github.com/users/hadley/"
    private var exModels:ArrayList<Ex2Item>?=null
    var tex: String= ""
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()
        DataBinding()

    }
    private fun DataBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }
    private fun loadData(){
        val retrofit= Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service=retrofit.create(DataAPI::class.java)
        val call =service.getData()

        call.enqueue(object : Callback<List<Ex2Item>> {
            override fun onFailure(call: Call<List<Ex2Item>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Ex2Item>>, response: Response<List<Ex2Item>>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        exModels = ArrayList(it)
                        if (exModels != null){
                            for (ornekModel: Ex2Item in exModels!!) {

                                binding.apply {
                                    binding.textView.text = ornekModel.avatarUrl
                                    Picasso.get().load(ornekModel.avatarUrl).into(imageView)

                                }


                            }
                    }
                    }
                }
            }

        })



}


}