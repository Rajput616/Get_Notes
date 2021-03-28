package com.example.notes.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.notes.models.Note;

import java.util.List;

public class NoteRepository {
    private NoteDatabase mNoteDatabase;

    private NoteRepository(Context context){
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note){

    }
    public void updateNote(Note note){

    }
    public LiveData<List<Note>> retrieveNotesTask(){
        return null;
    }
    public void deleteNote(Note note){

    }

}
