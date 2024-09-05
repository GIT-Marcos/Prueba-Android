package com.example.contactos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLiteAyudante extends android.database.sqlite.SQLiteOpenHelper {
    private Context contexto;
    private static final int VRSION = 1;
    private static final String NOMBRE_BASE = "contatcos.db";
    private static final String NOMBRE_TABLA = "tabla_contactos";
    private static final String ID_CONTACTO = "id_contacto";
    private static final String COLUMNA_NOMBRE = "nombre";
    private static final String COLUMNA_TEL = "telefono";
    private static final String COLUMNA_MAIL = "mail";
    private static final String COLUMNA_OBSERVACIONES = "observaciones";

    public SQLiteAyudante(@Nullable Context context) {
        super(context, NOMBRE_BASE, null, VRSION);
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sentencia = "CREATE TABLE " + NOMBRE_TABLA + " (" + ID_CONTACTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMNA_NOMBRE + " TEXT NOT NULL, " + COLUMNA_TEL + " TEXT, " + COLUMNA_MAIL + " TEXT, " + COLUMNA_OBSERVACIONES + " TEXT)";
        db.execSQL(sentencia);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor leer_base() {
        String sentencia = "SELECT * FROM " + NOMBRE_TABLA + " ORDER BY " + COLUMNA_NOMBRE;
        SQLiteDatabase manipulador = this.getReadableDatabase();
        Cursor cursor = manipulador.rawQuery(sentencia, null);
        return cursor;
    }

    public void agregar_contacto(String nombre, String telefono, String mail, String observaciones) {
        SQLiteDatabase manipulador = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put(COLUMNA_NOMBRE, nombre);
        registro.put(COLUMNA_TEL, telefono);
        registro.put(COLUMNA_MAIL, mail);
        registro.put(COLUMNA_OBSERVACIONES, observaciones);
        long resultado = manipulador.insert(NOMBRE_TABLA, null, registro);
        if (resultado == -1) {
            Toast.makeText(contexto, "Ha ocurrido un error al cargar el contacto", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(contexto, "Se ha agregado a: " + nombre + " correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor leer_nombre(String nombre) {
        String sentencia = "SELECT " + COLUMNA_NOMBRE + " FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_NOMBRE + "= '" + nombre + "'";
        SQLiteDatabase manipulador = this.getReadableDatabase();
        Cursor cursor = manipulador.rawQuery(sentencia, null);
        return cursor;
    }

    public Cursor busca_id_por_nombre(String nombre) {
        String sentencia = "SELECT " + ID_CONTACTO + " FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_NOMBRE + "= '" + nombre + "'";
        SQLiteDatabase manipulador = this.getReadableDatabase();
        Cursor cursor = manipulador.rawQuery(sentencia, null);
        return cursor;
    }

    public Cursor toma_datos_por_id(int id) {
        String sentencia = "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + ID_CONTACTO + "= '" + id + "'";
        SQLiteDatabase manipulador = this.getReadableDatabase();
        Cursor cursor = manipulador.rawQuery(sentencia, null);
        return cursor;
    }

    public void actualizar_contacto(int id, String nombre, String tel, String mail, String observaciones) {
        ContentValues registro = new ContentValues();
        registro.put(COLUMNA_NOMBRE, nombre);
        registro.put(COLUMNA_TEL, tel);
        registro.put(COLUMNA_MAIL, mail);
        registro.put(COLUMNA_OBSERVACIONES, observaciones);
        SQLiteDatabase manipulador = this.getWritableDatabase();
        int filas_afectadas = manipulador.update(NOMBRE_TABLA, registro, ID_CONTACTO + "= '" + id + "'", null);
        if (filas_afectadas > 0) {
            Toast.makeText(contexto, "Se ha editado a: " + nombre, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(contexto, "Ha ocurrido un error al editar", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar_por_id(int id, String nombre) {
        SQLiteDatabase manipulador = this.getWritableDatabase();
        int filas_afectadas = manipulador.delete(NOMBRE_TABLA, ID_CONTACTO + "= '" + id + "'", null);
        if (filas_afectadas > 0) {
            Toast.makeText(contexto, "Se ha eliminado a: " + nombre, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(contexto, "Ha ocrrido un error al elimiinar a: " + nombre, Toast.LENGTH_SHORT).show();
        }
    }
}

