package com.example.pm1e14321;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pm1e14321.modelo.contacto;
import com.example.pm1e14321.sqliteconexion.comunicacion;
import com.example.pm1e14321.sqliteconexion.conexion;

import java.util.ArrayList;

public class Activitylista extends AppCompatActivity {

    conexion con;

    EditText busqueda;


    int codigo;

    int toque;

    String nombre,numero,nota;

    ListView listacontactos;

    ArrayList<contacto> contacp;

    ArrayList<String>  Arreglocontacto;

    Button Atras,Imgane,Actualizar,borrar,compartir;

    Intent intent;
    contacto contactos;

    static final int PETICION_LLAMADA_TELEFONO = 102;

    int previousPosition = 1;
    int count=1;
    long previousMil=0;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitylista);

        try {
            con = new conexion(this, comunicacion.NameDatabase,null,1);
            listacontactos = (ListView) findViewById(R.id.ListaContactos);
            GetContacto();

            ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1,Arreglocontacto);
            listacontactos.setAdapter(adp);

            busqueda = (EditText) findViewById(R.id.tctbusq);

            busqueda.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adp.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            listacontactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(previousPosition==position)
                        {
                            count++;
                            if(count==2 && System.currentTimeMillis()-previousMil<=1000)
                            {
                                numero=contacp.get(position).getNumeroContacto();
                                //Toast.makeText(getApplicationContext(), "Doble Click ",Toast.LENGTH_LONG).show();
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setTitle("Acción");
                                alertDialogBuilder
                                        .setMessage("¿Desea llamar a "+contacp.get(position).getNombreContacto()+"?")
                                        .setCancelable(false)
                                        .setPositiveButton("SI",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                try{
                                                    permisoLlamada();
                                                }catch (Exception ex){
                                                    ex.toString();
                                                }

                                                Toast.makeText(getApplicationContext(),"Realizando llamada",
                                                        Toast.LENGTH_SHORT).show();

                                            }
                                        })
                                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                count=1;
                                toque=0;
                            }
                            else {
                                toque=1;
                                codigo=contacp.get(position).getCodigo();
                                nombre=contacp.get(position).getNombreContacto();
                                numero=contacp.get(position).getNumeroContacto();
                                nota= contacp.get(position).getNota();
                            }
                        }
                        else
                        {
                            toque=1;
                            codigo=contacp.get(position).getCodigo();
                            nombre=contacp.get(position).getNombreContacto();
                            numero=contacp.get(position).getNumeroContacto();
                            nota= contacp.get(position).getNota();
                            previousPosition=position;
                            count=1;
                            previousMil=System.currentTimeMillis();
                            contactos = contacp.get(position);

                        }
                    }
            });

        }
        catch (Exception e){

        }



        Actualizar= (Button) findViewById(R.id.btnActualizar);
        Imgane = (Button) findViewById(R.id.btnimagen);
        Atras = (Button) findViewById(R.id.btnatras);
        borrar = (Button) findViewById(R.id.btnEliminar);
        compartir = (Button) findViewById(R.id.btncompartir);

        Actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(toque==1){
                Intent intent = new Intent(getApplicationContext(),ActivityActualizar.class);
                intent.putExtra("codigo",codigo+"");
                intent.putExtra("nombre",nombre);
                intent.putExtra("telefono",numero.substring(3));
                intent.putExtra("nota",nota);
                startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Precione un contacto para actualizar",Toast.LENGTH_LONG).show();
                }
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toque==1) {
                    Borrar();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Precione un contacto para borrar",Toast.LENGTH_LONG).show();
                }
            }
        });

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        Imgane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toque==1) {
                    try {
                        Intent intent = new Intent(getApplicationContext(), ActivityImagen.class);
                        intent.putExtra("id", codigo+"");
                        intent.putExtra("nombre",nombre);
                        startActivity(intent);
                    } catch (Exception e) {
                        Intent intent = new Intent(getApplicationContext(), ActivityImagen.class);
                        intent.putExtra("id", "1");
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Precione un contacto para ver la imagen",Toast.LENGTH_LONG).show();
                }


            }
        });

        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toque==1) {
                    compar();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Precione un contacto ser el uso de compartir",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void permisoLlamada() {

        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE}, PETICION_LLAMADA_TELEFONO);
        }else{
            LlamarContacto();
        }
    }

    private void LlamarContacto() {
        Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel: +"+numero));
        startActivity(intent);
    }

    private void compar() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "El numero de " + nombre + " : +" + numero);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void Borrar() {
        SQLiteDatabase db = con.getWritableDatabase();

        db.delete(comunicacion.tablacontactos,comunicacion.id+" = "+codigo,null);
        db.close();
        Toast.makeText(getApplicationContext(),"Contacto eliminado",Toast.LENGTH_LONG).show();
        Intent inten = new Intent(getApplicationContext(),Activitylista.class);
        startActivity(inten);
    }

    private void GetContacto() {
        SQLiteDatabase db =con.getReadableDatabase();
        contacto agen= null;

        contacp =new ArrayList<contacto>();

        Cursor cursor =db.rawQuery(comunicacion.SelectContactos,null);
        while (cursor.moveToNext()){
            agen= new contacto();
            agen.setCodigo(cursor.getInt(0));
            agen.setNombreContacto(cursor.getString(1));
            agen.setNumeroContacto(cursor.getString(2));
            agen.setNota(cursor.getString(3));

            contacp.add(agen);
        }
        cursor.close();
        FillList();
    }

    private void FillList() {
        Arreglocontacto = new ArrayList<String>();

        for (int i = 0 ; i<contacp.size();i++){
            Arreglocontacto.add(contacp.get(i).getNombreContacto()+" | "+contacp.get(i).getNumeroContacto());
        }
    }
}