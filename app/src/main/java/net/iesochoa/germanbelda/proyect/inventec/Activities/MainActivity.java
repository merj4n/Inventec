package net.iesochoa.germanbelda.proyect.inventec.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.iesochoa.germanbelda.proyect.inventec.Adapter.AdaptadorArticulos;
import net.iesochoa.germanbelda.proyect.inventec.Database.ArticulosContract;
import net.iesochoa.germanbelda.proyect.inventec.Database.ArticulosDbHelper;
import net.iesochoa.germanbelda.proyect.inventec.Database.DbAccess;
import net.iesochoa.germanbelda.proyect.inventec.R;

import java.util.ArrayList;

import static net.iesochoa.germanbelda.proyect.inventec.Activities.InsertItem.EXTRA_ITEM_NUEVO;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_INSERTAR_ITEM = 2222;
    private static final String ARRAYLIST_DATA = "ARRAYLIST_DATA";
    private RecyclerView recView;
    public ArrayList<Articulo> lista;
    public AdaptadorArticulos adaptador;
    private RadioButton rbTodos;
    private RadioButton rbAlerta;
    private RadioGroup rgOpciones;
    private EditText etinputCodigo;
    private TextView tvTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etinputCodigo = (EditText)findViewById(R.id.etInputCodigo);
        tvTitulo = (TextView)findViewById(R.id.tvTitulo);
        etinputCodigo.setVisibility(View.INVISIBLE);


        //Compruebo el estado de la actividad primero
        if(savedInstanceState != null) {
            lista = savedInstanceState.getParcelableArrayList(ARRAYLIST_DATA);
        }else{
            //inicialización de la lista de ejemplo
            initArrayDb();
        }

        //Boton flotante para insertar un nuevo articulo
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!etinputCodigo.isActivated()) {
                    etinputCodigo.setActivated(true);
                    tvTitulo.setVisibility(View.INVISIBLE);
                    etinputCodigo.setVisibility(View.VISIBLE);
                }else {
                    etinputCodigo.setActivated(false);
                    tvTitulo.setVisibility(View.VISIBLE);
                    etinputCodigo.setVisibility(View.INVISIBLE);
                }
                //LLamo a la actividad encargada de crear el articulo

                //Intent intent = new Intent(MainActivity.this, InsertItem.class);
                //startActivityForResult(intent, REQUEST_INSERTAR_ITEM);
            }
        });

        adaptador = new AdaptadorArticulos(lista);

        //Inicialización RecyclerView
        recView = (RecyclerView) findViewById(R.id.rvArticulos);
        recView.setHasFixedSize(true);
        //Cuando pulsas sobre cualquiera de los articulos
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recView.getChildAdapterPosition(v);
                eliminarArt(position);
            }
        });
        recView.setAdapter(adaptador);
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
    // Mantener el estado de los datos de la actividad cuando giras el movil
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelableArrayList(ARRAYLIST_DATA, lista);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_INSERTAR_ITEM:
                if (resultCode == RESULT_OK) {
                    Articulo articulo = data.getParcelableExtra(EXTRA_ITEM_NUEVO);
                    lista.add(articulo);
                    adaptador.notifyDataSetChanged();
                }
                break;
            default:
        }
    }

    //Refresco los cambios del adaptador cuando vuelvo a la actividad principal, por si inserto un elemento nuevo o elimino uno
    @Override
    protected void onResume() {
        super.onResume();
        adaptador.notifyDataSetChanged();
    }

    //Creación del menu de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itAjustes) {
            Toast.makeText(this, "Boton Ajustes pulsado!", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.itAcercade) {
            Toast.makeText(this, "Boton Acercade pulsado!", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void initArrayDb() {
        ArticulosDbHelper db = new ArticulosDbHelper(this);
        SQLiteDatabase database = db.getWritableDatabase();
        lista = new ArrayList<>();
        //Como siempre creo la base de datos lo que compruebo para rellenar el array es si el numero de elementos es distinto de 0
        // si es así, recorro los elementos de la base de datos y relleno el array.
        Cursor c = database.query(ArticulosContract.ArticulosEntry.TABLE_NAME, null, null, null, null, null, null);

        if (!(c.getCount() == 0)) {
            DbAccess.readDb(database,db,lista);
            c.close();
        } else {
            DbAccess.fillDb(database,db,lista);
        }
    }

    public void eliminarArt(final int position) {
        final ArticulosDbHelper db = new ArticulosDbHelper(this);
        final SQLiteDatabase database = db.getWritableDatabase();
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Eliminar artículo")
                .setMessage("¿Quieres eliminar este artículo del inventario?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DbAccess.removeArt(database,db,lista,position);
                                adaptador.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        builder.show();
    }
}
