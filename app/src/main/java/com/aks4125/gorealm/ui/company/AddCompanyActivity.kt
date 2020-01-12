package com.aks4125.gorealm.ui.company

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.aks4125.gorealm.R
import com.aks4125.gorealm.model.CompanyModel
import com.aks4125.gorealm.repository.RealmRepository
import kotlinx.android.synthetic.main.activity_add_company.*
import org.koin.android.ext.android.inject

class AddCompanyActivity : AppCompatActivity() {

    // injecting repository
    private val realmRepository: RealmRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_company)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        btnAdd.setOnClickListener {

            if (validate()) {
                val mCompany = CompanyModel()

                mCompany.id = edtCompanyId.text.toString().toInt()
                mCompany.name = edtCompanyName.text.toString()
                mCompany.address = edtCompanyAddress.text.toString()
                mCompany.claps = edtCompanyClaps.text.toString().toInt()
                mCompany.empCount = edtCompanyEmpCount.text.toString().toInt()

                // considering replace scenario if user enters the same ID for a company.
                // i will update here after unit testing if time permits
                realmRepository.insertOrUpdateCompanyObject(mCompany)
                showDialog()
            }

        }


    }

    /**
     * upon success of adding company
     */
    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Success")
            setMessage("${edtCompanyName.text.toString()} has been added Successfully.")
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            show()
        }
    }

    /**
     * company validations
     */
    private fun validate(): Boolean {
        inputId.error = null
        inputName.error = null
        inputAddress.error = null
        inputClaps.error = null
        when {
            edtCompanyId.text.isNullOrEmpty() -> {
                inputId.error = getString(R.string.valid_id)
                return false
            }
            edtCompanyName.text.isNullOrEmpty() -> {
                inputName.error = getString(R.string.valid_name)
                return false
            }
            edtCompanyEmpCount.text.isNullOrEmpty() -> {
                inputEmpCount.error = getString(R.string.valid_count)
                return false
            }
            edtCompanyAddress.text.isNullOrEmpty() -> {
                inputAddress.error = getString(R.string.valid_address)
                return false
            }
            edtCompanyClaps.text.isNullOrEmpty() -> {
                inputClaps.error = getString(R.string.valid_claps)
                return false
            }
            else -> return true
        }
    }
}
