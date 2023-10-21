package com.example.pm1e14321.sqliteconexion;

public class comunicacion {

    public static final String NameDatabase = "PM01DB";

    public static final String tablacontactos = "contactos";

    public static final String id = "id";
    public static final String nombreCompleto = "nombreCompleto";
    public static final String telefono = "telefono";
    public static final String nota = "nota";
    public static final String foto = "foto";

    public static final String pais = "pais";


    public static final String createTableContact = "CREATE TABLE " + tablacontactos +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombreCompleto TEXT, telefono INTEGER,pais TEXT, nota TEXT, foto BLOB)";

    public  static  final String SelectContactos ="Select id,nombreCompleto,telefono,nota from "+tablacontactos;

    public static final String dropTableContact = "DROP TABLE IF EXIST" + tablacontactos;
}

