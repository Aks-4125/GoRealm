package com.aks4125.gorealm.di

import com.aks4125.gorealm.repository.RealmRepository
import com.aks4125.gorealm.ui.main.MainContractor
import com.aks4125.gorealm.ui.main.MainPresenter
import org.koin.dsl.module


val applicationModule = module {

    single<MainContractor.IMainPresenter> { (view: MainContractor.IMainView) ->
        MainPresenter(
            view,
            get()
        )
    }

    single { RealmRepository() }
}