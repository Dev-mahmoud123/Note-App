package com.mah_awad.notesapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mah_awad.notesapp.dao.NoteDao;
import com.mah_awad.notesapp.entities.Notes;

@Database(entities = Notes.class, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase noteDatabase;

    public abstract NoteDao noteDao();

    // make singleton pattern not to make more instances from the same database
    // one instance can make that
    // because when use database not make new instance
    // synchronized because if there is many threads not enter with others
    public static synchronized NoteDatabase getDatabase(Context context) {
        if (noteDatabase == null) {
            noteDatabase = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "notes_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return noteDatabase;
    }


}
