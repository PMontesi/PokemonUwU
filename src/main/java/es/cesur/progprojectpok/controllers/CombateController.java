package es.cesur.progprojectpok.controllers;

import es.cesur.progprojectpok.SplashApplication;
import es.cesur.progprojectpok.clases.*;
import es.cesur.progprojectpok.database.DBConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

public class CombateController implements Initializable {

    @FXML
    private AnchorPane anchorEquipo;

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
    private Button botonNo;

    @FXML
    private Button botonSI;

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
    private Turno turno;
    private Map<String, PaneData> paneMap;
    private boolean decisionUsuario;
    private boolean aprendiendoMovimiento = false;
    boolean noPuedeAprender = false;
    private Movimiento movimientoAprender;
    private Random r = new Random();
    private final String BAR_VERDE = "-fx-accent: green;";

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
        combate = new Combate(usuario, rival);
        turno = new Turno(1, 0, "a", "a");

        int sumatorioNivel = 0;
        int longitudEquipoUser = 0;

        for (Pokemon pokemon : usuario.getEquipoPokemon()){
            if (pokemon != null){
                    sumatorioNivel += pokemon.getNivel();
                    longitudEquipoUser++;
                }
            }

        int mediaNivel = sumatorioNivel / longitudEquipoUser;

        combate.setKoJugador(6-longitudEquipoUser);
        combate.setKoRival(6-longitudEquipoUser);

            setPokeUsuario(pokemonUsuarioActivo);


            //GENERAR EL EQUIPO DEL RIVAL.

            //EL RIVAL SIEMPRE TENDRÁ UN EQUIPO DEL MISMO TAMAÑO QUE EL RIVAL
            rival = new Entrenador("El Rival", longitudEquipoUser);
            //OBTENCIÓN DE UN POKEMON ALEATORIO DE LA POKEDEX PARA INTRODUCIRLO EN EL EQUIPO
            String sqlSelectPokemonRival = "SELECT * FROM POKEDEX ORDER BY RAND() LIMIT 1";
            //BUCLE PARA METER A LOS POKEMONS EN AL ARRAY DEL EQUIPO RIVAL
        try{
        Connection connection = DBConnection.getConnection();
        for (int i = 0; i < longitudEquipoUser; i++) {
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

                }
                resultSetPokemonRival.close();
                statementSelectPokemonRival.close();
            }

            connection.close();

