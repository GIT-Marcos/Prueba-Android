package com.example.contactos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Coagulador> lista;
    AdapatadorRV adaptador;
    RecyclerView recycler;
    SQLiteAyudante adminSQL = new SQLiteAyudante(MainActivity.this);
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.rv_main);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        //searchView.onActionViewCollapsed();

        lista = new ArrayList<>();
        llena_lista();
        llena_recycler();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adaptador.buscador(s);

                return false;
            }
        });
        //searchView.setIconified(true);
    }

    @Override
    protected void onResume() {
        lista.clear();
        llena_lista();
        llena_recycler();
        searchView.setQuery("",false);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater infladorMenu = getMenuInflater();
        infladorMenu.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ayuda){
            Intent i = new Intent(MainActivity.this, Ayuda.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private void llena_lista() {
        Coagulador coa;
        Cursor cursor = adminSQL.leer_base();
        while (cursor.moveToNext()) {
            coa = new Coagulador();
            coa.setNombre(cursor.getString(1));
            coa.setTelefono(cursor.getString(2));
            coa.setMail(cursor.getString(3));
            coa.setObservaciones(cursor.getString(4));
            lista.add(coa);
        }
        cursor.close();
    }

    private void llena_recycler() {
        adaptador = new AdapatadorRV(lista);
        recycler.setAdapter(adaptador);
        adaptador.setOnClickListenerMio(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre_tarjeta = lista.get(recycler.getChildLayoutPosition(v)).getNombre();
                Cursor cursor = adminSQL.busca_id_por_nombre(nombre_tarjeta);
                cursor.moveToFirst();
                int id_pasada = cursor.getInt(0);
                cursor.close();
                Cursor cursor2 = adminSQL.toma_datos_por_id(id_pasada);
                cursor2.moveToFirst();
                String telefono = cursor2.getString(2);
                String mail = cursor2.getString(3);
                String obs = cursor2.getString(4);
                cursor2.close();
                Intent i = new Intent(MainActivity.this, MostrarContacto.class);
                i.putExtra("key_id",id_pasada);//0
                i.putExtra("key_nombre", nombre_tarjeta);//1
                i.putExtra("key_tel",telefono);//2
                i.putExtra("key_mail", mail);//3
                i.putExtra("key_obs", obs);//4
                startActivity(i);
            }
        });
    }

    public void agregar_contacto(View v) {
        Intent i = new Intent(MainActivity.this, AgregarContacto.class);
        startActivity(i);
    }

}