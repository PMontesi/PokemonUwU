package es.cesur.progprojectpok.clases;

import es.cesur.progprojectpok.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Objeto {
    private String nombre;
    private int precio;
    private int aumento;
    private int reduccion;



    public void aumentarEstadistica(String estadistica) throws SQLException {
        String sqlEstadistica = "SELECT " + estadistica + " FROM OBJETO";
        Connection connection = DBConnection.getConnection();
        PreparedStatement statementEstadistica = connection.prepareStatement(sqlEstadistica);
        ResultSet resultSetEstadistica = statementEstadistica.executeQuery();
        while (resultSetEstadistica.next()){
            aumento = resultSetEstadistica.getInt(estadistica);
        }
    }
}
