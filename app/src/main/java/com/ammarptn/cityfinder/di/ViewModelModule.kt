package com.ammarptn.cityfinder.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import java.io.InputStream
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    @Named("CITY_JSON_DATA_STREAM")
    fun provideDataStream(@ApplicationContext context: Context): InputStream {
        return context.assets.open("cities.json")
    }
}