package com.aks4125.gorealm.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aks4125.gorealm.R
import kotlinx.android.synthetic.main.activity_add_company.*

class AddCompanyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_company)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        btnAdd.setOnClickListener {

            if (validate()) {
                Toast.makeText(this, "valid", Toast.LENGTH_SHORT).show()
            }

        }


    }

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
