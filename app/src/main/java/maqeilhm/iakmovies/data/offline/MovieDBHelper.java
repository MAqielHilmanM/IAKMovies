package maqeilhm.iakmovies.data.offline;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MAqielHilmanM on 12/10/2017.
 */

public class MovieDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movieDB.org";
    private static final int VERSION = 2;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE =
                "CREATE TABLE " +
                        MovieContract.MovieEntry.TABEL_NAME +
                        "(" +
                        MovieContract.MovieEntry._ID                    + " INTEGER PRIMARY KEY, "+
                        MovieContract.MovieEntry.COLUMN_FAVORITE_IDS    + " INTEGER NOT NULL, "+
                        MovieContract.MovieEntry.COLUMN_TITLE           + " TEXT NOT NULL, "+
                        MovieContract.MovieEntry.COLUMN_ORI_TITLE       + " TEXT NOT NULL, "+
                        MovieContract.MovieEntry.COLUMN_OVERVIEW        + " TEXT NOT NULL, "+
                        MovieContract.MovieEntry.COLUMN_POSTER_PATH     + " TEXT NOT NULL, "+
                        MovieContract.MovieEntry.COLUMN_BACKDROP_PATH   + " TEXT NOT NULL, "+
                        MovieContract.MovieEntry.COLUMN_ORIGINAL_LANG   + " TEXT NOT NULL, "+
                        MovieContract.MovieEntry.COLUMN_ADULT           + " INTEGER NOT NULL, "+
                        MovieContract.MovieEntry.COLUMN_VIDEO           + " INTEGER NOT NULL, "+
                        MovieContract.MovieEntry.COLUMN_RELEASE_DATE    + " TEXT NOT NULL, "+
                        MovieContract.MovieEntry.COLUMN_GENRE           + " TEXT NOT NULL, "+
                        MovieContract.MovieEntry.COLUMN_VOTE_COUNT      + " INTEGER NOT NULL, "+
                        MovieContract.MovieEntry.COLUMN_VOTE_AVG        + " REAL NOT NULL, "+
                        MovieContract.MovieEntry.COLUMN_POPULARITY      + " REAL NOT NULL); ";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
