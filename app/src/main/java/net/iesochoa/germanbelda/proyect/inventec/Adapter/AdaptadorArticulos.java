package net.iesochoa.germanbelda.proyect.inventec.Adapter;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.iesochoa.germanbelda.proyect.inventec.Pojo.Articulo;
import net.iesochoa.germanbelda.proyect.inventec.R;

import java.util.ArrayList;

public class AdaptadorArticulos extends RecyclerView.Adapter<AdaptadorArticulos.ArticulosViewHolder> implements View.OnClickListener {
    private ArrayList<Articulo> datos;
    private View.OnClickListener listener;


    public AdaptadorArticulos(ArrayList<Articulo> datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public AdaptadorArticulos.ArticulosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_edicion_articulo, viewGroup, false);
        itemView.setOnClickListener(this);
        ArticulosViewHolder articulovh = new ArticulosViewHolder(itemView);

        return articulovh;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticulosViewHolder articulosViewHolder, int posicion) {

        Articulo item = datos.get(posicion);
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
        articulosViewHolder.colorA = Integer.parseInt(articulosViewHolder.leido.getText().toString());
        articulosViewHolder.colorB = Integer.parseInt(articulosViewHolder.totales.getText().toString());
        if (articulosViewHolder.colorA > articulosViewHolder.colorB) {
            articulosViewHolder.leido.setTextColor(Color.parseColor("#34CA30"));
        } else if (articulosViewHolder.colorA < articulosViewHolder.colorB) {
            articulosViewHolder.leido.setTextColor(Color.parseColor("#F50606"));
        } else {
            articulosViewHolder.leido.setTextColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {

        return datos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {

        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onClick(v);
    }

    public class ArticulosViewHolder extends RecyclerView.ViewHolder {

        private TextView leido;
        private TextView codigo;
        private TextView totales;
        private ImageView advertencia;
        int colorA, colorB;

        public ArticulosViewHolder(View itemView) {
            super(itemView);

            leido = (TextView) itemView.findViewById(R.id.tvLeidos);
            codigo = (TextView) itemView.findViewById(R.id.tvCodigo);
            totales = (TextView) itemView.findViewById(R.id.tvTotales);
            advertencia = (ImageView) itemView.findViewById(R.id.ivAdvertencia);
        }
    }
}
