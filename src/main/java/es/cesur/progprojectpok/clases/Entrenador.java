package es.cesur.progprojectpok.clases;

import es.cesur.progprojectpok.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Entrenador {

    private static String nombre;
    private static int pokedolares;
    private static int id;



    //MÉTODOS
    public void moverACaja(Pokemon pokemon){

    }
    public void moverAEquipo(Pokemon pokemon){

    }
    public void entrenar(Pokemon pokemon){

    }
    public void ciar(Pokemon pokemonA, Pokemon pokemonB){

    }
    public void sumarPokedolares(int pokedolares){

    }
    public void restarPokedolares(int pokedolares){

    }
    public static void capturarPokemon(Pokemon pokemon){
        System.out.println("Método invocado con éxito");
        String sqlInsertPokemonCap = "INSERT INTO POKEMON (NUM_POKEDEX, ID_ENTRENADOR, MOTE, CAJA, ATAQUE, AT_ESPECIAL, DEFENSA, DEF_ESPECIAL, VELOCIDAD, NIVEL, FERTILIDAD, SEXO, ESTADO, EXPERIENCIA, VITALIDAD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statementPokemonCapturado = connection.prepareStatement(sqlInsertPokemonCap)){
            statementPokemonCapturado.setInt(1, pokemon.getNumPokedex());
            statementPokemonCapturado.setInt(2, Entrenador.getId());
            //Añadir algo para cuando no se le quiera poner un mote al pokemon
            statementPokemonCapturado.setString(3, pokemon.getMote());
            //Añadir algo para cuando el equipo pokemon está lleno
            statementPokemonCapturado.setInt(4, 0);
            statementPokemonCapturado.setInt(5, pokemon.getAtaque());
            statementPokemonCapturado.setInt(6, pokemon.getAtaqueEspecial());
            statementPokemonCapturado.setInt(7, pokemon.getDefensa());
            statementPokemonCapturado.setInt(8, pokemon.getDefensaEspecial());
            statementPokemonCapturado.setInt(9, pokemon.getVelocidad());
            statementPokemonCapturado.setInt(10, pokemon.getNivel());
            statementPokemonCapturado.setInt(11, pokemon.getFertilidad());
            statementPokemonCapturado.setString(12, String.valueOf(pokemon.getSexo()));
            statementPokemonCapturado.setString(13, "sinEstado");
            statementPokemonCapturado.setInt(14, pokemon.getExperiencia());
            statementPokemonCapturado.setInt(15, pokemon.getVitalidad());
            statementPokemonCapturado.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    //GETTERS Y SETTERS

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        Entrenador.nombre = nombre;
    }

    public static int getPokedolares() {
        return pokedolares;
    }

    public static void setPokedolares(int pokedolares) {
        Entrenador.pokedolares = pokedolares;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Entrenador.id = id;
    }
}
