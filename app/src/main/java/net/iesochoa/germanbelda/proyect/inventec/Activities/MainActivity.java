package net.iesochoa.germanbelda.proyect.inventec.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import net.iesochoa.germanbelda.proyect.inventec.Adapter.AdaptadorArticulos;
import net.iesochoa.germanbelda.proyect.inventec.Comunication.SftpConnection;
import net.iesochoa.germanbelda.proyect.inventec.Database.ArticulosContract;
import net.iesochoa.germanbelda.proyect.inventec.Database.ArticulosDbHelper;
import net.iesochoa.germanbelda.proyect.inventec.Database.DbAccess;
import net.iesochoa.germanbelda.proyect.inventec.Model.Articulo;
import net.iesochoa.germanbelda.proyect.inventec.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    private static final String ARRAYLIST_DATA = "ARRAYLIST_DATA";
    public static final String RUTA_FILE_DB = "/data/data/net.iesochoa.germanbelda.proyect.inventec/databases/articulos.db";
    public static final String RUTA_FILE_UPLOAD = "/var/www/html/downloads/app/upload";
    private static final int EAN13_CODE = 13;
    private RecyclerView recView;
    public ArrayList<Articulo> lista;
    public AdaptadorArticulos adaptador;
    private EditText etinputCodigo;
    private TextView tvTitulo;
    private TextView tvLeido;
    private ImageButton ibKeyboard;

    /**
     * Metodo principal en el que trato los articulos con su recyclerView y su adaptador,
     * y los eventos del boton de añadir articulos asi como la entrada de datos mediante
     * teclado o bluetooth.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etinputCodigo = (EditText) findViewById(R.id.etInputCodigo);
        tvTitulo = (TextView) findViewById(R.id.tvTitulo);
        tvLeido = (TextView) findViewById(R.id.tvLeidos);
        ibKeyboard = (ImageButton) findViewById(R.id.ibKeyboard);
        etinputCodigo.setVisibility(View.INVISIBLE);



        //Compruebo si tengo datos almacenados de movimientos anteriores
        if (savedInstanceState != null) {
            lista = savedInstanceState.getParcelableArrayList(ARRAYLIST_DATA);
        } else {
            //inicialización de la lista de ejemplo
            initArrayDb();
        }

        adaptador = new AdaptadorArticulos(this,lista);

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
            }
        });
        //Control de la visualizacion y funcionalidad del teclado.
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
        //Comprobación de la entrada de datos de formato y forma correcta.
        etinputCodigo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_UP)) { //Solo ejecuta al soltar la tecla enter
                    if (etinputCodigo.getText().length() == EAN13_CODE) {
                        ArticulosDbHelper db = new ArticulosDbHelper(MainActivity.this);
                        SQLiteDatabase database = db.getWritableDatabase();
                        Articulo articulo = new Articulo(etinputCodigo.getText().toString(), "", "1", "0");
                        if (!DbAccess.findArt(database, db, articulo)) {
                            lista.add(0, articulo); //Agrega al principio de la lista para mostrarlo el primero
                            DbAccess.insertArt(database, db, articulo);
                            //Toast.makeText(MainActivity.this, "Articulo insertado", Toast.LENGTH_SHORT).show();
                            etinputCodigo.setText("");
                            etinputCodigo.requestFocus();
                            adaptador.updateUI();
                            return true;
                        } else {
                            //Toast.makeText(MainActivity.this, "Articulo " + articulo.getCodigo() + " encontrado", Toast.LENGTH_SHORT).show();
                            for (Articulo art : lista) {
                                if (art.getCodigo().equals(articulo.getCodigo())) {
                                    int suma = Integer.parseInt(articulo.getLeidos()) + Integer.parseInt(art.getLeidos());
                                    art.setLeidos(String.valueOf(suma));
                                    Log.e("LEIDO", "Suma total ---> " + suma);
                                }
                            }
                            adaptador.updateUI();
                            etinputCodigo.setText("");
                            etinputCodigo.requestFocus();
                            return true;
                        }
                    } else {
                        Toast.makeText(MainActivity.this, R.string.Noreconocido, Toast.LENGTH_SHORT).show();
                        etinputCodigo.setText("");
                        etinputCodigo.requestFocus();
                        return true;
                    }
                } else {
                    return false;
                }
            }
        });
        adaptador.updateUI();
    }

    /**
     * Metodo que guarda una instancia de los datos presentes antes de cerrar la actividad principal
     * para poder restaurarlos posteriormente si es necesario.
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelableArrayList(ARRAYLIST_DATA, lista);

    }

    /**
     * Metodo que refresca el estado del adaptador cuando se vuelve a la pantalla principal.
     */
    @Override
    protected void onResume() {
        super.onResume();

        adaptador.updateUI();
    }

    /**
     * Metodo para la creación del menu de opciones de la barra principal.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Metodo que da funcionalidad a cada una de las opciones de menu.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itAjustes) {
            String[] listItems = new String[]{getString(R.string.excedente),
                                              getString(R.string.faltante),
                                              getString(R.string.descuadres),
                                              getString(R.string.todolosart)};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.mostrar));

            int checkedItem = -1; //this will checked the item when user open the dialog
            builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    switch (which) {
                        case 0:
                            adaptador.filter(">");
                            break;
                        case 1:
                            adaptador.filter("<");
                            break;
                        case 2:
                            adaptador.filter("<>");
                            break;
                        case 3:
                            adaptador.filter("");
                            break;
                        default:
                            break;
                    }
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        if (id == R.id.itAcercade) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            LayoutInflater inflater = getLayoutInflater();

            builder.setView(inflater.inflate(R.layout.acercade, null));

            AlertDialog dialogIcon = builder.create();
            dialogIcon.show();
        }

        if (id == R.id.itSubirDatos){
            SftpConnection conexion = new SftpConnection();
            conexion.execute();
            Toast.makeText(this, R.string.uploadok, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo de inicialización de la base de datos de prueba, llamo a los metodos de lectura o
     * rellenado de según sea necesario.
     */
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

    /**
     * Metodo que elimina un articulo seleccionado en el recyclerView en base a su posición,
     * es elminado de la DB y de la lista.
     * @param position
     */
    public void eliminarArt(final int position) {
        final ArticulosDbHelper db = new ArticulosDbHelper(this);
        final SQLiteDatabase database = db.getWritableDatabase();
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.eliminararttitle)
                .setMessage(R.string.questiondelete)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DbAccess.removeArt(database, db,lista, position);
                                adaptador.removeItem(position);
                                adaptador.updateUI();
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        builder.show();
    }
}
