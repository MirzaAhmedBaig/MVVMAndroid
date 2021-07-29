package com.mab.mvvmandroid

import android.app.Application
import com.mab.mvvmandroid.data.db.AppDatabase
import com.mab.mvvmandroid.data.network.ApiClient
import com.mab.mvvmandroid.data.network.NetworkConnectionInterceptor
import com.mab.mvvmandroid.data.repositories.UserRepository
import com.mab.mvvmandroid.ui.auth.AuthViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * Created by Mirza Ahmed Baig on 8/22/2020.
 * mirza@avantari.org
 * Avantari Technologies
 */

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiClient(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
    }


}