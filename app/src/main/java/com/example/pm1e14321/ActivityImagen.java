package com.example.pm1e14321;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pm1e14321.sqliteconexion.comunicacion;
import com.example.pm1e14321.sqliteconexion.conexion;

import java.io.ByteArrayInputStream;

public class ActivityImagen extends AppCompatActivity {

    conexion con = new conexion(this, comunicacion.NameDatabase, null, 1);

    ImageView imagen;
    TextView nombre;
    Button atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);

        imagen = (ImageView) findViewById(R.id.imageView);
        atras = (Button) findViewById(R.id.btnatrasi);
        nombre = (TextView) findViewById(R.id.txtNombre);


        Bitmap recuperarFoto = buscarImagen(getIntent().getStringExtra("id"));
        imagen.setImageBitmap(recuperarFoto);

        nombre.setText(getIntent().getStringExtra("nombre"));

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte= new Intent(getApplicationContext(),Activitylista.class);
                startActivity(inte);
            }
        });

    }

    public Bitmap buscarImagen(String id) {
        SQLiteDatabase db = con.getWritableDatabase();

        String sql = "SELECT foto FROM contactos WHERE id =" + id;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        Bitmap bitmap = null;
        if (cursor.moveToFirst()) {
            byte[] blob = cursor.getBlob(0);
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            bitmap = BitmapFactory.decodeStream(bais);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return bitmap;
    }
}