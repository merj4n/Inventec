package net.iesochoa.germanbelda.proyect.inventec.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.iesochoa.germanbelda.proyect.inventec.ADAPTER.AdaptadorArticulos;
import net.iesochoa.germanbelda.proyect.inventec.BBDD.ArticulosDbHelper;
import net.iesochoa.germanbelda.proyect.inventec.R;

public class InsertItem extends AppCompatActivity {

    EditText etCodigo;
    EditText etNombre;
    EditText etCantidad;
    Button btAceptar;
    Button btCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_item);

        etCodigo = (EditText) findViewById(R.id.etCodigo);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etCantidad = (EditText) findViewById(R.id.etCantidad);
        btAceptar = (Button) findViewById(R.id.btAceptar);
        btCancelar = (Button) findViewById(R.id.btCancelar);

        btAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticulosDbHelper db = new ArticulosDbHelper(InsertItem.this);
                SQLiteDatabase database = db.getWritableDatabase();

                if (etCodigo.getText().toString().isEmpty() && etNombre.getText().toString().isEmpty() && etCantidad.getText().toString().isEmpty())
                    finish();
                else {
                    //Agrego un nuevo articulo a la base de lista
                    Articulo nuevo = new Articulo(etCodigo.getText().toString(), etNombre.getText().toString(), etCantidad.getText().toString());
                    database.insert("Articulo", null, nuevo.toContentValues());
                    //Agrego el articulo a la ArrayList
                    MainActivity.lista.add(nuevo);
                    int pos = MainActivity.lista.indexOf(nuevo);
                    Log.i("Posicion", "-----> " + pos);
                    //Notifico al adaptador la posicion del nuevo articulo
                    AdaptadorArticulos.adaptador.notifyItemInserted(pos);
                    finish();
                }
            }
        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
