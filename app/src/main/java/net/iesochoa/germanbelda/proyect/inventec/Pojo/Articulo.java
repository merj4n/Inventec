package net.iesochoa.germanbelda.proyect.inventec.Pojo;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import net.iesochoa.germanbelda.proyect.inventec.Database.ArticulosDbHelper;

import java.util.Objects;
import java.util.UUID;

public class Articulo implements Parcelable {
    private String id;
    private String codigo;
    private String nombre;
    private String leidos; // Nuevo campo de nuestro pojo
    private String totales;

    public Articulo(String codigo, String nombre,String leidos, String totales) {
        this.id = UUID.randomUUID().toString();
        this.codigo = codigo;
        this.nombre = nombre;
        this.leidos = leidos;  // Nuevo campo de nuestro pojo
        this.totales = totales;
    }

    public void setLeidos(String leidos) {
        this.leidos = leidos;
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

    public String getLeidos() {
        return leidos;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Articulo articulo = (Articulo) o;
        return id.equals(articulo.id) &&
                codigo.equals(articulo.codigo) &&
                nombre.equals(articulo.nombre) &&
                totales.equals(articulo.totales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, nombre, totales);
    }
}