            setImagenPokeRival(pokemonRivalActivo);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        paneDescCombate.setVisible(false);
    }

    public void setImagenPokeRival(int pokemonRivalActivo){
        textNomPokeRiv.setText(rival.getPokemon(pokemonRivalActivo).getMote());
        textNivPokRiv.setText("Nv." + rival.getPokemon(pokemonRivalActivo).getNivel());
        barVitRival.setProgress(((double) rival.getPokemon(pokemonRivalActivo).getVitalidad() /rival.getPokemon(pokemonRivalActivo).getVitMax()));
        barVitJugador.setStyle(BAR_VERDE);

        String imgPokemonRival = Pokemon.imgRutaAbsouta(rival.getPokemon(pokemonRivalActivo).getImagenUrlDetras());

        Image imagenPokemonGenerado2 = new Image(imgPokemonRival);
        pokemonRival.setImage(imagenPokemonGenerado2);
    }


    public void desplegarMov(){
        paneMenuCombate.setVisible(false);
        paneMenuCombate.setDisable(true);
        paneMovimientos.setVisible(true);
        paneMovimientos.setDisable(false);
    }


    public void usarMovi1(){
        int indice = 0;
        botonMoviPulsado(indice);
    }

    public void usarMovi2(){
        int indice = 1;
        botonMoviPulsado(indice);
    }

    public void usarMovi3(){
        int indice = 2;
        botonMoviPulsado(indice);
    }

    public void usarMovi4(){
        int indice = 3;
        botonMoviPulsado(indice);
    }

    public void botonMoviPulsado(int indice){
        if(!aprendiendoMovimiento){

            paneDescCombate.setVisible(true);

            checkBeforeTurn(usuario.getPokemon(pokemonUsuarioActivo));
            checkBeforeTurn(rival.getPokemon(pokemonRivalActivo));
            combate.combatir(usuario.getPokemon(pokemonUsuarioActivo), indice, rival.getPokemon(pokemonRivalActivo), r.nextInt(4));

            progresoCombate();

        }
        else if (aprendiendoMovimiento) {

            usuario.getPokemon(pokemonUsuarioActivo).olvidarMovimiento(indice);
            usuario.getPokemon(pokemonUsuarioActivo).aprenderMovimiento(movimientoAprender);

            actualizarBotonesMov();

            paneMovimientos.setVisible(false);
            paneMovimientos.setDisable(true);
            paneDescCombate.setVisible(true);
            aprendiendoMovimiento = false;

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(3), event -> textDescripCombate.setText(usuario.getPokemon(pokemonUsuarioActivo) + " ha aprendido " + movimientoAprender.getNombre())),
                    new KeyFrame(Duration.seconds(4), event ->{
                        paneDescCombate.setVisible(false);
                        paneMenuCombate.setVisible(true);
                        paneMenuCombate.setDisable(false);
                    })
            );
            timeline.play();
        }

        paneMovimientos.setVisible(false);
        paneMovimientos.setDisable(true);


    }

    public void progresoCombate() {
        textDescripCombate.setText("El combate está teniendo lugar" + "Turno: " + turno.getNumeroTurno());

        String primeraAccion = combate.getPrimerPoke().getMote() + " usó " + combate.getPrimerPoke().getMovimiento(combate.getMovimientoUsadoPrimer()).getNombre();
        String segundaAccion = combate.getSegundoPoke().getMote() + " usó " + combate.getSegundoPoke().getMovimiento(combate.getMovimientoUsadoSegund()).getNombre();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    combate.getPrimerPoke().usarMovimiento(combate.getMovimientoUsadoPrimer(), combate.getSegundoPoke(), combate.getPrimerPoke());
                    checkAfterTurn(combate.getPrimerPoke());
                    textDescripCombate.setText(primeraAccion);
                }),
                new KeyFrame(Duration.seconds(5), event -> {
                    if (combate.getSegundoPoke().getVitalidad() <= 0){
                        textDescripCombate.setText(combate.getSegundoPoke().getMote() + " se ha debilitado");
                        return;
                    }

                    combate.getSegundoPoke().usarMovimiento(combate.getMovimientoUsadoSegund(), combate.getPrimerPoke(), combate.getSegundoPoke());
                    checkAfterTurn(combate.getSegundoPoke());
                    textDescripCombate.setText(segundaAccion);
                }),
                new KeyFrame(Duration.seconds(7), event -> {
                    turno.setNumeroTurno(turno.getNumeroTurno() + 1);
                    combate.getTurnos().add(
                            new Turno(
                                    turno.getNumeroCombate(),
                                    turno.getNumeroTurno(),
                                    primeraAccion,
                                    segundaAccion
                            )
                    );

                    checkKo();

                })
        );

        timeline.play();
    }

    private void checkKo(){

        if (usuario.getPokemon(pokemonUsuarioActivo).getVitalidad() <= 0){
            usuario.getPokemon(pokemonUsuarioActivo).setEstadosPersistentes(EstPersitentesEnum.DEBILITADO);
            combate.setKoJugador(combate.getKoJugador()+1);
            if (combate.getKoJugador() == 6){
                mostrarEquipo();
            }
        }

        if (rival.getPokemon(pokemonRivalActivo).getVitalidad() <= 0){
            combate.recibirExperiencia(usuario.getPokemon(pokemonUsuarioActivo), rival.getPokemon(pokemonRivalActivo));
            pokemonRivalActivo++;
            combate.setKoRival(combate.getKoRival()+1);
            if (combate.getKoRival() !=6 ){
                setImagenPokeRival(pokemonRivalActivo);
            }
        }

        darExperiencia();

        if (combate.determinarGanador() != null){
            if (combate.determinarGanador() == usuario){
                combate.entregarPokedolares(1, rival);
            } else if (combate.determinarGanador() == rival) {
                combate.entregarPokedolares(-1, rival);
            }
            combate.restaurarEquipo();
            volverMenu();
        }

        if(!aprendiendoMovimiento){
            paneDescCombate.setVisible(false);
            paneMenuCombate.setVisible(true);
            paneMenuCombate.setDisable(false);
        }
    }

    private void darExperiencia(){
        //ES POSIBLE QUE SI, POR LO QUE SEA, EL POKEMON SUBE 2 NIVELES DE GOLPE, NO FUNCIONE.
        for (Pokemon pokemon : usuario.getEquipoPokemon()){
            if(pokemon != null){
                while (pokemon.getExperiencia() >= pokemon.getNivel()*10){
                    textDescripCombate.setText(pokemon.getMote() + " ha subido al nivel " + (pokemon.getNivel()+1));
                    textNivPokJug.setText("Nv." + usuario.getPokemon(pokemonUsuarioActivo).getNivel());
                    barVitJugador.setProgress((double) usuario.getPokemon(pokemonUsuarioActivo).getVitalidad() /usuario.getPokemon(pokemonUsuarioActivo).getVitMax());


                    if (pokemon.subirNivel()){
                        movimientoAprender = pokemon.selecionarMovimientoDB();
                        aprendiendoMovimiento = true;
                        textDescripCombate.setText(pokemon.getMote() + " puede aprender " + movimientoAprender.getNombre() + " ¿deseas que lo aprenda?");
                        botonSI.setDisable(false);
                        botonSI.setVisible(true);
                        botonNo.setDisable(false);
                        botonNo.setVisible(true);
                    }
                }
            }
        }
    }


    private void aprenderMovimiento(Pokemon pokemon, Movimiento movimiento){
        noPuedeAprender = true;

        if (decisionUsuario){
            for (int i = 0; i < pokemon.getMovimientos().length; i++) {
                if (pokemon.getMovimiento(i) == null) {
                    pokemon.getMovimientos()[i] = movimiento;
                    noPuedeAprender = false;
                    pokemon.aprenderMovimiento(movimiento);
                    break;
                }
            }

            if (noPuedeAprender){
                textDescripCombate.setText(pokemon.getMote() + " Ya sabe 4 movimientos. ¿Quieres que olvide uno para que aprenda " + movimiento.getNombre() + "?");
                //OTROS DOS BOTONES DE DARLE A SI O NO
                decisionUsuario = false;
                botonSI.setDisable(false);
                botonSI.setVisible(true);
                botonNo.setDisable(false);
                botonNo.setVisible(true);

            }
        }
    }

    public void presionSi(){
        decisionUsuario = true;
        botonSI.setDisable(true);
        botonSI.setVisible(false);
        botonNo.setDisable(true);
        botonNo.setVisible(false);

        if (!noPuedeAprender){
            aprenderMovimiento(usuario.getPokemon(pokemonUsuarioActivo), movimientoAprender);
        } else if (noPuedeAprender) {
            aprendiendoMovimiento = true;
            paneDescCombate.setVisible(false);
            paneMovimientos.setVisible(true);
            paneMovimientos.setDisable(false);
        }
    }

    public void presionNo(){
        decisionUsuario = false;
        botonSI.setDisable(true);
        botonSI.setVisible(false);
        botonNo.setDisable(true);
        botonNo.setVisible(false);
        usuario.getPokemon(pokemonUsuarioActivo).aprenderMovimiento(movimientoAprender);
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

            barVitRival.setProgress(((double) rival.getPokemon(pokemonRivalActivo).getVitalidad() / rival.getPokemon(pokemonRivalActivo).getVitMax()));
            if (rival.getPokemon(pokemonRivalActivo).getVitalidad() <= 0) {
                rival.getPokemon(pokemonRivalActivo).setVitalidad(0);
                barVitRival.setProgress(0);
            }

        }
        else if (pokemon.getPrioridad() == rival.getPokemon(pokemonRivalActivo).getPrioridad()){

            barVitJugador.setProgress(((double) usuario.getPokemon(pokemonUsuarioActivo).getVitalidad() / usuario.getPokemon(pokemonUsuarioActivo).getVitMax()));
            if (usuario.getPokemon(pokemonUsuarioActivo).getVitalidad() <= 0){
                barVitJugador.setProgress(0);
                usuario.getPokemon(pokemonUsuarioActivo).setVitalidad(0);
            }

        }
    }

    public void setPokeUsuario(int pokemonUsuarioActivo){

        textNomPokeJug.setText(usuario.getPokemon(pokemonUsuarioActivo).getMote());
        textNivPokJug.setText("Nv." + usuario.getPokemon(pokemonUsuarioActivo).getNivel());
        barVitJugador.setProgress((double) usuario.getPokemon(pokemonUsuarioActivo).getVitalidad() /usuario.getPokemon(pokemonUsuarioActivo).getVitMax());
        barVitJugador.setStyle(BAR_VERDE);

        actualizarBotonesMov();

        String imgPokUsuario = Pokemon.imgRutaAbsouta(usuario.getPokemon(pokemonUsuarioActivo).getImagenUrlDetras());


        Image imagenPokemonGenerado = new Image(imgPokUsuario);
        pokemonJugador.setImage(imagenPokemonGenerado);

    }

    public void actualizarBotonesMov(){
        if (usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(0) != null){
            botonMovi1.setText(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(0).getNombre());
            botonMovi1.setVisible(true);
            botonMovi1.setDisable(false);
        }
        else {
            botonMovi1.setVisible(false);
            botonMovi1.setDisable(true);
        }
        if (usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(1) != null){
            botonMovi2.setText(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(1).getNombre());
            botonMovi2.setVisible(true);
            botonMovi2.setDisable(false);
        }
        else {
            botonMovi2.setVisible(false);
            botonMovi2.setDisable(true);
        }
        if (usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(2) != null){
            botonMovi3.setText(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(2).getNombre());
            botonMovi3.setVisible(true);
            botonMovi3.setDisable(false);
        }
        else {
            botonMovi3.setVisible(false);
            botonMovi3.setDisable(true);
        }
        if (usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(3) != null){
            botonMovi4.setText(usuario.getPokemon(pokemonUsuarioActivo).getMovimiento(3).getNombre());
            botonMovi4.setVisible(true);
            botonMovi4.setDisable(false);
        }
        else {
            botonMovi4.setVisible(false);
            botonMovi4.setDisable(true);
        }
    }

    public void mostrarEquipo() {

        double x = 35;
        double y = 18;
        paneMap = new HashMap<>();

        for (int i = 0; i < usuario.getEquipoPokemon().length; i++) {
            Pane pane = createPanel(i);
            pane.setPrefSize(200, 50);
            pane.setStyle("-fx-background-color: white;");
            AnchorPane.setTopAnchor(pane, y);
            AnchorPane.setLeftAnchor(pane, x);

            anchorEquipo.getChildren().add(pane);

            paneMap.put("pane" + i, new PaneData(pane));

            y += 60;

        }
        anchorEquipo.setVisible(true);
        anchorEquipo.setDisable(false);
    }

    public Pane createPanel(int indicePokemon) {
        Pane pane = new Pane();

        ImageView imageView = new ImageView(new Image(Pokemon.imgRutaAbsouta(usuario.getPokemon(indicePokemon).getImagenUrlDelante())));
        imageView.setId("image");

        Label labelNom = new Label(usuario.getPokemon(indicePokemon).getMote());
        labelNom.setId("labelNom");
        Label labelNvl = new Label("Nv." + usuario.getPokemon(indicePokemon).getNivel());
        labelNvl.setId("labelLvl");
        Label labelVit = new Label(usuario.getPokemon(indicePokemon).getVitalidad() + "/" + usuario.getPokemon(indicePokemon).getVitMax());
        labelVit.setId("labelVit");

        ProgressBar progressBar = new ProgressBar();
        progressBar.setId("progressBar");
        progressBar.setProgress(((double) usuario.getPokemon(indicePokemon).getVitalidad() / usuario.getPokemon(indicePokemon).getVitMax()));
        progressBar.setStyle(BAR_VERDE);

        imageView.setLayoutX(-6);
        imageView.setLayoutY(-6);
        imageView.setFitWidth(62);
        imageView.setFitHeight(68);

        labelNom.setLayoutX(46);
        labelNom.setLayoutY(3);
        labelNvl.setLayoutX(46);
        labelNvl.setLayoutY(20);
        labelVit.setLayoutX(146);
        labelVit.setLayoutY(20);

        progressBar.setLayoutX(46);
        progressBar.setLayoutY(37);
        progressBar.setPrefSize(150, 10);

        pane.setId(String.valueOf(indicePokemon));

        pane.setOnMouseClicked((MouseEvent event) -> {
            Pane clickedPane = (Pane) event.getSource();
            pokemonUsuarioActivo = Integer.parseInt(clickedPane.getId());
            setPokeUsuario(pokemonUsuarioActivo);
            anchorEquipo.setVisible(false);
            anchorEquipo.setDisable(true);
        });


        pane.getChildren().addAll(imageView, labelNom, labelNvl, labelVit, progressBar);

        return pane;
    }

    public void pulsarHuir(){
        combate.entregarPokedolares(-1, rival);
        volverMenu();
    }
    public void volverMenu() {
        Stage stage = (Stage) botonHuir.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(SplashApplication.class.getResource("view/mainMenu-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 480);
            stage.setTitle("Menu");
            stage.setScene(scene);
            stage.show();
            MainMenuController mainMenuController = fxmlLoader.getController();
            mainMenuController.setUsuario(usuario);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
