package com.aks4125.gorealm.ui

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.aks4125.gorealm.R
import com.aks4125.gorealm.adapter.CompanyAdapter
import com.aks4125.gorealm.model.CompanyFilterModel
import com.aks4125.gorealm.model.CompanyModel
import com.aks4125.gorealm.repository.RealmRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {

        const val COMPANY_FILTER_UUID = "c_id"
        const val FILTER_BY_ID = 1
        const val FILTER_BY_CLAPS = 2
        const val FILTER_BY_NAME = 3

    }

    private lateinit var mCompanyAdapter: CompanyAdapter
    private lateinit var mCompanyList: List<CompanyModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val turnsType = object : TypeToken<List<CompanyModel>>() {}.type
        mCompanyList =
            Gson().fromJson<List<CompanyModel>>(getString(R.string.sampleJson), turnsType)


        mCompanyAdapter =
            CompanyAdapter(mCompanyList)
        rvCompany.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mCompanyAdapter
        }

        btnShowFilter.setOnClickListener {
            showDialog()
        }

    }

    private fun showDialog() {
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
            val filterModel = CompanyFilterModel()
            filterModel.ascending = orderSwitch.isChecked
            filterModel.groupId = groupId


            RealmRepository.getRealm().insertOrUpdateCompanyFilter(filterModel)

            dialog.dismiss()
        }
        dialog.show()

    }


}
