package es.cesur.progprojectpok.controllers;

import es.cesur.progprojectpok.clases.*;
import es.cesur.progprojectpok.database.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CombateController implements Initializable {


    @FXML
    private ImageView pokemonEnemigo;
    @FXML
    private ImageView pokemonJugador;
    @FXML
    private Button botonMovi1;
    @FXML
    private Button botonMovi2;
    @FXML
    private Button botonMovi3;
    @FXML
    private Button botonMovi4;
    @FXML
    private Button iniciarCombate;
    @FXML
    private Pane paneMovimientos;
    @FXML
    private Pane paneDescCombate;
    @FXML
    private Pane panePokeJugador;
    @FXML
    private Pane panePokeEnemigo;


    @FXML
    private Text textDescripCombate;
    @FXML
    private Text textPokeJugador;
    @FXML
    private Text textPokeRival;


    //Esta variable se tiene que inicializar según el valor de la sentencia sql
    private Combate combate;
    private Entrenador usuario;
    private Entrenador rival;
    private int numeroCombate;

    public void setUsuario(Entrenador usuario){
        this.usuario = usuario;
    }
    /*
    Al comenzar el combate se crean los dos entrenadores y ambos equipos.

    Para el usuario, primero se obtiene el tamaño del equipo (de 1 a 6) y después se crea el entrenador.
    Después se obtienen los pokemons del Usuario y se meten en su array de equipo.

    Para el rival, el tamaño del equipo será igual al del usuario. Los pokemons se generarán aleatoriamente cada
    uno con un nivel +-3 de la media del nivel del equipo del usuario.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void empezarCombate(){
        if (usuario != null) {
            System.out.println(usuario.getIdUsuario());
            // Realizar cualquier inicialización adicional necesaria con el usuario
        } else {
            System.out.println("El usuario es null");
        }

        try {
            int mediaNivel = 0;

            String sqlLongitudequipo = "SELECT COUNT(CAJA), AVG(NIVEL) FROM POKEMON WHERE CAJA = 0 AND ID_ENTRENADOR = ?";
            Connection connection = DBConnection.getConnection();
            PreparedStatement statementLongitudequipo = connection.prepareStatement(sqlLongitudequipo);
            statementLongitudequipo.setInt(1, usuario.getIdUsuario());
            ResultSet resultLongitud = statementLongitudequipo.executeQuery();
            while(resultLongitud.next()){
                usuario.setEquipoPokemon(resultLongitud.getInt(1));
            }

            System.out.println(usuario.toString());


            String sqlSelectPokemon = "SELECT P.*, PD.TIPO1, PD.TIPO2\n" +
                    "FROM POKEMON P\n" +
                    "INNER JOIN POKEDEX PD ON P.NUM_POKEDEX = PD.NUM_POKEDEX\n" +
                    "WHERE P.CAJA = 0 AND P.ID_ENTRENADOR = ?;";
            PreparedStatement statementSelectPokemon = connection.prepareStatement(sqlSelectPokemon);
            statementSelectPokemon.setInt(1, usuario.getIdUsuario());
            ResultSet resultSetPokemon = statementSelectPokemon.executeQuery();{
                int indice = 0;
                while (resultSetPokemon.next()){

                    //HAY QUE RETOCAR ESTA PARTE UNA VEZ ESTÉN HECHOS LOS ESTADOS Y LOS OBJETOS
                    String estadoPermanente = resultSetPokemon.getString("ESTADO");
                    int idObjeto = resultSetPokemon.getInt("ID_OBJETO");
                    EstadoPersistente estadoPersistenteNulo = new EstadoPersistente();
                    EstadoTemporal estadotemporalNulo = new EstadoTemporal();
                    Objeto objetoNulo = new Objeto();


                    Pokemon pokemonUsuario = new Pokemon(
                            resultSetPokemon.getString("MOTE"),
                            resultSetPokemon.getInt("VITALIDAD"),
                            resultSetPokemon.getInt("ATAQUE"),
                            resultSetPokemon.getInt("DEFENSA"),
                            resultSetPokemon.getInt("AT_ESPECIAL"),
                            resultSetPokemon.getInt("DEF_ESPECIAL"),
                            resultSetPokemon.getInt("VELOCIDAD"),
                            resultSetPokemon.getInt("NIVEL"),
                            resultSetPokemon.getInt("EXPERIENCIA"),
                            resultSetPokemon.getInt("NUM_POKEDEX"),
                            resultSetPokemon.getInt("ID_POKEMON"),
                            resultSetPokemon.getString("TIPO1"),
                            resultSetPokemon.getString("TIPO2"),
                            estadoPersistenteNulo, estadotemporalNulo, objetoNulo
                    );
                    pokemonUsuario.asignarMovimientos(pokemonUsuario.getId());
                    usuario.setPokemon(pokemonUsuario, indice);
                    indice++;
                }
            }

            System.out.println("-----------------------------------------------------------------------------------------");
            rival = new Entrenador("El Rival", usuario.getEquipoPokemon().length);
            System.out.println(rival.toString());
            int numPokedexTotal = 0;
            String sqlSelectTotalPokedex = "SELECT MAX(NUM_POKEDEX) FROM POKEDEX";
            PreparedStatement statement = connection.prepareStatement(sqlSelectTotalPokedex);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numPokedexTotal = resultSet.getInt(1);
            }

            for (int i = 0; i < rival.getEquipoPokemon().length; i++) {

                int pokemonAleatorio = (int) (Math.random() * numPokedexTotal + 1);
                String sqlSelectPokemonRival = "SELECT * FROM POKEDEX WHERE NUM_POKEDEX = ?";
                PreparedStatement statementSelectPokemonRival = connection.prepareStatement(sqlSelectPokemonRival);
                statementSelectPokemonRival.setInt(1, pokemonAleatorio);

                ResultSet resultSetPokemonRival = statementSelectPokemonRival.executeQuery();
                while (resultSetPokemonRival.next()) {

                    //HAY QUE RETOCAR ESTA PARTE UNA VEZ ESTÉN HECHOS LOS ESTADOS Y LOS OBJETOS
                    EstadoPersistente estadoPersistenteNulo = new EstadoPersistente();
                    EstadoTemporal estadotemporalNulo = new EstadoTemporal();
                    Objeto objetoNulo = new Objeto();

                    Pokemon pokemonRival = new Pokemon(
                            resultSetPokemonRival.getString("NOM_POKEMON"),
                            mediaNivel,
                            pokemonAleatorio,
                            resultSetPokemonRival.getString("TIPO1"),
                            resultSetPokemonRival.getString("TIPO2"),
                            estadoPersistenteNulo, estadotemporalNulo, objetoNulo
                    );

                    for (int j = 0; j < pokemonRival.getMovimientos().length; j++) {
                        pokemonRival.setMovimiento(j, pokemonRival.selecionarMovimientoDB());
                    }

                    rival.setPokemon(pokemonRival, i);
                    System.out.println(rival.getPokemon(1));
                    System.out.println("Movimiento rival: " + pokemonRival.getMovimiento(0).getNombre());
                    System.out.println("Movimiento rival: " + pokemonRival.getMovimiento(1).getNombre());
                    System.out.println("Movimiento rival: " + pokemonRival.getMovimiento(2).getNombre());
                    System.out.println("Movimiento rival: " + pokemonRival.getMovimiento(3).getNombre());
                }

            }
            System.out.println("////////////////////////");
            System.out.println(rival.toString());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String sqlCombate = "guardar combate en la base de datos";
        //Aquí la ejecución de la sentencia sql

        combate = new Combate(usuario, rival);
        paneDescCombate.setVisible(false);
    }

    public void usarMovi1(){
        int indice = 1;
        combate.combatir(usuario.getPokemon(1), indice, rival.getPokemon(1), 1);
        textDescripCombate.setText("El combate está teniendo lugar");
    }
}
