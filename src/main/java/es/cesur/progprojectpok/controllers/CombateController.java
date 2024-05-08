package es.cesur.progprojectpok.controllers;

import es.cesur.progprojectpok.clases.*;
import es.cesur.progprojectpok.database.DBConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CombateController implements Initializable {

    @FXML
    private ProgressBar barVitJugador;


    @FXML
    private ProgressBar barVitRival;


    @FXML
    private Button botonAtacar;

    @FXML
    private Button botonCambiarPokemon;

    @FXML
    private Button botonHuir;

    @FXML
    private Button botonMovi1;

    @FXML
    private Button botonMovi2;

    @FXML
    private Button botonMovi3;

    @FXML
    private Button botonMovi4;

    @FXML
    private Rectangle cuadradoEstadoJug;

    @FXML
    private Rectangle cuadradoEstadoRiv;

    @FXML
    private Pane paneDescCombate;

    @FXML
    private Pane paneMenuCombate;

    @FXML
    private Pane paneMovimientos;

    @FXML
    private Pane panePokeEnemigo;

    @FXML
    private Pane panePokeJugador;

    @FXML
    private ImageView pokemonJugador;
    @FXML
    private ImageView pokemonRival;

    @FXML
    private Text textDescripCombate;

    @FXML
    private Text textNivPokJug;

    @FXML
    private Text textNivPokRiv;

    @FXML
    private Text textNomPokeJug;

    @FXML
    private Text textNomPokeRiv;


    //Esta variable se tiene que inicializar según el valor de la sentencia sql???
    private Combate combate;
    private Entrenador usuario;
    private Entrenador rival;
    private int pokemonUsuarioActivo = 0;
    private int pokemonRivalActivo = 0;
    private int numeroCombate;
    private Turno turno;

    public void setUsuario(Entrenador usuario){
        this.usuario = usuario;
        empezarCombate();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /*
    Al comenzar el combate se crean los dos entrenadores y ambos equipos.

    Para el usuario, primero se obtiene el tamaño del equipo (de 1 a 6) y después se crea el entrenador.
    Después se obtienen los pokemons del Usuario y se meten en su array de equipo.

    Para el rival, el tamaño del equipo será igual al del usuario. Los pokemons se generarán aleatoriamente cada
    uno con un nivel +-3 de la media del nivel del equipo del usuario.
     */

    public void empezarCombate(){
        //ESTO ES SOLO PARA COMPROBAR QUE PILLA EL USUSARIO
        if (usuario != null) {
            System.out.println(usuario.getIdUsuario());
            // Realizar cualquier inicialización adicional necesaria con el usuario
        } else {
            System.out.println("El usuario es null");
        }

        //CARGAR EQUIPO DEL ENTRENADOR
        try {
            int mediaNivel = 0;

            //OBTENER EL TAMAÑAO DEL EQUIPO Y SU MEDIA DE NIVEL.
            //NECESARIO PARA SABER DE QUÉ TAMAÑO SERÁN LOS ARRAY Y EL NIVEL DEL RIVAL.
            String sqlLongitudequipo = "SELECT COUNT(CAJA), AVG(NIVEL) FROM POKEMON WHERE CAJA = 0 AND ID_ENTRENADOR = ?";
            Connection connection = DBConnection.getConnection();
            PreparedStatement statementLongitudequipo = connection.prepareStatement(sqlLongitudequipo);
            statementLongitudequipo.setInt(1, usuario.getIdUsuario());
            ResultSet resultLongitud = statementLongitudequipo.executeQuery();
            while(resultLongitud.next()){
                usuario.setEquipoPokemon(resultLongitud.getInt(1));
            }
            resultLongitud.close();
            statementLongitudequipo.close();


            //System.out.println(usuario.toString());

            //EXTRACCIÓN DEL EQUIPO DEL USUARIO
            //QUIZÁ HAYA QUE BORRARLO
            String sqlSelectPokemon = "SELECT * FROM POKEMON_EQUIPO WHERE ID_ENTRENADOR = ?;";
            PreparedStatement statementSelectPokemon = connection.prepareStatement(sqlSelectPokemon);
            statementSelectPokemon.setInt(1, usuario.getIdUsuario());
            ResultSet resultSetPokemon = statementSelectPokemon.executeQuery();{
                int indice = 0;
                while (resultSetPokemon.next()){

                    //HAY QUE RETOCAR ESTA PARTE UNA VEZ ESTÉN HECHOS LOS OBJETOS
                    String estadoPermanente = resultSetPokemon.getString("ESTADO");
                    int idObjeto = resultSetPokemon.getInt("ID_OBJETO");
                    Objeto objetoNulo = new Objeto();


                    Pokemon pokemonUsuario = new Pokemon(
                            resultSetPokemon.getString("MOTE"),
                            resultSetPokemon.getInt("VITALIDAD"),
                            resultSetPokemon.getInt("VIT_MAX"),
                            resultSetPokemon.getInt("ATAQUE"),
                            resultSetPokemon.getInt("DEFENSA"),
                            resultSetPokemon.getInt("AT_ESPECIAL"),
                            resultSetPokemon.getInt("DEF_ESPECIAL"),
                            resultSetPokemon.getInt("VELOCIDAD"),
                            resultSetPokemon.getInt("NIVEL"),
                            resultSetPokemon.getInt("EXPERIENCIA"),
                            resultSetPokemon.getInt("NUM_POKEDEX"),
                            resultSetPokemon.getInt("ID_POKEMON"),
                            resultSetPokemon.getString("IMAGEN_DETRAS"),
                            resultSetPokemon.getString("TIPO1"),
                            resultSetPokemon.getString("TIPO2"),
                            resultSetPokemon.getString("ESTADO"),
                            objetoNulo
                    );
                    pokemonUsuario.asignarMovimientos(pokemonUsuario.getId());
                    usuario.setPokemon(pokemonUsuario, indice);
                    indice++;
                    //System.out.println(usuario.toString());
                }
                resultSetPokemon.close();
                statementSelectPokemon.close();

                System.out.println(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(0).getNombre());
                System.out.println(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(1).getNombre());
                System.out.println(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(2).getNombre());
                System.out.println(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(3).getNombre());
            }

            //SE SITUA AL POKEMON DE LA POSICIÓN 0 EN EL CAMPO DE BATALLA.
            textNomPokeJug.setText(usuario.getPokemon(0).getMote());
            textNivPokJug.setText("Nv." + usuario.getPokemon(0).getNivel());
            barVitJugador.setProgress((double) usuario.getPokemon(pokemonUsuarioActivo).getVitalidad() /usuario.getPokemon(pokemonUsuarioActivo).getVitMax());
            barVitJugador.setStyle("-fx-accent: green;");


            botonMovi1.setText(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(0).getNombre());
            botonMovi2.setText(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(1).getNombre());
            botonMovi3.setText(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(2).getNombre());
            botonMovi4.setText(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(3).getNombre());
            System.out.println(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(0).getNombre());
            System.out.println(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(1).getNombre());
            System.out.println(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(2).getNombre());
            System.out.println(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(3).getNombre());

            //SUSTITUCIÓN DE IMAGEN
            File archivo = new File(usuario.getPokemon(0).getImagenUrl());
            String rutaAbsoluta = archivo.getAbsolutePath();
            if(System.getProperty("os.name").startsWith("Windows")){
                rutaAbsoluta = rutaAbsoluta.replace("/", "\\");
            }

            Image imagenPokemonGenerado = new Image(rutaAbsoluta);
            pokemonJugador.setImage(imagenPokemonGenerado);

            //GENERAR EL EQUIPO DEL RIVAL.
            System.out.println("-----------------------------------------------------------------------------------------");
            //EL RIVAL SIEMPRE TENDRÁ UN EQUIPO DEL MISMO TAMAÑO QUE EL RIVAL
            rival = new Entrenador("El Rival", usuario.getEquipoPokemon().length);
            System.out.println(rival.toString());

            //OBTENCIÓN DE UN POKEMON ALEATORIO DE LA POKEDEX PARA INTRODUCIRLO EN EL EQUIPO
            String sqlSelectPokemonRival = "SELECT * FROM POKEDEX ORDER BY RAND() LIMIT 1";


            //BUCLE PARA METER A LOS POKEMONS EN AL ARRAY DEL EQUIPO RIVAL
            for (int i = 0; i < rival.getEquipoPokemon().length; i++) {
                PreparedStatement statementSelectPokemonRival = connection.prepareStatement(sqlSelectPokemonRival);
                ResultSet resultSetPokemonRival =  statementSelectPokemonRival.executeQuery();

                while (resultSetPokemonRival.next()) {

                    //HAY QUE RETOCAR ESTA PARTE UNA VEZ ESTÉN HECHOS LOS OBJETOS
                    Objeto objetoNulo = new Objeto();

                    Pokemon pokemonRival = new Pokemon(
                            resultSetPokemonRival.getString("NOM_POKEMON"),
                            mediaNivel,
                            resultSetPokemonRival.getInt("NUM_POKEDEX"),
                            resultSetPokemonRival.getString("IMAGEN_DELANTE"),
                            resultSetPokemonRival.getString("TIPO1"),
                            resultSetPokemonRival.getString("TIPO2"),
                            objetoNulo
                    );

                    //BUCLE PARA ASIGNARLE LOS MOVIMIENTOS AL POKEMON GENERADO.
                    for (int j = 0; j < pokemonRival.getMovimientos().length; j++) {
                        pokemonRival.setMovimiento(j);
                    }

                    rival.setPokemon(pokemonRival, i);

                    System.out.println(rival.getPokemon(i));
                    System.out.println("Movimiento rival: " + pokemonRival.getMovimiento(0).getNombre());
                    System.out.println("Movimiento rival: " + pokemonRival.getMovimiento(1).getNombre());
                    System.out.println("Movimiento rival: " + pokemonRival.getMovimiento(2).getNombre());
                    System.out.println("Movimiento rival: " + pokemonRival.getMovimiento(3).getNombre());
                }
                resultSetPokemonRival.close();
                statementSelectPokemonRival.close();
            }


            connection.close();

            //SE SITUA AL POKEMON DE LA POSICIÓN 0 EN EL CAMPO DE BATALLA.
            textNomPokeRiv.setText(rival.getPokemon(0).getMote());
            textNivPokRiv.setText("Nv." + rival.getPokemon(0).getNivel());
            barVitRival.setProgress(((double) rival.getPokemon(pokemonRivalActivo).getVitalidad() /rival.getPokemon(pokemonRivalActivo).getVitMax()));
            barVitJugador.setStyle("-fx-accent: green;");

            //Sustitución imagen
            File archivo2 = new File(rival.getPokemon(0).getImagenUrl());
            String rutaAbsoluta2 = archivo2.getAbsolutePath();
            if(System.getProperty("os.name").startsWith("Windows")){
                rutaAbsoluta = rutaAbsoluta.replace("/", "\\");
            }

            Image imagenPokemonGenerado2 = new Image(rutaAbsoluta2);
            pokemonRival.setImage(imagenPokemonGenerado2);
            System.out.println("////////////////////////");
            System.out.println(rival.toString());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String sqlCombate = "guardar combate en la base de datos";
        //Aquí la ejecución de la sentencia sql

        combate = new Combate(usuario, rival);
        turno = new Turno(1, 0, "a", "a");
        paneDescCombate.setVisible(false);
    }


    public void desplegarMov(){
        paneMenuCombate.setVisible(false);
        paneMenuCombate.setDisable(true);
        paneMovimientos.setVisible(true);
        paneMovimientos.setDisable(false);
    }


    public void usarMovi1(){
        System.out.println("Movi1 pulsado");
        paneMovimientos.setVisible(false);
        paneMovimientos.setDisable(true);
        paneDescCombate.setVisible(true);
        int indice = 0;
        checkBeforeTurn(usuario.getPokemon(pokemonUsuarioActivo));
        checkBeforeTurn(rival.getPokemon(pokemonRivalActivo));
        combate.combatir(usuario.getPokemon(pokemonUsuarioActivo), indice, rival.getPokemon(pokemonRivalActivo), (int) (Math.random()*4));

        System.out.println(usuario.getPokemon(pokemonUsuarioActivo).getVitalidad());
        System.out.println(rival.getPokemon(pokemonRivalActivo).getVitalidad());

        progresoCombate();


        System.out.println("Fin de Movi3");
    }
    public void usarMovi2(){
        System.out.println("Movi2 pulsado");
        paneMovimientos.setVisible(false);
        paneMovimientos.setDisable(true);
        paneDescCombate.setVisible(true);
        int indice = 1;
        checkBeforeTurn(usuario.getPokemon(pokemonUsuarioActivo));
        checkBeforeTurn(rival.getPokemon(pokemonRivalActivo));
        combate.setPrioridad(usuario.getPokemon(pokemonUsuarioActivo), rival.getPokemon(pokemonRivalActivo));

        combate.combatir(usuario.getPokemon(pokemonUsuarioActivo), indice, rival.getPokemon(pokemonRivalActivo), ((int) (Math.random()*4)));

        //System.out.println("From rival: " + rival.getPokemon(pokemonRivalActivo).getMovimiento(1).getNombre());
        //System.out.println(combate.getPrimerPoke().getMote());
        //System.out.println(combate.getMovimientoUsadoPrimer().getNombre());
        //System.out.println(combate.getSegundoPoke().getMote());
        //System.out.println("From combate: " + combate.getMovimientoUsadoSegund().getNombre());



        progresoCombate();

        System.out.println("Fin de Movi2");
    }

    public void usarMovi3(){
        System.out.println("Movi3 pulsado");
        paneMovimientos.setVisible(false);
        paneMovimientos.setDisable(true);
        paneDescCombate.setVisible(true);
        int indice = 2;
        checkBeforeTurn(usuario.getPokemon(pokemonUsuarioActivo));
        checkBeforeTurn(rival.getPokemon(pokemonRivalActivo));
        combate.combatir(usuario.getPokemon(pokemonUsuarioActivo), indice, rival.getPokemon(pokemonRivalActivo), (int) (Math.random()*4));

        progresoCombate();

        System.out.println("Fin de Movi3");
    }
    public void usarMovi4(){
        System.out.println("Movi4 pulsado");
        paneMovimientos.setVisible(false);
        paneMovimientos.setDisable(true);
        paneDescCombate.setVisible(true);
        int indice = 3;
        checkBeforeTurn(usuario.getPokemon(pokemonUsuarioActivo));
        checkBeforeTurn(rival.getPokemon(pokemonRivalActivo));
        combate.combatir(usuario.getPokemon(pokemonUsuarioActivo), indice, rival.getPokemon(pokemonRivalActivo), (int) (Math.random()*4));

        progresoCombate();

        System.out.println("Fin de Movi4");
    }

    public void progresoCombate() {
        textDescripCombate.setText("El combate está teniendo lugar" + "Turno: " + turno.getNumeroTurno());
        System.out.println("______________________");
        System.out.println(combate.getPrimerPoke().toString());
        System.out.println(combate.getPrimerPoke().getMovimiento(combate.getMovimientoUsadoPrimer()).toString());
        System.out.println("indice: " + (combate.getMovimientoUsadoPrimer()));
        String primeraAccion = combate.getPrimerPoke().getMote() + " usó " + combate.getPrimerPoke().getMovimiento(combate.getMovimientoUsadoPrimer()).getNombre();
        String segundaAccion = combate.getSegundoPoke().getMote() + " usó " + combate.getSegundoPoke().getMovimiento(combate.getMovimientoUsadoSegund()).getNombre();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> {
                    checkAfterTurn(combate.getPrimerPoke());
                    textDescripCombate.setText(primeraAccion);
                }),
                new KeyFrame(Duration.seconds(4), event -> {
                    checkAfterTurn(combate.getSegundoPoke());
                    textDescripCombate.setText(segundaAccion);
                }),
                new KeyFrame(Duration.seconds(6), event -> {
                    turno.setNumeroTurno(turno.getNumeroTurno() + 1);
                    combate.getTurnos().add(
                            new Turno(
                                    turno.getNumeroCombate(),
                                    turno.getNumeroTurno(),
                                    primeraAccion,
                                    segundaAccion
                            )
                    );
                    System.out.println(combate.getTurnos().toString());
                    paneDescCombate.setVisible(false);
                    paneMenuCombate.setVisible(true);
                    paneMenuCombate.setDisable(false);
                    System.out.println(usuario.getPokemon(pokemonUsuarioActivo).getVitalidad());
                    System.out.println(rival.getPokemon(pokemonRivalActivo).getVitalidad());
                })
        );

        timeline.play();
    }

    //HAY QUE AÑADIR LA CONDICIÓN DE ATRAPADO PARA QUE NO DEJE CAMBIAR DE POKEMON
    public void cambiarPokemon(){
        System.out.println("Botón cambiar pulsado");
        pokemonUsuarioActivo = 1;
        textNomPokeJug.setText(usuario.getPokemon(pokemonUsuarioActivo).getMote());
        textNivPokJug.setText("Nv." + usuario.getPokemon(pokemonUsuarioActivo).getNivel());
        barVitJugador.setProgress((double) usuario.getPokemon(pokemonUsuarioActivo).getVitalidad() /usuario.getPokemon(pokemonUsuarioActivo).getVitMax());
        barVitJugador.setStyle("-fx-accent: green;");
        botonMovi1.setText(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(0).getNombre());
        botonMovi2.setText(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(1).getNombre());
        botonMovi3.setText(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(2).getNombre());
        botonMovi4.setText(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(3).getNombre());



        //SUSTITUCIÓN DE IMAGEN
        File archivo = new File(usuario.getPokemon(pokemonUsuarioActivo).getImagenUrl());
        String rutaAbsoluta = archivo.getAbsolutePath();
        if(System.getProperty("os.name").startsWith("Windows")){
            rutaAbsoluta = rutaAbsoluta.replace("/", "\\");
        }

        Image imagenPokemonGenerado = new Image(rutaAbsoluta);
        pokemonJugador.setImage(imagenPokemonGenerado);
        System.out.println("Botón cambiar pulsado");


    }

    //ESTE MÉTODO TIENE QUE IR ANTES DEL MÉTODO COMBATIR DE CADA BOTÓN.
    //NO TIENE QUE ACTUALIZAR LA INTERFAZ, ESO SE HARÁ O EN EL MÉTODO CHECKAFTER
    //O HABRÁ QUE HACER UN MÉTODO DIFERENTE PARA ACRTUALIZAR INTERFAZ
    public void checkBeforeTurn(Pokemon pokemon){
        MovimientoEstado.quitarEstado(pokemon);
        MovimientoEstado.quitarConfusion(pokemon);
    }

    public void checkAfterTurn(Pokemon pokemon){
        if (pokemon.getEstadosPersistentes() == EstPersitentesEnum.ENVENENADO
            || pokemon.getEstadosPersistentes() == EstPersitentesEnum.QUEMADO
            || pokemon.getEstadosPersistentes() == EstPersitentesEnum.GRAV_ENVENENADO
            || pokemon.getEstadosPersistentes() == EstPersitentesEnum.HELADO
            || pokemon.getEstTemporalesEnums().contains(EstTemporalesEnum.MALDITO))
        {
            MovimientoEstado.damageOverTime(pokemon);
        }

        if (pokemon.getEstTemporalesEnums().contains(EstTemporalesEnum.CANTO_MORTAL)) pokemon.setDuracionCantoMortal(pokemon.getDuracionCantoMortal()-1);
        if (pokemon.getDuracionCantoMortal() == 0) pokemon.setVitalidad(0);
        pokemon.getEstTemporalesEnums().remove(EstTemporalesEnum.AMEDRENTADO);

        if (pokemon.getPrioridad() == usuario.getPokemon(pokemonUsuarioActivo).getPrioridad()){
            barVitJugador.setProgress(((double) usuario.getPokemon(pokemonUsuarioActivo).getVitalidad() /usuario.getPokemon(pokemonUsuarioActivo).getVitMax()));
        }
        else if (pokemon.getPrioridad() == rival.getPokemon(pokemonRivalActivo).getPrioridad()){
            barVitRival.setProgress(((double) rival.getPokemon(pokemonRivalActivo).getVitalidad() /rival.getPokemon(pokemonRivalActivo).getVitMax()));
        }



    }
}
