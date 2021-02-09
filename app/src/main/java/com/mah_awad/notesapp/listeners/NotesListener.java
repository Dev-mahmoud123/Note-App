package com.mah_awad.notesapp.listeners;

import com.mah_awad.notesapp.entities.Notes;

public interface NotesListener {
    void onNoteClicked(Notes notes , int position);
}
