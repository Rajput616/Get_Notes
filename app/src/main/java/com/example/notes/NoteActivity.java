package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.notes.models.Note;

public class NoteActivity extends AppCompatActivity {
    private static final String TAG = "NoteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        if(getIntent().hasExtra("selected_node")){
            Note note = getIntent().getParcelableExtra("selected_node");
            Log.d(TAG, "onCreate: "+ note.toString());
        }
    }
}