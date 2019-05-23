package net.iesochoa.germanbelda.proyect.inventec.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import net.iesochoa.germanbelda.proyect.inventec.Pojo.Articulo;

import java.util.ArrayList;

public class DbAccess {
    public static void fillDb(SQLiteDatabase database, ArticulosDbHelper db, ArrayList<Articulo> lista) {
        try {
            database = db.getWritableDatabase();

            lista.add(new Articulo("5030917198083", "Call Of Duty Infinity Warfare PC", "0", "3"));
            lista.add(new Articulo("6937826612077", "AMD AM4 Bloque refrigeración", "0", "5"));
            lista.add(new Articulo("5030917057885", "Call Of Duty 4 PC", "6", "6"));
            lista.add(new Articulo("5030917057502", "Call Of Duty World at War PC", "0", "11"));
            lista.add(new Articulo("5030917071126", "Call Of Duty Modern Warfare 2 PC", "0", "1"));
            lista.add(new Articulo("5030917085925", "Call Of Duty Black Ops PC", "4", "4"));
            lista.add(new Articulo("BX80684I58400", "", "0", "-2"));
            lista.add(new Articulo("911-7B48-001", "MSI Z370-A Pro", "0","12"));
            lista.add(new Articulo("911-7B24-003", "MSI B360M PRO-VDH", "7","7"));
            lista.add(new Articulo("GA-H110M-S2H", "Gigabyte GA-H110M-S2H", "0","2"));

            // Inserto en la base de lista los articulos de mi arrayList
            for (Articulo ins : lista) {
                Log.e("Error", ins.getNombre() + database.getPath());
                database.insert("Articulo", null, ins.toContentValues());
            }
        } catch (Exception e) {
            Log.e("Error", "No se ha podido crear la base de datos." + e.getMessage());
        }
    }

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
            database.delete(ArticulosContract.ArticulosEntry.TABLE_NAME, "CODIGO=?", args);
        } catch (Exception e) {
            Log.e("Error", "No se ha podido borrar el articulo." + e.getMessage());
        }
    }

    public static boolean findArt(SQLiteDatabase database, ArticulosDbHelper db, Articulo articulo) {
        String codigo = "";
        try {
            database = db.getReadableDatabase();
            String sql = "SELECT * FROM ARTICULO WHERE CODIGO=?";
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
            Log.e("Error", "Articulo no encontrado." + e.getMessage());
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

    public static ArrayList<Articulo> sortBy(ArrayList<Articulo> lista, String by){

        ArrayList<Articulo> original = new ArrayList<>();

        switch (by) {
            case ">":
                for (Articulo ar:lista) {
                    if(Integer.parseInt(ar.getLeidos())>Integer.parseInt(ar.getTotales())){
                        original.add(ar);
                        Log.e("LISTA",ar.getNombre());
                    }
                }
                return original;
            case "<":
                for (Articulo ar:lista) {
                    if(Integer.parseInt(ar.getLeidos())<Integer.parseInt(ar.getTotales())){
                        original.add(ar);
                        Log.e("LISTA",ar.getNombre());
                    }
                }
                return original;
            case "all":
                return lista;
            default:
                return lista;
        }
    }

    public boolean checkDataBase(String database_path) {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(database_path, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            Log.e("Error", "No existe la base de datos." + e.getMessage());
        }
        return checkDB != null;
    }
}
