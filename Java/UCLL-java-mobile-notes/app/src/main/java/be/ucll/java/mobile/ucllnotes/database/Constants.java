package be.ucll.java.mobile.ucllnotes.database;

public interface Constants {
    // database, table and table columns
    String DB_NAME = "notes.db";

    String TABLE_NAME = "note";

    String COLUMN_NAME_ID = "note_id";
    String COLUMN_NAME_TITLE = "title";
    //String COLUMN_NAME_CONTENT = "content";
    String COLUMN_NAME_DATE = "creation_date";

    // Fragment to Fragment navigation
    String ID = "id";
    String OPERATION = "operation";

    // Classic CRUDS operations
    String OPERATION_CREATE = "CREATE";
    String OPERATION_READ = "READ";
    String OPERATION_UPDATE = "UPDATE";
    String OPERATION_DELETE = "DELETE";

    String OPERATION_SEARCH = "SEARCH";

}
