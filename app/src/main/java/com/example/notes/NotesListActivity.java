package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.notes.adapters.NotesRecyclerAdapter;
import com.example.notes.models.Note;
import com.example.notes.persistence.NoteRepository;
import com.example.notes.util.VerticalSpacingItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity implements
        NotesRecyclerAdapter.OnNoteListener,
        View.OnClickListener {

    private static final String TAG = "NotesListActivity";
//  UI components
    private RecyclerView mRecyclerView;

    //vars
    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNotesRecyclerAdapter;
    private NoteRepository mNoteRepository; // Creating Note Repository Object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        mRecyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.fab).setOnClickListener(this);
        mNoteRepository = new NoteRepository(this);

        initRecyclerView();
//        insertFakeNotes();
        retrieveNotes(); // return list of all the notes


        setSupportActionBar(findViewById(R.id.notes_toolbar));
        setTitle("Notes");
    }
    private void retrieveNotes(){
        mNoteRepository.retrieveNotesTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if(mNotes.size() > 0){
                    mNotes.clear();
                }
                if(notes != null){
                    mNotes.addAll(notes);
                }
                mNotesRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void insertFakeNotes(){
        for(int i = 0; i < 1000; i++){
            Note note = new Note();
            note.setTitle("Title" + i);
            note.setContent("Content #" + i);
            note.setTimestamp("Mar 2021");
            mNotes.add(note);
        }
        mNotesRecyclerAdapter.notifyDataSetChanged();  // Very important to update list
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(5);
        mRecyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerView);

        mNotesRecyclerAdapter = new NotesRecyclerAdapter(mNotes, this);
        mRecyclerView.setAdapter(mNotesRecyclerAdapter);
    }

    @Override
    public void onNoteClick(int position) {
//        Log.d(TAG, "onNoteClick: Clicked" + position);
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("selected_note", mNotes.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    private void deleteNote(Note note){
        mNotes.remove(note);
        Toast.makeText(this, note.getTitle() + " removed", Toast.LENGTH_SHORT).show();
        mNotesRecyclerAdapter.notifyDataSetChanged();
    }
    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
    };
}