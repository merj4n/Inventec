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
        for(int i=1; i<=50; i++){
            //int result = Integer.parseInt(number);
            datos.add(new Articulo(String.valueOf(i),"Palo","10"));
        }

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
