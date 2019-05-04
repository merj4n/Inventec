package net.iesochoa.germanbelda.proyect.inventec.ADAPTER;

import android.graphics.Color;
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

import static net.iesochoa.germanbelda.proyect.inventec.Activities.MainActivity.lista;

public class AdaptadorArticulos extends RecyclerView.Adapter<AdaptadorArticulos.ArticulosViewHolder>
        implements View.OnClickListener {
    private ArrayList<Articulo> datos;
    private View.OnClickListener listener;
    public static AdaptadorArticulos adaptador = new AdaptadorArticulos(lista);

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
    public void onBindViewHolder(@NonNull AdaptadorArticulos.ArticulosViewHolder articulosViewHolder, int posicion) {
        Articulo item = datos.get(posicion);
        articulosViewHolder.bindArticulo(item,posicion);
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
        int colorA,colorB;

        public ArticulosViewHolder(View itemView) {
            super(itemView);

            leido = (TextView) itemView.findViewById(R.id.tvLeidos);
            codigo = (TextView) itemView.findViewById(R.id.tvCodigo);
            totales = (TextView) itemView.findViewById(R.id.tvTotales);
            advertencia = (ImageView) itemView.findViewById(R.id.ivAdvertencia);
        }

        public void bindArticulo(Articulo t,int posicion) {

            if (!t.getNombre().isEmpty()) {
                codigo.setText(t.getNombre());
            } else {
                codigo.setText(t.getCodigo());
            }
            totales.setText(t.getTotales());
            //Muestro el icono si las cantidades son diferentes
            if(t.getTotales().equals(leido.getText().toString())){
                advertencia.setVisibility(View.INVISIBLE);
            }
            colorA=Integer.parseInt(leido.getText().toString());
            colorB=Integer.parseInt(totales.getText().toString());
            if(colorA>colorB) {
                leido.setTextColor(Color.parseColor("#34CA30"));
            }
            if(colorA<colorB){
                leido.setTextColor(Color.parseColor("#F50606"));
            }
        }
    }
}
