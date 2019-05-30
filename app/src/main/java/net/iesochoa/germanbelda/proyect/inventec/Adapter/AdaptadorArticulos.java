package net.iesochoa.germanbelda.proyect.inventec.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.iesochoa.germanbelda.proyect.inventec.Model.Articulo;
import net.iesochoa.germanbelda.proyect.inventec.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorArticulos extends RecyclerView.Adapter<AdaptadorArticulos.ArticulosViewHolder> implements View.OnClickListener {
    private ArrayList<Articulo> datos, filtro;
    private View.OnClickListener listener;
    Context context;


    public AdaptadorArticulos(Context context,ArrayList<Articulo> datos) {
        this.datos = datos;
        this.filtro = new ArrayList<>();
        this.filtro.addAll(this.datos);
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptadorArticulos.ArticulosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_vista_articulo, viewGroup, false);
        itemView.setOnClickListener(this);
        ArticulosViewHolder articulovh = new ArticulosViewHolder(itemView);

        return articulovh;
    }

    public class ArticulosViewHolder extends RecyclerView.ViewHolder {

        private TextView leido;
        private TextView codigo;
        private TextView totales;
        private ImageView advertencia;
        int colorLeido, colorTotales;

        public ArticulosViewHolder(View itemView) {
            super(itemView);

            leido = (TextView) itemView.findViewById(R.id.tvLeidos);
            codigo = (TextView) itemView.findViewById(R.id.tvCodigo);
            totales = (TextView) itemView.findViewById(R.id.tvTotales);
            advertencia = (ImageView) itemView.findViewById(R.id.ivAdvertencia);

        }
    }

    /**
     * Metodo que trata la vista que recibe con el articulo y su posicion y construye el item
     * según los valores tanto leidos como totales.
     * @param articulosViewHolder
     * @param posicion
     */
    @Override
    public void onBindViewHolder(@NonNull ArticulosViewHolder articulosViewHolder, int posicion) {


        Articulo item = filtro.get(posicion);

        articulosViewHolder.leido.setText(item.getLeidos()); // Introduzco el valor de cada articulo en el campo leido
        if (!item.getNombre().isEmpty()) {
            articulosViewHolder.codigo.setText(item.getNombre());
        } else {
            articulosViewHolder.codigo.setText(item.getCodigo());
        }
        articulosViewHolder.totales.setText(item.getTotales());
        //Muestro el icono si las cantidades son diferentes
        if (item.getTotales().equals(articulosViewHolder.leido.getText().toString())) {
            articulosViewHolder.advertencia.setVisibility(View.INVISIBLE);
        } else {
            articulosViewHolder.advertencia.setVisibility(View.VISIBLE);
        }
        //Establezco el color del texto para los articulos leidos
        articulosViewHolder.colorLeido = Integer.parseInt(articulosViewHolder.leido.getText().toString());
        articulosViewHolder.colorTotales = Integer.parseInt(articulosViewHolder.totales.getText().toString());
        if (articulosViewHolder.colorLeido > articulosViewHolder.colorTotales) {
            articulosViewHolder.leido.setTextColor(Color.GREEN);
        } else if (articulosViewHolder.colorLeido < articulosViewHolder.colorTotales) {
            articulosViewHolder.leido.setTextColor(Color.RED);
        } else {
            articulosViewHolder.leido.setTextColor(Color.GRAY);
        }
    }

    /**
     * Metodo para controlar el filtrado de la lista en base al input de la opcion de menu.
     * @param text
     */

    public void filter(final String text) {

                // Limpio la lista filtrada
                filtro.clear();
                // Si no hay valor en el string la lista original la copio en la filtrada
                if (TextUtils.isEmpty(text)) {
                    filtro.addAll(datos);
                } else {
                    // Busco en la lista original y lo añado a la de filtros
                    for (Articulo item : datos) {
                        int leidos = Integer.parseInt(item.getLeidos());
                        int totales = Integer.parseInt(item.getTotales());

                        switch (text) {
                            case "<":
                                if (leidos < totales) {
                                    Log.e("NO",leidos+" ----- "+totales + " simbolo " +text);
                                    // Añado articulo
                                    filtro.add(item);
                                }
                                break;
                            case ">":
                                if (leidos > totales) {
                                    Log.e("SALIDA",leidos+" ----- "+totales + " simbolo " +text);
                                    // Añado articulo
                                    filtro.add(item);
                                    Log.e("INDICE",""+filtro.indexOf(item));
                                }
                                break;
                            case "<>":
                                if ((leidos > totales) || (leidos < totales)) {
                                    Log.e("NO",leidos+" ----- "+totales + " simbolo " +text);
                                    // Añado articulo
                                    filtro.add(item);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
                notifyDataSetChanged();
    }

    /**
     * Metodo que devuelve el tamaño de la lista auxiliar
     * @return
     */
    @Override
    public int getItemCount() {

        return filtro.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {

        this.listener = listener;
    }

    /**
     * Metodo que borra un articulo de la lista principal segun su posición.
     * @param posicion
     */
    public void removeItem(int posicion){

        datos.remove(posicion);
        notifyDataSetChanged();

    }

    /**
     * Metodo para refrescar siempre la lista secundaria de filtrado.
     */
   public void updateUI(List<Articulo> listaArticulos){
        filtro.clear();
        filtro.addAll(listaArticulos);
        datos.addAll(listaArticulos);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onClick(v);
    }
}
