package es.cesur.progprojectpok.clases;

import es.cesur.progprojectpok.database.DBConnection;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class Entrenador {

    private static String nombreUsuario;
    private static int pokedolares;
    private static int idUsuario;
    private String nombre;
    private Pokemon[] equipoPokemon;



    public Entrenador(String nombre, int longitudEquipo) {
        this.nombre = nombre;
        if(longitudEquipo > 6) {
            longitudEquipo = 6;
            System.out.println("El equipo no puede ser mayor de 6");
        }
        this.equipoPokemon = new Pokemon[longitudEquipo];
    }


    //MÉTODOS
    public static void capturarPokemon(Pokemon pokemon){
        System.out.println("Método invocado con éxito");
        String sqlInsertPokemonCap = "INSERT INTO POKEMON (NUM_POKEDEX, ID_ENTRENADOR, MOTE, CAJA, ATAQUE, AT_ESPECIAL, DEFENSA, DEF_ESPECIAL, VELOCIDAD, NIVEL, FERTILIDAD, SEXO, ESTADO, EXPERIENCIA, VITALIDAD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statementPokemonCapturado = connection.prepareStatement(sqlInsertPokemonCap)){
            statementPokemonCapturado.setInt(1, pokemon.getNumPokedex());
            statementPokemonCapturado.setInt(2, Entrenador.getIdUsuario());
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

    public void setPokemon(Pokemon pokemon, int indice){
        equipoPokemon[indice] = pokemon;
        System.out.println("Insertado pokemon " + indice + ": " + pokemon.toString());
    }
    public Pokemon getPokemon(int indice){
        return equipoPokemon[indice];
    }

    public Pokemon usarPokemon(int indice){
        if (indice >= 0 && indice < equipoPokemon.length) {
            return equipoPokemon[indice];
        } else {
            System.out.println("Índice de Pokémon inválido.");
            return null;
        }
    }



    //GETTERS Y SETTERS


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pokemon[] getEquipoPokemon() {
        return equipoPokemon;
    }

    public void setEquipoPokemon(Pokemon[] equipoPokemon) {
        this.equipoPokemon = equipoPokemon;
    }

    public static int getPokedolares() {
        return pokedolares;
    }

    public static void setPokedolares(int pokedolares) {
        Entrenador.pokedolares = pokedolares;
    }

    public static int getIdUsuario() {
        return idUsuario;
    }

    public static void setIdUsuario(int idUsuario) {
        Entrenador.idUsuario = idUsuario;
    }

    public static String getNombreUsuario() {
        return nombreUsuario;
    }

    public static void setNombreUsuario(String nombreUsuario) {
        Entrenador.nombreUsuario = nombreUsuario;
    }


    @Override
    public String toString() {
        return "Entrenador{" +
                "nombre='" + nombre + '\'' +
                ", equipoPokemon=" + Arrays.toString(equipoPokemon) +
                '}';
    }
}
