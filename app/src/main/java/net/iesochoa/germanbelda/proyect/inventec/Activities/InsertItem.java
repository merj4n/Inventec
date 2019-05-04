package net.iesochoa.germanbelda.proyect.inventec.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.iesochoa.germanbelda.proyect.inventec.ADAPTER.AdaptadorArticulos;
import net.iesochoa.germanbelda.proyect.inventec.BBDD.ArticulosContract;
import net.iesochoa.germanbelda.proyect.inventec.BBDD.ArticulosDbHelper;
import net.iesochoa.germanbelda.proyect.inventec.R;

public class InsertItem extends AppCompatActivity {

    EditText etCodigo;
    EditText etNombre;
    EditText etCantidad;
    Button btAceptar;
    Button btCancelar;
    boolean existe = false;

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
                Articulo nuevo;
                if (etCodigo.getText().toString().equals(""))
                    Toast.makeText(InsertItem.this, "Necesitas introducir el codigo al menos", Toast.LENGTH_LONG).show();
                else {
                    //Agrego un nuevo articulo a la base de lista
                    if(etCantidad.getText().toString().equals("")) {
                        nuevo = new Articulo(etCodigo.getText().toString(), etNombre.getText().toString(), "0");
                    }else {
                        nuevo = new Articulo(etCodigo.getText().toString(), etNombre.getText().toString(), etCantidad.getText().toString());
                    }
                    String nuevoCodigo = etCodigo.getText().toString();
                    //Recorro la base de datos comprobando si existe el articulo
                    Cursor c = database.query(ArticulosContract.ArticulosEntry.TABLE_NAME, null, null, null, null, null, null);
                    while (c.moveToNext()) {
                        String codigo = c.getString(c.getColumnIndex(ArticulosContract.ArticulosEntry.CODIGO));
                        if (codigo.equals(nuevoCodigo))
                        {
                            existe = true;
                            finish();
                            Toast.makeText(InsertItem.this, "El articulo "+ codigo +" ya existe", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (!existe) {
                        database.insert("Articulo", null, nuevo.toContentValues());
                        //Agrego el articulo a la ArrayList
                        MainActivity.lista.add(nuevo);
                        int pos = MainActivity.lista.indexOf(nuevo);
                        //Notifico al adaptador la posicion del nuevo articulo
                        AdaptadorArticulos.adaptador.notifyItemInserted(pos);
                        Toast.makeText(InsertItem.this, "Articulo "+ nuevoCodigo +" añadido", Toast.LENGTH_LONG).show();
                        finish();
                    }
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
