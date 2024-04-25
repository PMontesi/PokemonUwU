package es.cesur.progprojectpok.clases;

import es.cesur.progprojectpok.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Usuario extends Entrenador{

    private static String nombreUsuario;
    private static int pokedolares;
    private static int idUsuario;
    //Quizá haga falta hacer un static de un arraylist del equipo pokemon.

    public Usuario(String nombre) {
        super(nombre);
        nombre = nombreUsuario;
    }

    public void moverACaja(Pokemon pokemon){

    }
    public void moverAEquipo(Pokemon pokemon){

    }
    public void entrenar(Pokemon pokemon){

    }
    public void criar(Pokemon pokemonA, Pokemon pokemonB){

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
            statementPokemonCapturado.setInt(2, Usuario.getIdUsuario());
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




    public static int getPokedolares() {
        return pokedolares;
    }

    public static void setPokedolares(int pokedolares) {
        Usuario.pokedolares = pokedolares;
    }

    public static int getIdUsuario() {
        return idUsuario;
    }

    public static void setIdUsuario(int idUsuario) {
        Usuario.idUsuario = idUsuario;
    }

    public static String getNombreUsuario() {
        return nombreUsuario;
    }

    public static void setNombreUsuario(String nombreUsuario) {
        Usuario.nombreUsuario = nombreUsuario;
    }
}
