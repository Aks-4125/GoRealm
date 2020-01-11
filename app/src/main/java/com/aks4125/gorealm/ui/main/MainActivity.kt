package com.aks4125.gorealm.ui.main

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.RadioGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.aks4125.gorealm.R
import com.aks4125.gorealm.adapter.CompanyAdapter
import com.aks4125.gorealm.model.CompanyFilterModel
import com.aks4125.gorealm.model.CompanyModel
import com.aks4125.gorealm.ui.AddCompanyActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    MainContractor.IMainView {
    companion object {
        const val COMPANY_FILTER_UUID = "c_id"
        const val FILTER_BY_ID = 1
        const val FILTER_BY_CLAPS = 2
        const val FILTER_BY_NAME = 3
        const val FIELD_ID = "id"
        const val FIELD_NAME = "name"
        const val FIELD_CLAPS = "claps"
    }

    private lateinit var mCompanyAdapter: CompanyAdapter
    private var mCompanyList: MutableList<CompanyModel> = arrayListOf()
    private val mainPresenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mCompanyAdapter =
            CompanyAdapter(mCompanyList)

        rvCompany.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mCompanyAdapter
            mCompanyAdapter.onItemClick = { mCompany: CompanyModel ->
                mainPresenter.insertOrUpdateCompanyModel(mCompany)
            }
        }

        mainPresenter.processJson(getString(R.string.sampleJson))

        btnShowFilter.setOnClickListener {
            showDialog()
        }
        mSearchView.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                mCompanyAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })
        btnAddCompany.setOnClickListener {
            mSearchView.setQuery("", true)
            startActivity(Intent(this@MainActivity, AddCompanyActivity::class.java))
            overridePendingTransition(0, 0)
        }

    }

    private fun showDialog() {
        val filterModel = CompanyFilterModel()
        val dialog = Dialog(this@MainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_filter)
        val group = dialog.findViewById(R.id.filterGroup) as RadioGroup
        val btnApply = dialog.findViewById(R.id.btnApplyFilter) as Button
        val orderSwitch = dialog.findViewById(R.id.orderSwitch) as SwitchCompat

        btnApply.setOnClickListener {
            val id = group.checkedRadioButtonId
            var groupId = 0
            when (id) {
                R.id.radioCompanyId -> {
                    groupId =
                        FILTER_BY_ID
                }
                R.id.radioClaps -> {
                    groupId =
                        FILTER_BY_CLAPS
                }
                R.id.radioCompanyName -> {
                    groupId =
                        FILTER_BY_NAME
                }
            }
            filterModel.ascending = orderSwitch.isChecked
            filterModel.groupId = groupId
            mainPresenter.filterData(filterModel)
            dialog.dismiss()
        }
        dialog.show()

    }

    override fun updateList(mutableList: MutableList<CompanyModel>) {
        mCompanyList.clear()
        mCompanyList.addAll(mutableList)
        mCompanyAdapter.updateList(mutableList)
    }


}
