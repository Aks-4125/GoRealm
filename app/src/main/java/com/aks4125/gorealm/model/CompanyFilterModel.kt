package com.aks4125.gorealm.model

import com.aks4125.gorealm.ui.main.MainActivity.Companion.COMPANY_FILTER_UUID
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CompanyFilterModel : RealmObject() {
    var ascending: Boolean = false
    var groupId: Int = 0
    @PrimaryKey
    var pID: String = COMPANY_FILTER_UUID

}