package com.aks4125.gorealm

data class CompanyModel(
    val id: Int,
    val empCount: Int,
    val name: String,
    val address: String,
    val claps: Int,
    val employees: List<Employees>
)

data class Employees(
    val id: Int,
    val name: String,
    val age: Int,
    val address: String,
    val department: String
)