package com.example.todo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.todo.NoteApp
import com.example.todo.feature_note.data.data_source.NoteDatabase
import com.example.todo.feature_note.data.repository.NoteRepositoryImplementation
import com.example.todo.feature_note.domain.repository.NoteRepository
import com.example.todo.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImplementation(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCase {
        return NoteUseCase(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNotes = AddNotes(repository),
            getNote = GetNote(repository)
        )
    }
}