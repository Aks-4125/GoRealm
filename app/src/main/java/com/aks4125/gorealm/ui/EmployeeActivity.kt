package com.aks4125.gorealm.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aks4125.gorealm.R
import com.aks4125.gorealm.adapter.EmployeeAdapter
import com.aks4125.gorealm.model.CompanyFilterModel
import com.aks4125.gorealm.model.Employees
import com.aks4125.gorealm.repository.RealmRepository
import com.aks4125.gorealm.utils.GoConstants
import com.aks4125.gorealm.utils.GoConstants.Companion.FIELD_ID
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_employee.*
import org.koin.android.ext.android.inject

class EmployeeActivity : AppCompatActivity() {

    private lateinit var mEmpList: RealmList<Employees>
    private lateinit var mAdapter: EmployeeAdapter
    private lateinit var filterModel: CompanyFilterModel
    private val mRepository: RealmRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)
        initUI()
    }

    private fun initUI() {
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val mCompanyId = intent!!.getIntExtra(FIELD_ID, 0)
        val mCompany = mRepository.getCompanyById(mCompanyId)
        mEmpList = mCompany.employees
        if (mEmpList.isEmpty()) {
            Toast.makeText(this, getString(R.string.no_record), Toast.LENGTH_SHORT).show()

        }
        mAdapter =
            EmployeeAdapter(mCompany.employees)
        filterModel = CompanyFilterModel()
        filterModel.groupId = GoConstants.FILTER_BY_ID

        rvEmployees.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter

        }

    }
}
