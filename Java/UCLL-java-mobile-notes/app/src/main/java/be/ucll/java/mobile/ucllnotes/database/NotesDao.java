package be.ucll.java.mobile.ucllnotes.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import be.ucll.java.mobile.ucllnotes.model.Note;

// DAO = Data Access Object ofwel interface met lijstje van ondersteunde CRUDS operaties
@Dao
public interface NotesDao {

    /* --- Operations on 1 single Entity --- */
    // CREATE
    @Insert
    long insertNote(Note note);

    // READ
    @Query("SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.COLUMN_NAME_ID + " = :id LIMIT 1")
    Note getNote(long id);

    // UPDATE
    @Update
    void updateNote(Note repos);

    // DELETE
    @Delete
    void deleteNote(Note note);

    /* --- Operations involving a list of Entities --- */
    // SEARCH all notes. Return ordered on Creation date desc. Last Note on top of list.
    @Query("SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + Constants.COLUMN_NAME_DATE + " DESC")
    List<Note> getAllNotes();

    // DELETE a list of notes
    @Delete
    void deleteNotes(Note... note);

    // DELETE all notes
    @Query("DELETE FROM " + Constants.TABLE_NAME)
    void deleteAll();

}
