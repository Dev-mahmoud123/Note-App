package com.mah_awad.notesapp.adapter;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mah_awad.notesapp.R;
import com.mah_awad.notesapp.entities.Notes;
import com.mah_awad.notesapp.listeners.NotesListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NotesViewHolder> {
    private List<Notes> notes;
    private NotesListener notesListener;
    private Timer timer;
    private List<Notes> noteSearch;

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, final int position) {
        holder.setNotes(notes.get(position));
        holder.layoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesListener.onNoteClicked(notes.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public NoteAdapter(List<Notes> notes, NotesListener notesListener) {
        this.notes = notes;
        this.notesListener = notesListener;
        noteSearch = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtSubTitle, txtDateTime;
        LinearLayout layoutNote;
        RoundedImageView imageNote;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.textTitle);
            txtSubTitle = itemView.findViewById(R.id.textSubtitle);
            txtDateTime = itemView.findViewById(R.id.textDateTime);
            layoutNote = itemView.findViewById(R.id.layoutNote);
            imageNote = itemView.findViewById(R.id.imageNote2);

        }

        void setNotes(Notes notes) {
            // display text
            txtTitle.setText(notes.getTitle());
            if (notes.getSubtitle().trim().isEmpty()) {
                txtSubTitle.setVisibility(View.GONE);
            } else {
                txtSubTitle.setText(notes.getSubtitle());
            }
            // display time and date
            txtDateTime.setText(notes.getDateTime());

            // display color
            GradientDrawable gradientDrawable = (GradientDrawable) layoutNote.getBackground();
            if (notes.getColor() != null) {
                gradientDrawable.setColor(Color.parseColor(notes.getColor()));
            } else {
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }
            // display image
            if (notes.getImagePath() != null) {
                imageNote.setImageBitmap(BitmapFactory.decodeFile(notes.getImagePath()));
                imageNote.setVisibility(View.VISIBLE);
            } else {
                imageNote.setVisibility(View.GONE);
            }
        }
    }

    public void searchNote(final String searchKeyboard) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchKeyboard.trim().isEmpty()) {
                    notes = noteSearch;
                } else {
                    ArrayList<Notes> temp = new ArrayList<>();
                    for (Notes note : noteSearch) {
                        if (note.getTitle().toLowerCase().contains(searchKeyboard.toLowerCase())
                                || note.getSubtitle().toLowerCase().contains(searchKeyboard.toLowerCase())
                                || note.getNoteText().toLowerCase().contains(searchKeyboard.toLowerCase())) {
                            temp.add(note);
                        }
                    }
                    notes = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }, 500);
    }

    public void cancelTimer(){
        if (timer != null){
            timer.cancel();
        }
    }
}
