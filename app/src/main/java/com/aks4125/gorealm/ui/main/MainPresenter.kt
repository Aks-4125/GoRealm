package com.aks4125.gorealm.ui.main

import com.aks4125.gorealm.model.CompanyFilterModel
import com.aks4125.gorealm.model.CompanyModel
import com.aks4125.gorealm.repository.RealmRepository
import com.aks4125.gorealm.ui.main.MainContractor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainPresenter(val mainView: MainContractor.IMainView) : MainContractor.IMainPresenter {


    private val mRepository = RealmRepository.getRealm()
    private var mCompanyList: MutableList<CompanyModel> = arrayListOf()

    override fun processJson(json: String) {
        if (mRepository.isCompanyListEmpty) {
            val turnsType = object : TypeToken<MutableList<CompanyModel>>() {}.type
            mCompanyList =
                Gson().fromJson<MutableList<CompanyModel>>(
                    json,
                    turnsType
                )
            RealmRepository.getRealm().insertOrUpdateCompanyList(mCompanyList)

        } else {
            mCompanyList.addAll(RealmRepository.getRealm().companyList)
        }
        mainView.updateList(mCompanyList)
    }

    override fun filterData(filterModel: CompanyFilterModel) {
        mRepository.insertOrUpdateCompanyFilter(filterModel)
        mainView.updateList(mRepository.getFilteredList(filterModel))
    }

    override fun insertOrUpdateCompanyModel(mCompany: CompanyModel) {
        mRepository.insertOrUpdateCompanyObject(mCompany)
    }


}