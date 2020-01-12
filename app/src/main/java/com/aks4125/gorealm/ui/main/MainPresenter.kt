package com.aks4125.gorealm.ui.main

import com.aks4125.gorealm.model.CompanyFilterModel
import com.aks4125.gorealm.model.CompanyModel
import com.aks4125.gorealm.repository.RealmRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.realm.Realm
import io.realm.RealmResults

class MainPresenter(
    private val mainView: MainContractor.IMainView,
    private val mRepository: RealmRepository
) : MainContractor.IMainPresenter {

    private lateinit var realmInstance: Realm
    private lateinit var companyListener: Unit
    private lateinit var companyResult: RealmResults<CompanyModel>
    private var mCompanyList: MutableList<CompanyModel> = arrayListOf()

    /**
     * insert json (companies) single time only
     */
    override fun processJson(json: String) {
        if (mRepository.isCompanyListEmpty) {
            val turnsType = object : TypeToken<MutableList<CompanyModel>>() {}.type
            mCompanyList =
                Gson().fromJson<MutableList<CompanyModel>>(
                    json,
                    turnsType
                )
            mRepository.insertOrUpdateCompanyList(mCompanyList)

        } else {
            mCompanyList.addAll(mRepository.companyList)
        }
        mainView.updateList(mCompanyList)
    }

    /**
     * filter and update ui
     */
    override fun filterData(filterModel: CompanyFilterModel) {
        mRepository.insertOrUpdateCompanyFilter(filterModel)
        mainView.updateList(mRepository.getFilteredList(filterModel))
    }

    /**
     * insert or update company object in database
     */
    override fun insertOrUpdateCompanyModel(mCompany: CompanyModel) {
        mRepository.insertOrUpdateCompanyObject(mCompany)
    }

    /**
     * remove change listener as no need to listen anymore changes in database
     */
    override fun removeRealmListener() {
        if (::realmInstance.isInitialized) {
            realmInstance.removeAllChangeListeners()
            realmInstance.close()
        }
    }

    /**
     * only register listener when user navigates to add new company
     * listener removed @onResume when user has successfully added company, no need to listen further updates
     */
    override fun setupRealmChangeListener(filterModel: CompanyFilterModel) {
        realmInstance = Realm.getDefaultInstance()
        companyResult = realmInstance.where(CompanyModel::class.java).findAll()
        //updating list without resetting filter
        companyListener = companyResult.addChangeListener { _, _ ->
            filterData(filterModel)
        }
    }

}