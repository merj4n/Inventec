package net.iesochoa.germanbelda.proyect.inventec.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.iesochoa.germanbelda.proyect.inventec.Model.Articulo;

import java.util.ArrayList;

public class DbAccess {

    public static void readDb(SQLiteDatabase database, ArticulosDbHelper db, ArrayList<Articulo> lista) {
        try {
            database = db.getWritableDatabase();
            Cursor c = database.query(ArticulosContract.ArticulosEntry.TABLE_NAME, null, null, null, null, null, null);

            if (!(c.getCount() == 0)) {
                while (c.moveToNext()) {
                    String codigo = c.getString(c.getColumnIndex(ArticulosContract.ArticulosEntry.CODIGO));
                    String nombre = c.getString(c.getColumnIndex(ArticulosContract.ArticulosEntry.NAME));
                    String totals = c.getString(c.getColumnIndex(ArticulosContract.ArticulosEntry.TOTALS));
                    String leidos = "0";
                    lista.add(new Articulo(codigo, nombre, leidos, totals));
                }
                c.close();
            }
            database.close();
        } catch (Exception e) {
            Log.e("Error", "Error de lectura en la base de datos." + e.getMessage());
        }
    }

    public static void removeArt(SQLiteDatabase database, ArticulosDbHelper db, ArrayList<Articulo> lista, int position) {
        try {
            database = db.getWritableDatabase();
            Articulo articulo = lista.get(position);
            String[] args = {
                    articulo.getCodigo()
            };
            database.delete(ArticulosContract.ArticulosEntry.TABLE_NAME, ArticulosContract.ArticulosEntry.CODIGO + "=?", args);
            database.close();
        } catch (Exception e) {
            Log.e("Error", "No se ha podido borrar el articulo." + e.getMessage());
        }
    }

    public static void dropTable(SQLiteDatabase database, ArticulosDbHelper db) {
        try {
            database = db.getWritableDatabase();
            database.execSQL("DROP TABLE IF EXISTS " + ArticulosContract.ArticulosEntry.TABLE_NAME);
            database.execSQL("CREATE TABLE " + ArticulosDbHelper.ArticulosContract.ArticulosEntry.TABLE_NAME + " ("
                    + ArticulosDbHelper.ArticulosContract.ArticulosEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ArticulosDbHelper.ArticulosContract.ArticulosEntry.CODIGO + " TEXT NOT NULL,"
                    + ArticulosDbHelper.ArticulosContract.ArticulosEntry.NAME + " TEXT NOT NULL,"
                    + ArticulosDbHelper.ArticulosContract.ArticulosEntry.TOTALS + " TEXT NOT NULL,"
                    + "UNIQUE (" + ArticulosDbHelper.ArticulosContract.ArticulosEntry.CODIGO + "))");
            database.close();
        } catch (Exception e) {
            Log.e("Error", "No se ha podido eliminar la tabla");
        }
    }

    public static boolean findArt(SQLiteDatabase database, ArticulosDbHelper db, Articulo articulo) {
        String codigo = "";
        try {
            database = db.getReadableDatabase();
            String sql = "SELECT * FROM " + ArticulosContract.ArticulosEntry.TABLE_NAME + " WHERE " + ArticulosContract.ArticulosEntry.CODIGO + " = ?";
            String[] args = {
                    articulo.getCodigo()
            };
            Cursor c = database.rawQuery(sql, args);
            if (c.moveToFirst()) {
                codigo = c.getString(c.getColumnIndex(ArticulosContract.ArticulosEntry.CODIGO));
                return true;
            }
            c.close();
        } catch (Exception e) {
            Log.e("Error", "Articulo " + codigo + " no encontrado." + e.getMessage());
        }
        return false;
    }

    public static void insertArt(SQLiteDatabase database, ArticulosDbHelper db, Articulo articulo) {
        try {
            database = db.getWritableDatabase();
            database.insert(ArticulosContract.ArticulosEntry.TABLE_NAME, null, articulo.toContentValues());
        } catch (Exception e) {
            Log.e("Error", "No se ha insertado el articulo." + e.getMessage());
        }
    }

}
