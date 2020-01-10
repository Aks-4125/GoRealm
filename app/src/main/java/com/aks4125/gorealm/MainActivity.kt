package com.aks4125.gorealm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mCompanyAdapter: CompanyAdapter
    private lateinit var mCompanyList: List<CompanyModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val turnsType = object : TypeToken<List<CompanyModel>>() {}.type
        mCompanyList = Gson().fromJson<List<CompanyModel>>(getString(R.string.sampleJson), turnsType)


        mCompanyAdapter = CompanyAdapter(mCompanyList)
        rvCompany.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mCompanyAdapter
        }




    }
}
