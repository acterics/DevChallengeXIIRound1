package it.devchallenge.snake.di.app

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseDatabaseModule {

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Singleton
    @Provides
    fun provideDatabaseDirectionReference(database: FirebaseDatabase): DatabaseReference {
        return database.reference.child("direction")
    }
}