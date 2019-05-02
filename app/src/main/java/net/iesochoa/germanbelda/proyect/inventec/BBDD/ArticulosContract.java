package net.iesochoa.germanbelda.proyect.inventec.BBDD;

import android.provider.BaseColumns;

public class ArticulosContract {
    public static abstract class ArticulosEntry implements BaseColumns {
        public static final String TABLE_NAME ="Articulo";
        public static final String CODIGO = "codigo";
        public static final String NAME = "nombre";
        public static final String TOTALS = "totales";
    }
}
