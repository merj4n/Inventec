package net.iesochoa.germanbelda.proyect.inventec.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.iesochoa.germanbelda.proyect.inventec.ADAPTER.AdaptadorArticulos;
import net.iesochoa.germanbelda.proyect.inventec.BBDD.ArticulosDbHelper;
import net.iesochoa.germanbelda.proyect.inventec.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recView;
    private ArrayList<Articulo> datos;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciamos dbhelper para crear la base de datos.
        ArticulosDbHelper db = new ArticulosDbHelper(this);
        SQLiteDatabase database = db.getWritableDatabase();

        //inicialización de la lista de datos de ejemplo
        datos = new ArrayList<>();

            datos.add(new Articulo("GA-B450M DS3H","Gigabyte B450M DS3H","18"));
            datos.add(new Articulo("911-7B48-001","MSI Z370-A Pro","1"));
            datos.add(new Articulo("911-7B24-003","MSI B360M PRO-VDH","44"));
            datos.add(new Articulo("GA-H110M-S2H","Gigabyte GA-H110M-S2H","132"));
            datos.add(new Articulo("911-7B98-001","MSI Z390-A PRO","0"));
            datos.add(new Articulo("X470 AORUS ULTRA GAM","Gigabyte Aorus X470 Ultra Gaming","5"));
            datos.add(new Articulo("GA-Z390 GAMING X","Gigabyte Z390 Gaming X","6"));
            datos.add(new Articulo("90MB0ZZ0-M0EAY0","Asus Prime Z370-P II","11"));
            datos.add(new Articulo("GAB45ARSM-00-G","Gigabyte B450 AORUS M","15"));
            datos.add(new Articulo("911-7B51-007","MSI Mpg Z390 Gaming Plus","26"));
            //datos.add(new Articulo("BX80684I58400","Intel Core i5-8400 2.8GHz BOX","-2"));
            datos.add(new Articulo("BX80684I58400","","-2"));
            datos.add(new Articulo("YD2600BBAFBOX","Procesador AMD Ryzen 5 2600 3.4 Ghz","5"));
            datos.add(new Articulo("BX80684I78700","Intel Core i7-8700 3.2Ghz BOX","15"));
            datos.add(new Articulo("BX80684I59600K","Intel Core i5-9600K 3.7Ghz","16"));
            datos.add(new Articulo("BX80684I38100","Intel Core i3-8100 3.6GHz BOX","17"));
            datos.add(new Articulo("BX80684I79700K","Intel Core i7-9700K 3.6Ghz","22"));
            datos.add(new Articulo("BX80684I99900K","Intel Core i9-9900K 3.6Ghz","12"));
            datos.add(new Articulo("BX80677I57400","Intel Core i5-7400 3.0GHz BOX","4"));
            datos.add(new Articulo("YD2700BBAFBOX","Procesador AMD Ryzen 7 2700 4.1 Ghz","33"));
            datos.add(new Articulo("BX80677G4560","Intel Pentium G4560 3.5GHz Box","66"));
            datos.add(new Articulo("SA400S37/240G","Kingston A400 SSD 240GB","44"));
            datos.add(new Articulo("ST1000DM010","Seagate BarraCuda 3.5\" 1TB SATA3","88"));
            datos.add(new Articulo("TR200 25SAT3-240G","Toshiba OCZ TR200 SSD 240GB SATA3","38"));
            datos.add(new Articulo("MZ-76E500B/EU","Samsung 860 EVO Basic SSD 500GB SATA3","41"));
            datos.add(new Articulo("SA400S37/120G","Kingston A400 SSD 120GB","22"));
            datos.add(new Articulo("MZ-V7S500BW","Samsung 970 EVO Plus 500GB SSD NVMe M.2","19"));
            datos.add(new Articulo("ST2000DM008","Seagate BarraCuda 3.5\" 2TB SATA 3","51"));
            datos.add(new Articulo("WDS500G2B0A","WD Blue 3D Nand SSD SATA 500GB","40"));
            datos.add(new Articulo("HDWD110UZSVA","Toshiba P300 3.5\" 1TB 7200RPM SATA 3","32"));
            datos.add(new Articulo("CT500MX500SSD1","Crucial MX500 SSD 500GB SATA","13"));
            datos.add(new Articulo("GV-RX580GAMING-8GD","Gigabyte Radeon RX 580 Gaming 8G 8GB GDDR5","8"));
            datos.add(new Articulo("GV-N2060OC-6GD","Gigabyte GeForce RTX 2060 OC 6GB GDDR6","1"));
            datos.add(new Articulo("90YV0A74-M0NA00","ASUS Cerberus GeForce GTX 1050 Ti OC Edition 4GB GDDR5","1"));
            datos.add(new Articulo("GV-N2070WF3-8GC","Gigabyte GeForce RTX 2070 WindForce 8G 8GB GDDR6","2"));
            datos.add(new Articulo("GV-N1660OC-6GD","Gigabyte GeForce GTX 1660 OC 6GB GDDR5","36"));
            datos.add(new Articulo("GV-N105TOC-4GD","Gigabyte GeForce GTX 1050Ti OC 4GB GDDR5","32"));
            datos.add(new Articulo("ZT-P10620C-10M","Zotac GeForce GTX 1060 AMP ! Edition 6GB GDDR5X","22"));
            datos.add(new Articulo("GV-N1050OC-2GD","Gigabyte GeForce GTX 1050 OC 2GB GDDR5","10"));
            datos.add(new Articulo("912-V375-022","MSI GeForce RTX 2060 Ventus OC 6GB GDDR6","8"));
            datos.add(new Articulo("912-V335-001","MSI GeForce GTX 1050Ti GAMING X 4GB GDDR5","108"));
            datos.add(new Articulo("CT8G4DFS824A","Crucial DDR4 2400 PC4-19200 8GB CL17","1024"));
            datos.add(new Articulo("F4-3200C16D-16GTZR","G.Skill Trident Z RGB DDR4 3200 PC4-25600 16GB 2x8GB CL16","12"));
            datos.add(new Articulo("CMK16GX4M2A2400C16","Corsair Vengeance LPX DDR4 2400 PC4-19200 16GB 2x8GB CL16","16"));
            datos.add(new Articulo("HX424C15FB2/8","Kingston HyperX Fury Black DDR4 2400 PC4-19200 8GB CL15","-6"));
            datos.add(new Articulo("CMK16GX4M2B3000C15","Corsair Vengeance LPX DDR4 3000 PC4-24000 16GB 2x8GB CL15","2"));
            datos.add(new Articulo("V7192008GBD-SR","V7 V7192008GBD-SR DDR4 2400 PC4-19200 8GB CL17","6"));
            datos.add(new Articulo("HX424C15FB2K2/16","Kingston HyperX Fury DDR4 2400 PC4-19200 16GB 2X8GB CL15","9"));
            datos.add(new Articulo("CMW16GX4M2C3200C16","Corsair Vengeance RGB Pro DDR4 3200 PC4-25600 16GB 2x8GB CL16","21"));
            datos.add(new Articulo("BLS8G4D26BFSBK","Memoria Ram Crucial Ballistix Sport LT Single Rank DDR4 2666 PC4-21300 8GB CL16","42"));
            datos.add(new Articulo("BLS4G4D26BFSE","Memoria Ram Crucial Ballistix Sport LT Red DDR4 2666Mhz PC4-21300 4GB CL16","74"));
            datos.add(new Articulo("CMW16GX4M2C3000C15","Corsair Vengeance RGB Pro DDR4 3000 PC4-24000 16GB 2x8GB CL15","62"));

        // Inserto en la base de datos los articulos de mi arrayList
        /*for (Articulo ins:datos) {
            database.insert("Articulo",null,ins.toContentValues());
        }*/

        //Inicialización RecyclerView
        recView = (RecyclerView) findViewById(R.id.rvArticulos);
        recView.setHasFixedSize(true);

        final AdaptadorArticulos adaptador = new AdaptadorArticulos(datos);

        recView.setAdapter(adaptador);
        //Mostramos el recycler en modo linea
        recView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }
}
