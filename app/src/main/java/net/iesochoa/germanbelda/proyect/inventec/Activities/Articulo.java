package net.iesochoa.germanbelda.proyect.inventec.Activities;

import android.content.ContentValues;

import net.iesochoa.germanbelda.proyect.inventec.BBDD.ArticulosDbHelper;

import java.util.UUID;

public class Articulo {
    private String id;
    private String codigo;
    private String nombre;
    private String totales;

    public Articulo(String codigo, String nombre, String totales) {
        this.id = UUID.randomUUID().toString();
        this.codigo = codigo;
        this.nombre = nombre;
        this.totales = totales;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTotales() {
        return totales;
    }

    public String getCodigo() {
        return codigo;
    }

    /*public long saveArticulo(Articulo articulo) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(ArticulosContract.ArticulosEntry.TABLE_NAME,
                null,
                articulo.toContentValues());

    }*/

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ArticulosDbHelper.ArticulosContract.ArticulosEntry.CODIGO, codigo);
        values.put(ArticulosDbHelper.ArticulosContract.ArticulosEntry.NAME, nombre);
        values.put(ArticulosDbHelper.ArticulosContract.ArticulosEntry.TOTALS, totales);

        return values;
    }
}