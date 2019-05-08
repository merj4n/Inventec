package net.iesochoa.germanbelda.proyect.inventec.Activities;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import net.iesochoa.germanbelda.proyect.inventec.Database.ArticulosDbHelper;

import java.util.UUID;

public class Articulo implements Parcelable {
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

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ArticulosDbHelper.ArticulosContract.ArticulosEntry.CODIGO, codigo);
        values.put(ArticulosDbHelper.ArticulosContract.ArticulosEntry.NAME, nombre);
        values.put(ArticulosDbHelper.ArticulosContract.ArticulosEntry.TOTALS, totales);

        return values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.codigo);
        dest.writeString(this.nombre);
        dest.writeString(this.totales);
    }

    protected Articulo(Parcel in) {
        this.id = in.readString();
        this.codigo = in.readString();
        this.nombre = in.readString();
        this.totales = in.readString();
    }

    public static final Parcelable.Creator<Articulo> CREATOR = new Parcelable.Creator<Articulo>() {
        @Override
        public Articulo createFromParcel(Parcel source) {
            return new Articulo(source);
        }

        @Override
        public Articulo[] newArray(int size) {
            return new Articulo[size];
        }
    };
}