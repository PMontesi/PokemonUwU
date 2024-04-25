package es.cesur.progprojectpok.clases;

import es.cesur.progprojectpok.database.DBConnection;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Entrenador {

    protected String nombre;
    private Pokemon[] equipoPokemon = new Pokemon[6];



    public Entrenador(String nombre) {
        this.nombre = nombre;
    }

    //MÉTODOS


    //GETTERS Y SETTERS


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
