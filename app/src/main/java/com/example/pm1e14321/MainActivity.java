package com.example.pm1e14321;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pm1e14321.sqliteconexion.comunicacion;
import com.example.pm1e14321.sqliteconexion.conexion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {


    EditText nombre,telefono,nota;

    String newtelefon;

    String  paises;

   Spinner seleccionar;
    ImageView imagen;
    Button btnsalvar,btncontactos;

    String codigo;

    Bitmap imagenb;

    FloatingActionButton tomar;
    static final int peticion_acceso_camara = 101;
    static final int peticion_toma_fotografica = 102;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] codigopais = {"Selecciona un pais","(+504) Honduras","(+502) Guatemala",
                "(+506) Costa Rica","(+503) El Salvador","(+505) Nicaragua","(+507) Panama",
                "(+501) Belice"};
        nombre=(EditText) findViewById(R.id.Txtnombre);
        telefono=(EditText) findViewById(R.id.Txttelefono);
        nota=(EditText) findViewById(R.id.datenota);
        imagen = (ImageView) findViewById(R.id.imagew);
        btncontactos = (Button) findViewById(R.id.btncontactos);
        btnsalvar = (Button) findViewById(R.id.btnsalvar);
        tomar = (FloatingActionButton) findViewById(R.id.agregar);

        seleccionar = (Spinner) findViewById(R.id.spinerpais);

        ArrayAdapter<String> adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item, codigopais);
        seleccionar.setAdapter(adp);

        seleccionar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(codigopais[position]=="Selecciona un pais") {
                    codigo="";
                }
                else if (codigopais[position]=="(+504) Honduras") {
                    paises="Honduras";
                    codigo="504";
                }
                else if (codigopais[position]=="(+502) Guatemala") {
                    paises="Guatemala";
                    codigo="502";
                }
                else if (codigopais[position]=="(+506) Costa Rica") {
                    paises="Costa Rica";
                    codigo="506";
                }
                else if (codigopais[position]=="(+503) El Salvador") {
                    paises="El Salvador";
                    codigo="503";
                }
                else if (codigopais[position]=="(+505) Nicaragua") {
                    paises="Nicaragua";
                    codigo="505";
                }
                else if (codigopais[position]=="(+507) Panama") {
                    paises="Panama";
                    codigo="507";
                }
                else if (codigopais[position]=="(+501) Belice") {
                    paises="Belice";
                    codigo="501";
                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisos();
            }
        });

        btnsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (codigo == ""){
                    Toast.makeText(getApplicationContext(), "Debe de seleccionar  un Pais" ,Toast.LENGTH_LONG).show();
                }else  if (nombre.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Debe de escribir un nombre" ,Toast.LENGTH_LONG).show();
                }else if (telefono.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Debe de escribir un telefono" ,Toast.LENGTH_LONG).show();
                }
                else if (nota.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Debe de escribir una nota" ,Toast.LENGTH_LONG).show();
                }else {
                    newtelefon = codigo+""+telefono.getText().toString();
                    addContac(imagenb);
                }
            }
        });

        btncontactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Activitylista.class);
                startActivity(intent);
            }
        });

    }

    private void permisos() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},peticion_acceso_camara);
        }
        else
        {
            TomarFoto();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == peticion_acceso_camara)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                TomarFoto();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Permiso denegado", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void TomarFoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!= null)
        {
            startActivityForResult(intent, peticion_toma_fotografica);
        }
    }


    protected void onActivityResult(int requescode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requescode, resultCode, data);

        if (requescode==peticion_toma_fotografica && resultCode== RESULT_OK){
            Bundle extra =data.getExtras();
            imagenb =(Bitmap)extra.get("data");
            imagen.setImageBitmap(imagenb);
        } else if (resultCode==RESULT_OK) {
            Uri imagenuri =data.getData();
            imagen.setImageURI(imagenuri);
        }
    }



    private void addContac(Bitmap bitmap) {
        try {
            conexion con = new conexion(this,comunicacion.NameDatabase,null,1);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            SQLiteDatabase db =  con.getWritableDatabase();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            byte[] ArrayFoto =stream.toByteArray();

            ContentValues valores = new ContentValues();
            valores.put(comunicacion.nombreCompleto, nombre.getText().toString());
            valores.put(comunicacion.telefono, newtelefon);
            valores.put(comunicacion.pais,paises);
            valores.put(comunicacion.nota, nota.getText().toString());
            valores.put(String.valueOf(comunicacion.foto),ArrayFoto);

            Long Result = db.insert(comunicacion.tablacontactos,comunicacion.id, valores);

            Toast.makeText(this,"Contacto Agregado", Toast.LENGTH_SHORT).show();
            db.close();
        }
        catch (Exception exception)
        {
            Toast.makeText(this,"Necesita tomar una foto", Toast.LENGTH_SHORT).show();
        }

    }
    }