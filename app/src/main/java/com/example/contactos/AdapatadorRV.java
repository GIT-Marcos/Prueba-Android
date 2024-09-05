package com.example.contactos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapatadorRV extends RecyclerView.Adapter<AdapatadorRV.ViewHolder> implements View.OnClickListener {
    ArrayList<Coagulador> lista_array;
    ArrayList<Coagulador> lista_original;

    private View.OnClickListener escuchador;

    public AdapatadorRV(ArrayList<Coagulador> lista_array) {
        this.lista_array = lista_array;
        lista_original = new ArrayList<>();
        lista_original.addAll(lista_array);
    }

    @NonNull
    @Override
    public AdapatadorRV.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflador = LayoutInflater.from(parent.getContext());
        View vista = inflador.inflate(R.layout.filas, parent, false);
        vista.setOnClickListener(this);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapatadorRV.ViewHolder holder, int position) {
        holder.tv_nombre.setText(lista_array.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return lista_array.size();
    }

    public void setOnClickListenerMio(View.OnClickListener escucha) {
        this.escuchador = escucha;
    }

    @Override
    public void onClick(View v) {
        if (escuchador != null) {
            escuchador.onClick(v);
        }
    }

    public void buscador(String texto) {
        int largo = texto.length();
        if (largo == 0) {
            lista_array.clear();
            lista_array.addAll(lista_original);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Coagulador> coleccion = lista_array.stream().filter(i -> i.getNombre().toLowerCase()
                        .contains(texto.toLowerCase())).collect(Collectors.toList());
                lista_array.clear();
                lista_array.addAll(coleccion);
            } else {
                lista_array.clear();
                for (Coagulador c : lista_original) {
                    if (c.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                        lista_array.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nombre = itemView.findViewById(R.id.textView2);
        }
    }
}
