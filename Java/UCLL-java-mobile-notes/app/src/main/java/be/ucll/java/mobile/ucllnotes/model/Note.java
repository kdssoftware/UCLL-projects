package be.ucll.java.mobile.ucllnotes.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import be.ucll.java.mobile.ucllnotes.database.Constants;

@Entity(tableName = Constants.TABLE_NAME)
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.COLUMN_NAME_ID)
    private Long id;

    @ColumnInfo(name = Constants.COLUMN_NAME_TITLE)
    private String title;

    // Als je geen @ColumnInfo specificeert dan neemt hij de veldnaam hieronder ook als kolomnaam
    private String content;

    @ColumnInfo(name = Constants.COLUMN_NAME_DATE)
    private Date creation_date;

    // Impliciet dwingen we af dat beide velden ingevuld zijn (NonNull)
    // Gebruik deze constructor bij Create
    public Note(@NonNull String title, @NonNull String content) {
        this.title = title;
        this.content = content;
        this.creation_date = new Date(System.currentTimeMillis());
    }

    // Gebruik deze constructor bij Update
    @Ignore
    public Note(@NonNull Long id, @NonNull String title, @NonNull String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    /* --- Generated Getters and Setters hereunder --- */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }
}
