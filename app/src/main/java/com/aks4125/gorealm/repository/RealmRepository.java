package com.aks4125.gorealm.repository;

import android.util.Log;

import com.aks4125.gorealm.model.CompanyFilterModel;
import com.aks4125.gorealm.model.CompanyModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.aks4125.gorealm.utils.GoConstants.FIELD_CLAPS;
import static com.aks4125.gorealm.utils.GoConstants.FIELD_ID;
import static com.aks4125.gorealm.utils.GoConstants.FIELD_NAME;
import static com.aks4125.gorealm.utils.GoConstants.FILTER_BY_ID;
import static com.aks4125.gorealm.utils.GoConstants.FILTER_BY_NAME;


public class RealmRepository {


    /**
     * @param filterModel filter company objects
     */
    public void insertOrUpdateCompanyFilter(@NotNull CompanyFilterModel filterModel) {
        try (Realm mRealm = Realm.getDefaultInstance()) {
            CompanyFilterModel dbModel = mRealm.where(CompanyFilterModel.class).findFirst(); // it will have only single record
            if (dbModel != null) {
                Log.d("realm__", dbModel.getGroupId() + "------" + dbModel.getAscending());
                mRealm.executeTransaction(rlm -> {
                    dbModel.setAscending(filterModel.getAscending());
                    dbModel.setGroupId(filterModel.getGroupId());
                });
            } else
                mRealm.executeTransaction(rlm -> rlm.insertOrUpdate(filterModel));

        }
    }


    /**
     * @param mDataList company list to insert or update
     */
    public void insertOrUpdateCompanyList(List<CompanyModel> mDataList) {
        try (Realm mRealm = Realm.getDefaultInstance()) {
            mRealm.executeTransaction(rlm ->
                    mRealm.insertOrUpdate(mDataList));
        }
    }

    /**
     * @return true if no company data in database
     */
    public boolean isCompanyListEmpty() {
        try (Realm mRealm = Realm.getDefaultInstance()) {
            return mRealm.where(CompanyModel.class).findAll().isEmpty();
        }
    }

    /**
     * @return list of all companies
     */
    @NotNull
    public List<CompanyModel> getCompanyList() {
        try (Realm mRealm = Realm.getDefaultInstance()) {
            return mRealm.copyFromRealm(mRealm.where(CompanyModel.class).findAll()); // this will not empty in any case
        }
    }


    /**
     * @param filterModel filtering data for company
     * @return list of filtered companies
     */
    public List<CompanyModel> getFilteredList(@NotNull CompanyFilterModel filterModel) {
        List<CompanyModel> modelList;
        try (Realm mRealm = Realm.getDefaultInstance()) {
            RealmResults<CompanyModel> mQuery = mRealm.where(CompanyModel.class).findAll(); // in memory objects
            switch (filterModel.getGroupId()) {
                case FILTER_BY_ID:
                    mQuery = mQuery.sort(FIELD_ID, filterModel.getAscending() ? Sort.ASCENDING : Sort.DESCENDING); // in memory query, faster than db
                    break;
                case FILTER_BY_NAME:
                    mQuery = mQuery.sort(FIELD_NAME, filterModel.getAscending() ? Sort.ASCENDING : Sort.DESCENDING);
                    break;
                default:
                    mQuery = mQuery.sort(FIELD_CLAPS, filterModel.getAscending() ? Sort.ASCENDING : Sort.DESCENDING);
                    break;
            }
            modelList = new ArrayList<>(mRealm.copyFromRealm(mQuery));
        }
        return modelList;
    }

    /**
     * @param mCompany object to insert or update into realm
     */
    public void insertOrUpdateCompanyObject(@NotNull CompanyModel mCompany) {
        try (Realm mRealm = Realm.getDefaultInstance()) {
            mRealm.executeTransaction(rlm -> rlm.insertOrUpdate(mCompany));

        }
    }
}
