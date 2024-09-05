package com.example.contactos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class AgregarContacto extends AppCompatActivity {
    TextView tv_aviso;
    EditText et_nombre, et_telefono, et_mail, et_obs;
    SQLiteAyudante adminSQL = new SQLiteAyudante(AgregarContacto.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);
        tv_aviso = findViewById(R.id.textView11);
        et_nombre = findViewById(R.id.editTextTextPersonName);
        et_telefono = findViewById(R.id.editTextPhone);
        et_mail = findViewById(R.id.editTextTextEmailAddress);
        et_obs = findViewById(R.id.editTextTextMultiLine);

       //et_nombre.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

    }

    public void agregar(View v) {
        String nombre = et_nombre.getText().toString().trim();
        String tel = et_telefono.getText().toString();
        String mail = et_mail.getText().toString().trim();
        String obs = et_obs.getText().toString().trim();

        if (nombre.equals("")) {
            Toast.makeText(this, "Hay campos obligatorios", Toast.LENGTH_SHORT).show();
            tv_aviso.setVisibility(View.VISIBLE);
        } else {
            String nombre_mayus = nombre.toLowerCase().substring(0, 1).toUpperCase() + nombre.substring(1);
            Cursor cursor = adminSQL.leer_nombre(nombre_mayus);
            if (cursor.moveToFirst()) {
                Toast.makeText(this, "Ya existe un contacto con ese nombre", Toast.LENGTH_SHORT).show();
            } else {
                adminSQL.agregar_contacto(nombre_mayus, tel, mail, obs);
                et_nombre.setText("");
                et_obs.setText("");
                et_mail.setText("");
                et_telefono.setText("");
            }
        }
    }

}