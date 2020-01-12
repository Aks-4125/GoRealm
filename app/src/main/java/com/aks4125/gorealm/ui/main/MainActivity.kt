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
import com.aks4125.gorealm.ui.company.AddCompanyActivity
import com.aks4125.gorealm.utils.GoConstants.Companion.FILTER_BY_CLAPS
import com.aks4125.gorealm.utils.GoConstants.Companion.FILTER_BY_ID
import com.aks4125.gorealm.utils.GoConstants.Companion.FILTER_BY_NAME
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


/**
 * This class is only responsible to intract Presenter Layer & View Layer
 * No Database related calls are done here
 */
class MainActivity : AppCompatActivity(),
    MainContractor.IMainView {

    private lateinit var filterModel: CompanyFilterModel
    private lateinit var mCompanyAdapter: CompanyAdapter
    private var mCompanyList: MutableList<CompanyModel> = arrayListOf()
    private val mainPresenter: MainContractor.IMainPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        mainPresenter.processJson(getString(R.string.sampleJson))

    }

    /**
     * initialize objects & UI
     */
    private fun initUI() {
        mCompanyAdapter =
            CompanyAdapter(mCompanyList)
        filterModel = CompanyFilterModel()
        filterModel.groupId = FILTER_BY_ID

        rvCompany.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mCompanyAdapter
            mCompanyAdapter.onItemClick = { mCompany: CompanyModel ->
                mainPresenter.insertOrUpdateCompanyModel(mCompany)
            }
        }

        // listeners
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
            mainPresenter.setupRealmChangeListener(filterModel)
            mSearchView.setQuery("", true)
            startActivity(Intent(this@MainActivity, AddCompanyActivity::class.java))
            overridePendingTransition(0, 0)
        }
    }


    override fun onResume() {
        super.onResume()
        mainPresenter.removeRealmListener()
    }

    /**
     * filter dialog
     */
    private fun showDialog() {
        filterModel = CompanyFilterModel()
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

    /**
     * updates from presenter layer to display list on view layer
     */
    override fun updateList(mutableList: MutableList<CompanyModel>) {
        mCompanyList.clear()
        mCompanyList.addAll(mutableList)
        mCompanyAdapter.updateList(mutableList)
    }

}
