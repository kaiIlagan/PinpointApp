package com.example.pinpointapp.dependencyinjection

import com.backendless.Backendless
import com.backendless.Persistence
import com.example.pinpointapp.data.repository.BackendlessDataSourceImplementation
import com.example.pinpointapp.data.repository.RepositoryImplementation
import com.example.pinpointapp.domain.repository.BackendlessDataSource
import com.example.pinpointapp.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    // apart of Backendless SDK to communicate with Backendless Database
    @Provides
    @Singleton
    fun provideBackendless(): Persistence {
        return Backendless.Data
    }

    @Provides
    @Singleton
    fun provideBackendlessDataSource(backendless: Persistence): BackendlessDataSource {
        return BackendlessDataSourceImplementation(backendless = backendless)
    }

    @Provides
    @Singleton
    fun provideRepository(backendlessDataSource: BackendlessDataSource): Repository {
        return RepositoryImplementation(backendlessDataSource = backendlessDataSource)
    }
}