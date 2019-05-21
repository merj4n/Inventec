package net.iesochoa.germanbelda.proyect.inventec.Activities;

import android.content.Context;
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
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import net.iesochoa.germanbelda.proyect.inventec.Adapter.AdaptadorArticulos;
import net.iesochoa.germanbelda.proyect.inventec.Database.ArticulosContract;
import net.iesochoa.germanbelda.proyect.inventec.Database.ArticulosDbHelper;
import net.iesochoa.germanbelda.proyect.inventec.Database.DbAccess;
import net.iesochoa.germanbelda.proyect.inventec.Pojo.Articulo;
import net.iesochoa.germanbelda.proyect.inventec.R;

import java.util.ArrayList;

import static net.iesochoa.germanbelda.proyect.inventec.Activities.InsertItem.EXTRA_ITEM_NUEVO;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_INSERTAR_ITEM = 2222;
    private static final String ARRAYLIST_DATA = "ARRAYLIST_DATA";
    private RecyclerView recView;
    public ArrayList<Articulo> lista;
    public AdaptadorArticulos adaptador,mayor,menor;
    private EditText etinputCodigo;
    private TextView tvTitulo;
    private TextView tvLeido;
    private ImageButton ibKeyboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etinputCodigo = (EditText) findViewById(R.id.etInputCodigo);
        tvTitulo = (TextView) findViewById(R.id.tvTitulo);
        tvLeido = (TextView) findViewById(R.id.tvLeidos);
        ibKeyboard = (ImageButton) findViewById(R.id.ibKeyboard);
        etinputCodigo.setVisibility(View.INVISIBLE);


        //Compruebo el estado de la actividad primero
        if (savedInstanceState != null) {
            lista = savedInstanceState.getParcelableArrayList(ARRAYLIST_DATA);
        } else {
            //inicialización de la lista de ejemplo
            initArrayDb();
        }

        adaptador = new AdaptadorArticulos(lista);

        //Inicialización RecyclerView
        recView = (RecyclerView) findViewById(R.id.rvArticulos);
        recView.setHasFixedSize(true);
        recView.setAdapter(adaptador);
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //Cuando pulsas sobre cualquiera de los articulos
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recView.getChildAdapterPosition(v);
                eliminarArt(position);
            }
        });

        //Boton flotante para insertar un nuevo articulo
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!etinputCodigo.isShown()) {
                    tvTitulo.setVisibility(View.INVISIBLE);
                    etinputCodigo.setVisibility(View.VISIBLE);
                    ibKeyboard.setVisibility(View.VISIBLE);
                    etinputCodigo.setInputType(InputType.TYPE_NULL);//Dejo el teclado oculto para este edittext
                    etinputCodigo.requestFocus();
                } else {
                    tvTitulo.setVisibility(View.VISIBLE);
                    etinputCodigo.setVisibility(View.INVISIBLE);
                    ibKeyboard.setVisibility(View.INVISIBLE);
                    etinputCodigo.clearFocus();
                }
                //LLamo a la actividad encargada de crear el articulo

                //Intent intent = new Intent(MainActivity.this, InsertItem.class);
                //startActivityForResult(intent, REQUEST_INSERTAR_ITEM);
            }
        });

        ibKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etinputCodigo.getInputType() == InputType.TYPE_NULL) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etinputCodigo, 0);
                    etinputCodigo.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etinputCodigo.getWindowToken(), 0);
                    etinputCodigo.setInputType(InputType.TYPE_NULL);
                }
            }
        });

        etinputCodigo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_UP)) { //Solo ejecuta al soltar la tecla enter
                    if (etinputCodigo.getText().length() == 13) {
                        ArticulosDbHelper db = new ArticulosDbHelper(MainActivity.this);
                        SQLiteDatabase database = db.getWritableDatabase();
                        Articulo articulo = new Articulo(etinputCodigo.getText().toString(), "", "1", "0");
                        if (!DbAccess.findArt(database, db, articulo)) {
                            lista.add(0, articulo); //Agrega al principio de la lista para mostrarlo el primero
                            DbAccess.insertArt(database, db, articulo);
                            Toast.makeText(MainActivity.this, "Articulo insertado", Toast.LENGTH_SHORT).show();
                            etinputCodigo.setText("");
                            etinputCodigo.requestFocus();
                            adaptador.notifyDataSetChanged();
                            return true;
                        } else {
                            Toast.makeText(MainActivity.this, "Articulo " + articulo.getCodigo() + " encontrado", Toast.LENGTH_SHORT).show();
                            for (Articulo art : lista) {
                                if (art.getCodigo().equals(articulo.getCodigo())) {
                                    int suma = Integer.parseInt(articulo.getLeidos()) + Integer.parseInt(art.getLeidos());
                                    art.setLeidos(String.valueOf(suma));
                                    Log.e("LEIDO", "Suma total ---> " + suma);
                                }
                            }
                            adaptador.notifyDataSetChanged();
                            etinputCodigo.setText("");
                            etinputCodigo.requestFocus();
                            return true;
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Formato no reconocido", Toast.LENGTH_SHORT).show();
                        etinputCodigo.setText("");
                        etinputCodigo.requestFocus();
                        return true;
                    }
                } else {
                    return false;
                }
            }
        });
        adaptador.notifyDataSetChanged();
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
            //Toast.makeText(this, "Boton Ajustes pulsado!", Toast.LENGTH_SHORT).show();
            mayor = new AdaptadorArticulos(DbAccess.sortBy(lista,">"));
            recView.setAdapter(mayor);
            mayor.notifyDataSetChanged();
        }
        if (id == R.id.itAcercade) {
            //Toast.makeText(this, "Boton Acercade pulsado!", Toast.LENGTH_SHORT).show();
            menor = new AdaptadorArticulos(DbAccess.sortBy(lista,"<"));
            recView.setAdapter(menor);
            menor.notifyDataSetChanged();
        }
        if (id == R.id.itAll) {
            //Toast.makeText(this, "Boton Acercade pulsado!", Toast.LENGTH_SHORT).show();
            recView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
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
            DbAccess.readDb(database, db, lista);
            c.close();
        } else {
            DbAccess.fillDb(database, db, lista);
            c.close();
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
                                DbAccess.removeArt(database, db, lista, position);
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
