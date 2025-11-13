package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorCanciones extends RecyclerView.Adapter<AdaptadorCanciones.CancionViewHolder> {
    private ArrayList<Cancion> listaCanciones;
    private final RecyclerCancionesInterface recyclerCancionesInterface;
    public AdaptadorCanciones(ArrayList<Cancion> listaCanciones, RecyclerCancionesInterface recyclerCancionesInterface) {
        this.listaCanciones = listaCanciones;
        this.recyclerCancionesInterface = recyclerCancionesInterface;
    }

    @NonNull
    @Override
    public CancionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(parent.getContext());
        View inflate = from.inflate(R.layout.item_cancion, parent, false);
        return new CancionViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CancionViewHolder holder, int position) {
        Cancion cancion = this.listaCanciones.get(position);

        Glide.with(holder.imagen.getContext())
                .load(cancion.getFoto())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imagen);

                //CAMBIAR PLACEHOLDER Y ERROR POR OTRAS

        holder.titulo.setText(cancion.getTitulo());
        holder.artista.setText(cancion.getArtista());
        holder.duracion.setText(cancion.getDuracion()/60+" minutos");
    }

    @Override
    public int getItemCount() {
        return this.listaCanciones.size();
    }

    public class CancionViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagen;
        public TextView titulo;
        public TextView artista;
        public TextView duracion;

        public CancionViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenCancion);
            titulo = itemView.findViewById(R.id.nombreCancion);
            artista = itemView.findViewById(R.id.nombreBanda);
            duracion = itemView.findViewById(R.id.duracionCancion);

            // Click normal
            itemView.setOnClickListener(view -> {
                if (recyclerCancionesInterface != null) {
                    int pos = getAbsoluteAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerCancionesInterface.onItemClick(pos);
                    }
                }
            });

            // Long click - Menú contextual
            itemView.setOnLongClickListener(view -> {
                if (recyclerCancionesInterface != null) {
                    int pos = getAbsoluteAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerCancionesInterface.onItemLongClick(pos);  // Aquí se pasará la posición
                    }
                }
                return true;  // Indicamos que manejamos el evento
            });
        }
    }
}
