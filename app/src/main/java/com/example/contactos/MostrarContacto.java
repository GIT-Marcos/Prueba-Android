package com.example.contactos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Objects;

public class MostrarContacto extends AppCompatActivity {
    TextView tv_nombre, tv_tel, tv_mail, tv_obs;
    Button btn_llamar, btn_copiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_contacto);
        tv_nombre = findViewById(R.id.textView3);
        tv_tel = findViewById(R.id.textView4);
        tv_mail = findViewById(R.id.textView5);
        tv_obs = findViewById(R.id.textView6);
        btn_llamar = findViewById(R.id.button6);
        btn_copiar = findViewById(R.id.button5);

        Bundle b = getIntent().getExtras();
        String nombre = b.getString("key_nombre");
        tv_nombre.setText(nombre);
        String tel = b.getString("key_tel");
        if (tel.equals("")) {
            btn_llamar.setEnabled(false);
        } else {
            tv_tel.setTextColor(ContextCompat.getColor(this, R.color.black));
            tv_tel.setText(tel);
        }
        String mail = b.getString("key_mail");
        if (mail.equals("")) {
            btn_copiar.setEnabled(false);
        } else {
            tv_mail.setTextColor(ContextCompat.getColor(this, R.color.black));
            tv_mail.setText(mail);
        }
        String obs = b.getString("key_obs");
        if (obs.length() > 0) {
            tv_obs.setTextColor(ContextCompat.getColor(this, R.color.black));
            tv_obs.setText(obs);
        }

        Objects.requireNonNull(getSupportActionBar()).setTitle(nombre);
    }

    public void editar(View v) {
        Bundle b = getIntent().getExtras();
        int id = b.getInt("key_id");
        String nombre = b.getString("key_nombre");
        String tel = b.getString("key_tel");
        String mail = b.getString("key_mail");
        String obs = b.getString("key_obs");

        Intent i = new Intent(MostrarContacto.this, Editar.class);
        i.putExtra("key_id", id);
        i.putExtra("key_nombre", nombre);
        i.putExtra("key_tel", tel);
        i.putExtra("key_mail", mail);
        i.putExtra("key_obs", obs);
        startActivity(i);
        finish();
    }

    public void copiar_mail(View v) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("mail", tv_mail.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Se ha copiado el mail al portapapeles", Toast.LENGTH_SHORT).show();
    }

    public void marcar_numero(View v) {
        pedir_permiso();
        String tel = tv_tel.getText().toString();
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:" + tel));
        startActivity(i);
    }

    public void pedir_permiso() {
        int permiso_check = ContextCompat.checkSelfPermission(MostrarContacto.this, android.Manifest.permission.CALL_PHONE);
        if (permiso_check != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para hacer llamadas");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 11);
        } else {
            Log.i("Mensaje", "Se tiene permiso para hacer llamadas");
        }
    }
}