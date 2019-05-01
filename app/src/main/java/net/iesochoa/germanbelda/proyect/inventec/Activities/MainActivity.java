package net.iesochoa.germanbelda.proyect.inventec.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.iesochoa.germanbelda.proyect.inventec.ADAPTER.AdaptadorArticulos;
import net.iesochoa.germanbelda.proyect.inventec.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recView;
    private ArrayList<Articulo> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicialización de la lista de datos de ejemplo
        datos = new ArrayList<>();
        for(int i=1; i<=50; i++){
            //int result = Integer.parseInt(number);
            datos.add(new Articulo("215488796","Palo","10"));
        }
        //Inicialización RecyclerView
        recView = (RecyclerView) findViewById(R.id.rvArticulos);
        recView.setHasFixedSize(true);

        final AdaptadorArticulos adaptador = new AdaptadorArticulos(datos);

        recView.setAdapter(adaptador);
        //Mostramos el
        recView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }
}
