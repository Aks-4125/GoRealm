package com.aks4125.gorealm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_company.view.*


class CompanyAdapter(private val modelList: List<CompanyModel>) :
    RecyclerView.Adapter<CompanyAdapter.CompanyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyHolder {
        return CompanyHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_company, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CompanyHolder, position: Int) {
        holder.bind(modelList[position])
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    inner class CompanyHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name = view.name
        private val tvEmpCount = view.tvEmpCount
        private val tvAddress = view.tvAddress
        private val btnClaps = view.btnClaps

        fun bind(data: CompanyModel) {
            name.text = data.name
            tvEmpCount.text = itemView.context.getString(R.string.total_emp, data.empCount)
            tvAddress.text = data.address
            btnClaps.text = data.claps.toString()
        }
    }

}
