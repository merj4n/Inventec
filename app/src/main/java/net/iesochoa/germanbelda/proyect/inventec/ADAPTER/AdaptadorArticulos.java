package net.iesochoa.germanbelda.proyect.inventec.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.iesochoa.germanbelda.proyect.inventec.Activities.Articulo;
import net.iesochoa.germanbelda.proyect.inventec.R;

import java.util.ArrayList;

public class AdaptadorArticulos extends RecyclerView.Adapter<AdaptadorArticulos.ArticulosViewHolder>
        implements View.OnClickListener {

    private ArrayList<Articulo> datos;
    private View.OnClickListener listener;
    private TextView tvid;
    private TextView tvtotales;

    public AdaptadorArticulos(ArrayList<Articulo> datos) {
        this.datos = datos;
    }
    @NonNull
    @Override
    public AdaptadorArticulos.ArticulosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main, viewGroup, false);

        itemView.setOnClickListener(this);

        ArticulosViewHolder articulovh = new ArticulosViewHolder(itemView);

        return articulovh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorArticulos.ArticulosViewHolder articulosViewHolder, int posicion) {
        Articulo item = datos.get(posicion);
        articulosViewHolder.bindArticulo(item);
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
        if(listener != null)
            listener.onClick(v);
    }

    public class ArticulosViewHolder extends RecyclerView.ViewHolder{

        private TextView id;
        private TextView totales;
        private ImageView advertencia;

        public ArticulosViewHolder(View itemView) {
            super(itemView);

            id = (TextView)itemView.findViewById(R.id.tvId);
            totales = (TextView)itemView.findViewById(R.id.tvTotales);
            advertencia = (ImageView)itemView.findViewById(R.id.ivAdvertencia);
        }

        public void bindArticulo(Articulo t) {
            tvid.setText(t.getId());
            tvtotales.setText(t.getTotales());
        }

    }
}