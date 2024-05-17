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
        }
        this.equipoPokemon = new Pokemon[longitudEquipo];
    }


    /**
     * Inserta un pokemon capturado en la base de datos, le da su id de la base de datos y sus imágenes relacionadas.
     *
     * @param pokemon el pokemon capturado.
     * @param imagenTrasera la url que está en la base de datos de la imagen trasera.
     * @param imagenDelantera la url que está en la base de datos de la imagen delantera.
     */
    public void capturarPokemon(Pokemon pokemon, String imagenTrasera, String imagenDelantera){

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

        setPokemonIdBBDD(pokemon);
        pokemon.setMovimiento(0);
        pokemon.setImagenUrlDetras(imagenTrasera);
        pokemon.setImagenUrlDelante(imagenDelantera);

    }

    /**
     * Obtiene la ID del último pokemon añadido a la base de datos.
     * Se usa exclusivamente cuando un pokemon es capturado.
     * @param pokemon el pokemon capturado.
     */
    public void setPokemonIdBBDD(Pokemon pokemon){
        String obtenerIDpoke = "SELECT MAX(ID_POKEMON) FROM POKEMON";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statementObtenerIDPoke = connection.prepareStatement(obtenerIDpoke);){
            ResultSet resultSetObtenerIDPoke = statementObtenerIDPoke.executeQuery();
            while (resultSetObtenerIDPoke.next()){
                pokemon.setId(resultSetObtenerIDPoke.getInt("MAX(ID_POKEMON)"));
            }
            resultSetObtenerIDPoke.close();
            statementObtenerIDPoke.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    /**
     * Para enviar el pokemon al equipo o a la caja.
     * Si el equipo tiene espacio, el pokemon se inserta en el array y devuelve 0 para insertarlo en la BBDD.
     * Si el equipo no tiene espacio, el pokemon no se inserta en el array y devuelve 1 para insertarlo en la BBDD.
     * @param pokemon pokemon capturado
     * @return devuelve el valor a actualizar en la columna caja de la base de datos
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
