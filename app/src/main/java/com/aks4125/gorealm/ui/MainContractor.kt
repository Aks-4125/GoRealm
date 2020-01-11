package com.aks4125.gorealm.ui

import com.aks4125.gorealm.model.CompanyFilterModel
import com.aks4125.gorealm.model.CompanyModel

interface MainContractor {
    interface IMainView {
        fun updateList(mutableList: MutableList<CompanyModel>)
    }

    interface IMainPresenter {
        fun processJson(json: String)
        fun filterData(filterModel: CompanyFilterModel)
    }

}