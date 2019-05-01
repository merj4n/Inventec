package net.iesochoa.germanbelda.proyect.inventec.BBDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class ArticulosDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "articulos.db";

    public ArticulosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public class ArticulosContract {
        public abstract class ArticulosEntry implements BaseColumns {
            public static final String TABLE_NAME ="Articulo";
            public static final String ID = "id";
            public static final String NAME = "nombre";
            public static final String TOTALS = "totales";
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ArticulosContract.ArticulosEntry.TABLE_NAME + " ("
                + ArticulosContract.ArticulosEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ArticulosContract.ArticulosEntry.ID + " TEXT NOT NULL,"
                + ArticulosContract.ArticulosEntry.NAME + " TEXT NOT NULL,"
                + ArticulosContract.ArticulosEntry.TOTALS + " TEXT NOT NULL,"
                + "UNIQUE (" + ArticulosContract.ArticulosEntry.ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}