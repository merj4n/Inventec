package net.iesochoa.germanbelda.proyect.inventec.Database;

import android.provider.BaseColumns;

public class ArticulosContract {
    public static abstract class ArticulosEntry implements BaseColumns {
        public static final String TABLE_NAME = "Articulos";
        public static final String CODIGO = "ean13";
        public static final String NAME = "denominacion";
        public static final String TOTALS = "total";
    }
}
