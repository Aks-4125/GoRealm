package com.aks4125.gorealm.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.aks4125.gorealm.R
import com.aks4125.gorealm.model.CompanyModel
import com.aks4125.gorealm.ui.EmployeeActivity
import com.aks4125.gorealm.utils.GoConstants.Companion.FIELD_ID
import kotlinx.android.synthetic.main.list_item_company.view.*


class CompanyAdapter(val context: Context, private val modelList: MutableList<CompanyModel>) :
    RecyclerView.Adapter<CompanyAdapter.CompanyHolder>(), Filterable {
    var filterList = modelList

    private val customFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            filterList =
                modelList.filter {
                    it.name!!.contains(constraint, true)
                }.toMutableList()



            return FilterResults().apply {
                values = filterList
                count = filterList.size
            }
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter = customFilter
    override fun getItemCount() = filterList.size

    /**
     * diffutils to submit updates
     */
    fun notifyChanges(oldList: MutableList<CompanyModel>, newList: MutableList<CompanyModel>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size
        })
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyHolder {
        return CompanyHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_company, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CompanyHolder, position: Int) {
        holder.bind(filterList[position])
    }

    fun updateList(mList: MutableList<CompanyModel>) {
        with(filterList) {
            clear()
            addAll(mList)
            notifyDataSetChanged()
        }
    }

    var onItemClick: ((CompanyModel) -> Unit)? = null

    inner class CompanyHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name = view.name
        private val tvEmpCount = view.tvEmpCount
        private val tvAddress = view.tvAddress
        private val btnClaps = view.btnClaps
        private val imgHeart = view.imageHeart
        private val emptyView = view.emptyView
        private var mVector: AnimatedVectorDrawable? = null
        private var mVectorCompat: AnimatedVectorDrawableCompat? = null
        private val mParent = view.mParent


        fun bind(data: CompanyModel) {
            if (adapterPosition == itemCount - 1)
                emptyView.visibility = View.VISIBLE
            else
                emptyView.visibility = View.GONE


            name.text = data.name
            tvEmpCount.text = itemView.context.getString(R.string.total_emp, data.empCount)
            tvAddress.text = data.address
            btnClaps.text = data.claps.toString()

            val heartDrawable = imgHeart.drawable

            btnClaps.setOnClickListener {
                //RealmRecyclerViewAdapter alternative but requires managed objects
                filterList[adapterPosition].claps = filterList[adapterPosition].claps!! + 1
                notifyItemChanged(adapterPosition)
                onItemClick?.invoke(filterList[adapterPosition])

                imgHeart.alpha = 0.75f
                if (heartDrawable is AnimatedVectorDrawable) {
                    mVector = heartDrawable
                    mVector?.start()
                } else if (heartDrawable is AnimatedVectorDrawableCompat) {
                    mVectorCompat = heartDrawable
                    mVectorCompat?.start()
                }

            }
            mParent.setOnClickListener {
                context.startActivity(
                    Intent(
                        context,
                        EmployeeActivity::class.java
                    ).putExtra(FIELD_ID, filterList[adapterPosition].id)
                )
            }


        }
    }

}
