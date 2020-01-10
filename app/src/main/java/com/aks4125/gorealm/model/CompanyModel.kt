package com.aks4125.gorealm.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CompanyModel(
    @PrimaryKey
    var id: Int? = null,
    var empCount: Int? = null,
    var name: String? = null,
    var address: String? = null,
    var claps: Int? = null,
    var employees: RealmList<Employees> = RealmList()
) : RealmObject()


open class Employees(
    @PrimaryKey
    var id: Int? = null,
    var name: String? = null,
    var age: Int? = null,
    var address: String? = null,
    var department: String? = null
) : RealmObject()