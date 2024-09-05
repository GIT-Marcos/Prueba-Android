package com.example.contactos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class Editar extends AppCompatActivity {
    TextView tv_aviso;
    EditText et_nombre, et_tel, et_mail, et_obs;
    SQLiteAyudante base = new SQLiteAyudante(Editar.this);
    SQLiteDatabase manipulador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        tv_aviso = findViewById(R.id.textView9);
        et_nombre = findViewById(R.id.editTextTextPersonName2);
        et_tel = findViewById(R.id.editTextPhone2);
        et_mail = findViewById(R.id.editTextTextEmailAddress2);
        et_obs = findViewById(R.id.editTextTextMultiLine2);

        Bundle b = getIntent().getExtras();
        String nombre_traido = b.getString("key_nombre");
        String tel_traido = b.getString("key_tel");
        String mail_traido = b.getString("key_mail");
        String obs_traido = b.getString("key_obs");

        et_nombre.setText(nombre_traido);
        et_tel.setText(tel_traido);
        et_mail.setText(mail_traido);
        et_obs.setText(obs_traido);

        et_nombre.setHint(nombre_traido);
        if (tel_traido.equals("")) {
            et_tel.setHint("No hay teléfono");
        } else {
            et_tel.setHint(tel_traido);
        }
        if (mail_traido.equals("")) {
            et_mail.setHint("no hay mail");
        } else {
            et_mail.setHint(mail_traido);
        }
        if (obs_traido.equals("")) {
            et_obs.setHint("no hay observaciones");
        } else {
            et_obs.setHint(obs_traido);
        }
    }

    public void confirmar_edicion(View v) {
        String nom = et_nombre.getText().toString().trim();
        if (nom.equals("")) {
            Toast.makeText(this, "Hay campos obligatorios vacios", Toast.LENGTH_SHORT).show();
            tv_aviso.setVisibility(View.VISIBLE);
        } else {
            String nombre = nom.toLowerCase().substring(0, 1).toUpperCase() + nom.substring(1);
            Bundle b = getIntent().getExtras();
            int id = b.getInt("key_id");
            manipulador = base.getWritableDatabase();
            Cursor cursor = manipulador.rawQuery("SELECT nombre FROM tabla_contactos WHERE nombre= '" + nombre + "' AND id_contacto IS NOT " + id,
                    null);
            if (cursor.moveToFirst()) {
                Toast.makeText(this, "Ya existe un contacto con ese nombre", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Alerta!")
                        .setMessage("¿Estás seguro que deseas guardar los cambios que has hecho?")
                        .setCancelable(true)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cursor.close();
                                String tel = et_tel.getText().toString();
                                String mail = et_mail.getText().toString().trim();
                                String obs = et_obs.getText().toString().trim();
                                base.actualizar_contacto(id, nombre, tel, mail, obs);
                                finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        }
    }

    public void eliminar(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alerta!")
                .setMessage("¿Estás seguro que deseas borrar este contacto?")
                .setCancelable(true)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nombre = et_nombre.getText().toString();
                        Bundle b = getIntent().getExtras();
                        int id = b.getInt("key_id");
                        base.eliminar_por_id(id, nombre);

                        // Snackbar snackbar = Snackbar.make(constraintLayout_editar,"Se ha eliminado a: "+nombre, BaseTransientBottomBar.LENGTH_LONG);
                        // snackbar.show();

                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
    }
}