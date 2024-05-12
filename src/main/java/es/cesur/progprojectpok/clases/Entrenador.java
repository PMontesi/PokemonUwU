package es.cesur.progprojectpok.clases;

import es.cesur.progprojectpok.database.DBConnection;

import java.sql.*;
import java.util.Arrays;

public class Entrenador {

    private String nombreUsuario;
    private int pokedolares;
    private int idUsuario;
    private String nombre;
    private Pokemon[] equipoPokemon;

    public Entrenador() {
    }

    public Entrenador(String nombreUsuario, int pokedolares, int idUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.pokedolares = pokedolares;
        this.idUsuario = idUsuario;
        this.equipoPokemon = new Pokemon[6];
    }

    public Entrenador(String nombre, int longitudEquipo) {
        this.nombre = nombre;
        if(longitudEquipo > 6) {
            longitudEquipo = 6;
            System.out.println("El equipo no puede ser mayor de 6");
        }
        this.equipoPokemon = new Pokemon[longitudEquipo];
    }


    //MÉTODOS
    public void capturarPokemon(Pokemon pokemon){
        int equipoOrCaja = 0;
        System.out.println("Método invocado con éxito");

        String sqlInsertPokemonCap = "INSERT INTO POKEMON " +
                "(NUM_POKEDEX, ID_ENTRENADOR, MOTE, CAJA, ATAQUE, AT_ESPECIAL, DEFENSA, DEF_ESPECIAL, VELOCIDAD, NIVEL, " +
                "FERTILIDAD, SEXO, ESTADO, EXPERIENCIA, VITALIDAD, VIT_MAX) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statementPokemonCapturado = connection.prepareStatement(sqlInsertPokemonCap)){
            statementPokemonCapturado.setInt(1, pokemon.getNumPokedex());
            statementPokemonCapturado.setInt(2, getIdUsuario());
            statementPokemonCapturado.setString(3, pokemon.getMote());
            statementPokemonCapturado.setInt(4, equipOrCaja(pokemon));
            statementPokemonCapturado.setInt(5, pokemon.getAtaque());
            statementPokemonCapturado.setInt(6, pokemon.getAtaqueEspecial());
            statementPokemonCapturado.setInt(7, pokemon.getDefensa());
            statementPokemonCapturado.setInt(8, pokemon.getDefensaEspecial());
            statementPokemonCapturado.setInt(9, pokemon.getVelocidad());
            statementPokemonCapturado.setInt(10, pokemon.getNivel());
            statementPokemonCapturado.setInt(11, pokemon.getFertilidad());
            statementPokemonCapturado.setString(12, String.valueOf(pokemon.getSexo()));
            statementPokemonCapturado.setString(13, "Saludable");
            statementPokemonCapturado.setInt(14, pokemon.getExperiencia());
            statementPokemonCapturado.setInt(15, pokemon.getVitalidad());
            statementPokemonCapturado.setInt(16, pokemon.getVitalidad());
            statementPokemonCapturado.executeUpdate();

            statementPokemonCapturado.close();
            connection.close();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /*
    Método para enviar el pokemon al equipo o a la caja.
    Si el equipo tiene espacio, el pokemon se inserta en el array y devuelve 0 para insertarlo en la BBDD.
    Si el equipo no tiene espacio, el pokemon no se inserta en el array y devuelve 1 para insertarlo en la BBDD.
     */
    public int equipOrCaja(Pokemon pokemon){
        for (int i = 0; i < equipoPokemon.length; i++) {
            if (getPokemon(i) == null){
                setPokemon(pokemon, i);
                return 0;
            }
        }
        return 1;
    }

    public void setPokemon(Pokemon pokemon, int indice){
        equipoPokemon[indice] = pokemon;
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

    public void setEquipoPokemon(int longitud) {

        this.equipoPokemon = new Pokemon[longitud];
    }

    public int getPokedolares() {
        return pokedolares;
    }

    public void setPokedolares(int pokedolares) {
        this.pokedolares = pokedolares;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }


    @Override
    public String toString() {
        return "Entrenador{" +
                "nombre='" + nombre + '\'' +
                ", equipoPokemon=" + Arrays.toString(equipoPokemon) +
                '}';
    }
}
