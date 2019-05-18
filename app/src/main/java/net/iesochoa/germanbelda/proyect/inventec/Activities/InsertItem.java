package net.iesochoa.germanbelda.proyect.inventec.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.iesochoa.germanbelda.proyect.inventec.Database.ArticulosDbHelper;
import net.iesochoa.germanbelda.proyect.inventec.Database.DbAccess;
import net.iesochoa.germanbelda.proyect.inventec.Pojo.Articulo;
import net.iesochoa.germanbelda.proyect.inventec.R;

public class InsertItem extends AppCompatActivity {

    public static final String EXTRA_ITEM_NUEVO = "net.iesochoa.germanbelda.proyect.inventec.Activities.InsertItem";
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
                Articulo nuevo;
                String codigo;
                if (etCodigo.getText().toString().equals(""))
                    Toast.makeText(InsertItem.this, "Necesitas introducir el codigo al menos", Toast.LENGTH_LONG).show();
                else {
                    //Agrego un nuevo articulo a la lista
                    if (etCantidad.getText().toString().equals("")) {
                        nuevo = new Articulo(etCodigo.getText().toString(), etNombre.getText().toString(), "1", "0");
                    } else {
                        nuevo = new Articulo(etCodigo.getText().toString(), etNombre.getText().toString(), "1", etCantidad.getText().toString());
                    }
                    //Recorro la base de datos comprobando si existe el articulo
                    if (DbAccess.findArt(database, db, nuevo)) {
                        Toast.makeText(InsertItem.this, "El articulo " + nuevo.getCodigo() + " ya existe", Toast.LENGTH_LONG).show();
                        setResult(RESULT_CANCELED);
                        finish();

                    } else {
                        //Agrego el articulo a la ArrayList y comunico el resultado
                        DbAccess.insertArt(database, db, nuevo);
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_ITEM_NUEVO, nuevo);
                        setResult(RESULT_OK, intent);
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
