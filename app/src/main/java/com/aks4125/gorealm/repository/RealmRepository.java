package com.aks4125.gorealm.repository;

import android.util.Log;

import com.aks4125.gorealm.model.CompanyFilterModel;

import org.jetbrains.annotations.NotNull;

import io.realm.Realm;

public class RealmRepository {

    private static RealmRepository realmRepository;

    public static RealmRepository getRealm() {
        if (realmRepository == null)
            realmRepository = new RealmRepository();

        return realmRepository;
    }

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


}
