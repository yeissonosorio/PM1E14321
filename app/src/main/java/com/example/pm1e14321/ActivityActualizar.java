package com.example.pm1e14321;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm1e14321.sqliteconexion.comunicacion;
import com.example.pm1e14321.sqliteconexion.conexion;

public class ActivityActualizar extends AppCompatActivity {

    EditText nombre,numero,nota;
    Spinner codpais;

    Button Actualizar,atras;

    String newtelefon;

    String paises;

    String id;

    String codigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        String[] codigopais = {"Selecciona un pais","(+504) Honduras","(+502) Guatemala",
                "(+506) Costa Rica","(+503) El Salvador","(+505) Nicaragua","(+507) Panama",
                "(+501) Belice"};

        nombre=(EditText) findViewById(R.id.txtnombreA);
        numero=(EditText) findViewById(R.id.txttelefonoA);
        nota=(EditText) findViewById(R.id.datenotaA);
        atras = (Button) findViewById(R.id.btnatrasA);
        Actualizar = (Button) findViewById(R.id.btnActualizarA);

        nombre.setText(getIntent().getStringExtra("nombre"));
        numero.setText(getIntent().getStringExtra("telefono"));
        nota.setText(getIntent().getStringExtra("nota"));
        id = getIntent().getStringExtra("codigo");



        codpais = (Spinner) findViewById(R.id.txtcodi);

        ArrayAdapter<String> adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item, codigopais);
        codpais.setAdapter(adp);

        codpais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        Actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codigo == ""){
                    Toast.makeText(getApplicationContext(), "Debe de seleccionar  un Pais" ,Toast.LENGTH_LONG).show();
                }else  if (nombre.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Debe de escribir un nombre" ,Toast.LENGTH_LONG).show();
                }else if (numero.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Debe de escribir un telefono" ,Toast.LENGTH_LONG).show();
                }
                else if (nota.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Debe de escribir una nota" ,Toast.LENGTH_LONG).show();
                }else {
                    newtelefon = codigo + "" + numero.getText().toString();
                    EditarContacto();
                }
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(),Activitylista.class);
                startActivity(inte);
            }
        });
    }

    private void EditarContacto() {
        conexion con = new conexion(this, comunicacion.NameDatabase,null,1);
        SQLiteDatabase db = con.getWritableDatabase();

        String ObjCodigo =id;

        ContentValues valores = new ContentValues();

        valores.put(comunicacion.nombreCompleto, nombre.getText().toString());
        valores.put(comunicacion.telefono, newtelefon);
        valores.put(comunicacion.pais, paises);
        valores.put(comunicacion.nota, nota.getText().toString());


        try {
            db.update(comunicacion.tablacontactos,valores, comunicacion.id +" = "+ ObjCodigo, null);
            db.close();
            Toast.makeText(getApplicationContext(),"Contacto Actualizado Correctamente", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Activitylista.class);
            startActivity(intent);
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"No se pudo actualizo", Toast.LENGTH_SHORT).show();
        }
    }
}