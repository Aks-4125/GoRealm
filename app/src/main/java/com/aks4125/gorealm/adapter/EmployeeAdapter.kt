package com.aks4125.gorealm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aks4125.gorealm.R
import com.aks4125.gorealm.model.Employees
import kotlinx.android.synthetic.main.list_item_company.view.tvAddress
import kotlinx.android.synthetic.main.list_item_employee.view.*

class EmployeeAdapter(private val empList: MutableList<Employees>) :
    RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHolder {
        return EmployeeHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_employee, parent, false)
        )
    }

    override fun getItemCount() = empList.size


    override fun onBindViewHolder(holder: EmployeeHolder, position: Int) {
        holder.bind(empList[position])
    }

    inner class EmployeeHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name = view.tvName
        private val age = view.tvAge
        private val address = view.tvAddress
        private val department = view.tvEmpDeptartment
        private val emptyView = view.emptyViewEmp


        fun bind(data: Employees) {
            if (adapterPosition == itemCount - 1)
                emptyView.visibility = View.VISIBLE
            else
                emptyView.visibility = View.GONE


            name.text = data.name
            age.text = itemView.context.getString(R.string.emp_age, data.age)
            address.text = data.address
            department.text = itemView.context.getString(R.string.emp_dept, data.department)

        }
    }
}