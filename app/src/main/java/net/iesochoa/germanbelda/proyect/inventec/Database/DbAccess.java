package net.iesochoa.germanbelda.proyect.inventec.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import net.iesochoa.germanbelda.proyect.inventec.Pojo.Articulo;

import java.util.ArrayList;

public class DbAccess {
    public static void fillDb(SQLiteDatabase database, ArticulosDbHelper db, ArrayList<Articulo> lista){
        try{
            database = db.getWritableDatabase();

            lista.add(new Articulo("5030917198083", "Call Of Duty Infinity Warfare PC","0", "12"));
            lista.add(new Articulo("6937826612077", "AMD AM4 Bloque refrigeración","0", "5"));
            lista.add(new Articulo("5030917057885", "Call Of Duty 4 PC", "0","6"));
            lista.add(new Articulo("5030917057502", "Call Of Duty World at War PC", "0","11"));
            lista.add(new Articulo("5030917071126", "Call Of Duty Modern Warfare 2 PC", "0","12"));
            lista.add(new Articulo("5030917085925", "Call Of Duty Black Ops PC", "0","12"));
            lista.add(new Articulo("BX80684I58400", "", "0","-2"));
            /*lista.add(new Articulo("911-7B48-001", "MSI Z370-A Pro", "0","12"));
            lista.add(new Articulo("911-7B24-003", "MSI B360M PRO-VDH", "0","12"));
            lista.add(new Articulo("GA-H110M-S2H", "Gigabyte GA-H110M-S2H", "0","12"));
            lista.add(new Articulo("911-7B98-001", "MSI Z390-A PRO", "0","0"));
            lista.add(new Articulo("YD2600BBAFBOX", "Procesador AMD Ryzen 5 2600 3.4 Ghz", "12"));
            lista.add(new Articulo("BX80684I78700", "Intel Core i7-8700 3.2Ghz BOX", "12"));
            lista.add(new Articulo("BX80684I59600K", "Intel Core i5-9600K 3.7Ghz", "12"));
            lista.add(new Articulo("BX80684I38100", "Intel Core i3-8100 3.6GHz BOX", "17"));
            lista.add(new Articulo("BX80684I79700K", "Intel Core i7-9700K 3.6Ghz", "12"));
            lista.add(new Articulo("BX80684I99900K", "Intel Core i9-9900K 3.6Ghz", "12"));
            lista.add(new Articulo("BX80677I57400", "Intel Core i5-7400 3.0GHz BOX", "12"));
            lista.add(new Articulo("YD2700BBAFBOX", "Procesador AMD Ryzen 7 2700 4.1 Ghz", "12"));
            lista.add(new Articulo("BX80677G4560", "Intel Pentium G4560 3.5GHz Box", "12"));
            lista.add(new Articulo("SA400S37/240G", "Kingston A400 SSD 240GB", "44"));
            lista.add(new Articulo("ST1000DM010", "Seagate BarraCuda 3.5\" 1TB SATA3", "12"));
            lista.add(new Articulo("TR200 25SAT3-240G", "Toshiba OCZ TR200 SSD 240GB SATA3", "12"));
            lista.add(new Articulo("MZ-76E500B/EU", "Samsung 860 EVO Basic SSD 500GB SATA3", "412"));
            lista.add(new Articulo("SA400S37/120G", "Kingston A400 SSD 120GB", "12"));
            lista.add(new Articulo("MZ-V7S500BW", "Samsung 970 EVO Plus 500GB SSD NVMe M.2", "19"));
            lista.add(new Articulo("ST2000DM008", "Seagate BarraCuda 3.5\" 2TB SATA 3", "12"));
            lista.add(new Articulo("WDS500G2B0A", "WD Blue 3D Nand SSD SATA 500GB", "12"));
            lista.add(new Articulo("HDWD110UZSVA", "Toshiba P300 3.5\" 1TB 7200RPM SATA 3", "412"));
            lista.add(new Articulo("CT500MX500SSD1", "Crucial MX500 SSD 500GB SATA", "12"));
            lista.add(new Articulo("GV-RX580GAMING-8GD", "Gigabyte Radeon RX 580 Gaming 8G 8GB GDDR5", "12"));
            lista.add(new Articulo("GV-N2060OC-6GD", "Gigabyte GeForce RTX 2060 OC 6GB GDDR6", "12"));
            lista.add(new Articulo("90YV0A74-M0NA00", "ASUS Cerberus GeForce GTX 1050 Ti OC Edition 4GB GDDR5", "1"));
            lista.add(new Articulo("GV-N2070WF3-8GC", "Gigabyte GeForce RTX 2070 WindForce 8G 8GB GDDR6", "12"));
            lista.add(new Articulo("GV-N1660OC-6GD", "Gigabyte GeForce GTX 1660 OC 6GB GDDR5", "12"));
            lista.add(new Articulo("GV-N105TOC-4GD", "Gigabyte GeForce GTX 1050Ti OC 4GB GDDR5", "12"));
            lista.add(new Articulo("ZT-P10620C-10M", "Zotac GeForce GTX 1060 AMP ! Edition 6GB GDDR5X", "12"));
            lista.add(new Articulo("GV-N1050OC-2GD", "Gigabyte GeForce GTX 1050 OC 2GB GDDR5", "12"));
            lista.add(new Articulo("912-V375-022", "MSI GeForce RTX 2060 Ventus OC 6GB GDDR6", "12"));
            lista.add(new Articulo("912-V335-001", "MSI GeForce GTX 1050Ti GAMING X 4GB GDDR5", "12"));
            lista.add(new Articulo("CT8G4DFS824A", "Crucial DDR4 2400 PC4-19200 8GB CL17", "1024"));
            lista.add(new Articulo("F4-3200C16D-16GTZR", "G.Skill Trident Z RGB DDR4 3200 PC4-25600 16GB 2x8GB CL16", "12"));
            lista.add(new Articulo("CMK16GX4M2A2400C16", "Corsair Vengeance LPX DDR4 2400 PC4-19200 16GB 2x8GB CL16", "12"));
            lista.add(new Articulo("HX424C15FB2/8", "Kingston HyperX Fury Black DDR4 2400 PC4-19200 8GB CL15", "12"));
            lista.add(new Articulo("CMK16GX4M2B3000C15", "Corsair Vengeance LPX DDR4 3000 PC4-24000 16GB 2x8GB CL15", "12"));
            lista.add(new Articulo("V7192008GBD-SR", "V7 V7192008GBD-SR DDR4 2400 PC4-19200 8GB CL17", "12"));
            lista.add(new Articulo("HX424C15FB2K2/16", "Kingston HyperX Fury DDR4 2400 PC4-19200 16GB 2X8GB CL15", "12"));
            lista.add(new Articulo("CMW16GX4M2C3200C16", "Corsair Vengeance RGB Pro DDR4 3200 PC4-25600 16GB 2x8GB CL16", "12"));
            lista.add(new Articulo("BLS8G4D26BFSBK", "Memoria Ram Crucial Ballistix Sport LT Single Rank DDR4 2666 PC4-21300 8GB CL16", "12"));
            lista.add(new Articulo("BLS4G4D26BFSE", "Memoria Ram Crucial Ballistix Sport LT Red DDR4 2666Mhz PC4-21300 4GB CL16", "12"));
            lista.add(new Articulo("CMW16GX4M2C3000C15", "Corsair Vengeance RGB Pro DDR4 3000 PC4-24000 16GB 2x8GB CL15", "12"));
            */
            // Inserto en la base de lista los articulos de mi arrayList
            for (Articulo ins : lista) {
                Log.e("Error", ins.getNombre() + database.getPath());
                database.insert("Articulo", null, ins.toContentValues());
            }
        }catch(Exception e){
            Log.e("Error","No se ha podido crear la base de datos." + e.getMessage());
        }
    }
    public static void readDb(SQLiteDatabase database, ArticulosDbHelper db, ArrayList<Articulo> lista){
        try{
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
        }catch(Exception e){
            Log.e("Error","Error de lectura en la base de datos." + e.getMessage());
        }
    }
    public static void removeArt(SQLiteDatabase database, ArticulosDbHelper db, ArrayList<Articulo> lista, int position) {
        try{
            database = db.getWritableDatabase();
            Articulo articulo = lista.get(position);
            lista.remove(articulo);
            String[] args = {
                    articulo.getCodigo()
            };
            database.delete(ArticulosContract.ArticulosEntry.TABLE_NAME, "CODIGO=?", args);
        }
        catch(Exception e){
            Log.e("Error","No se ha podido borrar el articulo." + e.getMessage());
        }
    }
    public static boolean findArt(SQLiteDatabase database, ArticulosDbHelper db, Articulo articulo){
        String codigo="";
        try{
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
        }catch(Exception e){
            Log.e("Error","Articulo no encontrado." + e.getMessage());
        }
        return false;
    }
    public static void insertArt(SQLiteDatabase database, ArticulosDbHelper db, Articulo articulo){
        try{
            database = db.getWritableDatabase();
            database.insert(ArticulosContract.ArticulosEntry.TABLE_NAME, null, articulo.toContentValues());
        }catch(Exception e){
            Log.e("Error","No se ha insertado el articulo." + e.getMessage());
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
