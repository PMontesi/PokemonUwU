package es.cesur.progprojectpok.controllers;

import es.cesur.progprojectpok.SplashApplication;
import es.cesur.progprojectpok.clases.*;
import es.cesur.progprojectpok.database.DBConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EquipoController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label apodoPokSelecc;

    @FXML
    private Label atNumPokSelecc;

    @FXML
    private Label atespNumPokSelecc;

    @FXML
    private Button botonAtrasColeccion;

    @FXML
    private Button botonEnviarCaja;

    @FXML
    private Button botonEnviarEquipo;

    @FXML
    private Button botonVolverMenu;

    @FXML
    private Pane colecimagenElemento1;

    @FXML
    private Label defespNumPokSelecc;

    @FXML
    private Label deffNumPokSelecc;

    @FXML
    private Text textoError;

    @FXML
    private GridPane gridCaja;

    @FXML
    private Pane imagenElemento2;

    @FXML
    private ImageView imgPokeSelecc;

    @FXML
    private Pane paneInfoPok;

    @FXML
    private ProgressBar progVitPokSelecc;

    @FXML
    private Label razaPokSelecc;

    @FXML
    private Label tipo1PokSelecc;

    @FXML
    private Label tipo2PokSelecc;

    @FXML
    private Label velNumPokSelecc;

    @FXML
    private Label vitNumPokSelecc;


    private Entrenador usuario;
    private Pokemon[] pokemonCaja = new Pokemon[18];
    private Map<String, PaneData> paneMap;
    private int equipoSelect = -1;
    private int cajaSelect = -1;
    private int caja0Equipo = 2;
    private boolean equipoLleno = false;
    private int contEquipo;

    public void setUsuario(Entrenador usuario) {
        this.usuario = usuario;
        cargarEquipo();
        meterArray();
        cargarCaja();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cargarEquipo() {

        double x = 35;
        double y = 98;
        paneMap = new HashMap<>();

        for (int i = 0; i < usuario.getEquipoPokemon().length; i++) {
            Pane pane = createPanel(i);
            pane.setPrefSize(200, 50);
            pane.setStyle("-fx-background-color: white;");
            AnchorPane.setTopAnchor(pane, y);
            AnchorPane.setLeftAnchor(pane, x);

            anchorPane.getChildren().add(pane);

            paneMap.put("pane" + i, new PaneData(pane));

            y += 60;

            if (usuario.getPokemon(i) != null){
                contEquipo++;
            }

        }
    }

    //HAY QUE HACER ALGO CON EL TEMA DEL ÍNDICE
    public void cargarCaja() {

        int numRows = gridCaja.getRowCount();
        int numCols = gridCaja.getColumnCount();
        int indice = 0;

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            for (int colIndex = 0; colIndex < numCols; colIndex++) {
                ImageView imageView;

                if (indice < pokemonCaja.length && pokemonCaja[indice] != null) {
                    String imgUrl = Pokemon.imgRutaAbsouta(pokemonCaja[indice].getImagenUrlDelante());

                    imageView = new ImageView(new Image(imgUrl));
                    imageView.setId(String.valueOf(indice));
                    createImageViewCaja(imageView, pokemonCaja[indice]);
                    imageView.setUserData(colIndex + "" + rowIndex);
                } else {

                    imageView = new ImageView();
                    imageView.setId(String.valueOf(indice));
                }

                gridCaja.add(imageView, colIndex, rowIndex);
                indice++;
            }
        }
    }

    /*

    Envía el pokemon seleccionado a la caja.

    Primero actualiza la imágen del grid y desactiva el pane correspondiente.
    Segundo actualiza la base de datos.
    Tercero actualiza los arrays.

     */
    public void enviarCaja() {

        String imgUrl = Pokemon.imgRutaAbsouta(usuario.getPokemon(equipoSelect).getImagenUrlDelante());
        Image image = new Image(imgUrl);
        rellenarGridSinImagen(image, usuario.getPokemon(equipoSelect));

        actualizarPane(equipoSelect);

        caja0Equipo = 1;
        actualizarDB(usuario.getPokemon(equipoSelect), caja0Equipo);
        caja0Equipo = 2;

        for (int i = 0; i < pokemonCaja.length; i++) {
            if(pokemonCaja[i] == null){
                pokemonCaja[i] = usuario.getPokemon(equipoSelect);
                break;
            }
        }
        usuario.setPokemon(null, equipoSelect);
        equipoSelect = -1;
        contEquipo--;

    }

    /*

    Envía el pokemon seleccionado al equipo.

    Primero añade el pokemon al array del equipo.
    Segundo actualiza el pane correspondient y lo activa.
    Tercero borra la imagen del grid.
    Cuarto actualiza la BBDD.
    Quinto borra al pokemon del array de la caja.

     */

    public void enviarEquipo() {
        if (contEquipo < 6){
            Pokemon pokemon = pokemonCaja[cajaSelect];
            int i;
            for (i = 0; i < usuario.getEquipoPokemon().length; i++) {
                if (usuario.getPokemon(i) == null){
                    usuario.setPokemon(pokemonCaja[cajaSelect], i);
                    break;
                }
            }

            Image newImage = new Image(Pokemon.imgRutaAbsouta(pokemon.getImagenUrlDelante()));

            PaneData paneData = paneMap.get("pane" + i);
            paneData.actualizarElementos(pokemon.getMote(), newImage, pokemon.getNivel(), pokemon.getVitalidad(), pokemon.getVitMax(), pokemon);
            encenderPane(i);

            ImageView imageViewToRemove = obtenerImageViewDelGrid(cajaSelect);
            if (imageViewToRemove != null) {
                imageViewToRemove.setImage(null);
            }

            caja0Equipo = 0;
            actualizarDB(pokemon, caja0Equipo);
            caja0Equipo = 2;

            pokemonCaja[cajaSelect] = null;
            contEquipo++;
            cajaSelect = -1;
        }
        else {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(3), event -> {
                        textoError.setVisible(false);
                    })
            );
            textoError.setText("Equipo Pokemon lleno");
            textoError.setVisible(true);
            timeline.play();
        }

    }

    /*

    Método para crear los paneles del equipo.
    Crea también los elementos del panel.
    También incluye un MouseEvent para poder seleccionar al pokemon y realizar más acciones con él.

     */
    //SOLUCIONAR EL TEMA DE QUE LOS POKEMONS CAPTURADOS NO TIENEN IMAGEN.
    public Pane createPanel(int indicePokemon) {
        Pane pane = new Pane();
        ImageView imageView = new ImageView();
        Label labelNom = new Label();
        Label labelNvl = new Label();
        Label labelVit = new Label();
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(0);

        if (usuario.getPokemon(indicePokemon) != null){
            imageView.setImage(new Image(Pokemon.imgRutaAbsouta(usuario.getPokemon(indicePokemon).getImagenUrlDelante())));
            labelNom.setText(usuario.getPokemon(indicePokemon).getMote());
            labelNvl.setText("Nv." + usuario.getPokemon(indicePokemon).getNivel());
            labelVit.setText(usuario.getPokemon(indicePokemon).getVitalidad() + "/" + usuario.getPokemon(indicePokemon).getVitMax());
            progressBar.setProgress(((double) usuario.getPokemon(indicePokemon).getVitalidad() / usuario.getPokemon(indicePokemon).getVitMax()));
        }

        imageView.setId("image");

        labelNom.setId("labelNom");

        labelNvl.setId("labelLvl");

        labelVit.setId("labelVit");

        progressBar.setId("progressBar");

        progressBar.setStyle("-fx-accent: green;");

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

            cambiarInfo(usuario.getPokemon(Integer.parseInt(clickedPane.getId())));
            botonEnviarCaja.setVisible(true);
            botonEnviarCaja.setDisable(false);
            botonEnviarEquipo.setVisible(false);
            botonEnviarEquipo.setDisable(true);
            equipoSelect = Integer.parseInt(clickedPane.getId());
        });


        pane.getChildren().addAll(imageView, labelNom, labelNvl, labelVit, progressBar);

        return pane;
    }

    /*

    Crea ImageView para el GridPane de la caja.
    Se usa tanto al cargar la caja por primera vez como al psarle un pokemon a la caja.
    Añade a las imágenes un MouseEvent para poder interactuar con el pokemon y realizar más acciones con él

     */
    public void createImageViewCaja(ImageView imageView, Pokemon pokemon) {
        imageView.setFitHeight(62);
        imageView.setFitWidth(68);


        imageView.setOnMouseClicked((MouseEvent event) -> {

            cambiarInfo(pokemon);
            botonEnviarCaja.setVisible(false);
            botonEnviarCaja.setDisable(true);
            botonEnviarEquipo.setVisible(true);
            botonEnviarEquipo.setDisable(false);
            cajaSelect = Integer.parseInt(imageView.getId());

        });
    }

    //Méte todos los pokemons que en la BBDD están guardados en la caja en un ArrayList.
    public void meterArray() {
        String sqlCajaPokemon = "SELECT * FROM POKEMON_CAJA WHERE ID_ENTRENADOR = ?";
        int indice = 0;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statementCajaPokemon = connection.prepareStatement(sqlCajaPokemon)) {
            statementCajaPokemon.setInt(1, usuario.getIdUsuario());
            ResultSet resultSetEquipoPokemon = statementCajaPokemon.executeQuery();
            while (resultSetEquipoPokemon.next()) {

                Pokemon pokemon = new Pokemon(
                        resultSetEquipoPokemon.getString("MOTE"),
                        resultSetEquipoPokemon.getInt("VITALIDAD"),
                        resultSetEquipoPokemon.getInt("VIT_MAX"),
                        resultSetEquipoPokemon.getInt("ATAQUE"),
                        resultSetEquipoPokemon.getInt("DEFENSA"),
                        resultSetEquipoPokemon.getInt("AT_ESPECIAL"),
                        resultSetEquipoPokemon.getInt("DEF_ESPECIAL"),
                        resultSetEquipoPokemon.getInt("VELOCIDAD"),
                        resultSetEquipoPokemon.getInt("NIVEL"),
                        resultSetEquipoPokemon.getInt("EXPERIENCIA"),
                        resultSetEquipoPokemon.getInt("NUM_POKEDEX"),
                        resultSetEquipoPokemon.getInt("ID_POKEMON"),
                        resultSetEquipoPokemon.getString("IMAGEN_DETRAS"),
                        resultSetEquipoPokemon.getString("IMAGEN_DELANTE"),
                        resultSetEquipoPokemon.getString("TIPO1"),
                        resultSetEquipoPokemon.getString("TIPO2"),
                        resultSetEquipoPokemon.getString("ESTADO"),
                        null
                );
                pokemon.asignarMovimientos(pokemon.getId());
                pokemonCaja[indice] = pokemon;
                indice++;
            }
            resultSetEquipoPokemon.close();
            statementCajaPokemon.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*

    Cambia la información que sale en el panel de la derecha del t0d0 de la vista

     */
    public void cambiarInfo(Pokemon pokemon) {
        apodoPokSelecc.setText(pokemon.getMote());
        razaPokSelecc.setText(pokemon.getNombre());
        tipo1PokSelecc.setText(Pokemon.TipoEnumToString(pokemon.getTipo1()));
        if (pokemon.getTipo2() == Tipos.NULL) tipo2PokSelecc.setVisible(false);
        else {
            tipo2PokSelecc.setVisible(true);
            tipo2PokSelecc.setText(Pokemon.TipoEnumToString(pokemon.getTipo2()));
        }
        vitNumPokSelecc.setText(pokemon.getVitalidad() + "/" + pokemon.getVitMax());
        progVitPokSelecc.setProgress((double) pokemon.getVitalidad() / pokemon.getVitMax());

        atNumPokSelecc.setText(String.valueOf(pokemon.getAtaque()));
        deffNumPokSelecc.setText(String.valueOf(pokemon.getDefensa()));
        atespNumPokSelecc.setText(String.valueOf(pokemon.getAtaqueEspecial()));
        defespNumPokSelecc.setText(String.valueOf(pokemon.getDefensaEspecial()));
        velNumPokSelecc.setText(String.valueOf(pokemon.getVelocidad()));

        imgPokeSelecc.setImage(new Image(Pokemon.imgRutaAbsouta(pokemon.getImagenUrlDelante())));
    }

    /*

    Recorre todos los nodos del GridPane de la Caja Pokemon en busca de un nodo que no tenga imagen.
    Al encontrarlo le pone la imagen que se le pasa y para el búcle.

     */
    public void rellenarGridSinImagen(Image image, Pokemon pokemon) {

        for (Node node : gridCaja.getChildren()){

            if (((ImageView) node).getImage() == null){
                int rowIndex = GridPane.getRowIndex(node) == null ? 0 : GridPane.getRowIndex(node);

                int columnIndex = GridPane.getColumnIndex(node) == null ? 0 : GridPane.getColumnIndex(node);

                ((ImageView) node).setImage(image);
                createImageViewCaja(((ImageView) node), pokemon);


                ((ImageView) node).setUserData(columnIndex + "" + rowIndex);
                break;
            }
        }
    }

    public void actualizarPane(int indice){
        PaneData paneData = paneMap.get("pane" + indice);

        paneData.getImage().setVisible(false);
        paneData.getLabelLvl().setVisible(false);
        paneData.getLabelNom().setVisible(false);
        paneData.getLabelVit().setVisible(false);


    }

    public void encenderPane(int indice){
        PaneData paneData = paneMap.get("pane" + indice);

        paneData.getImage().setVisible(true);
        paneData.getLabelLvl().setVisible(true);
        paneData.getLabelNom().setVisible(true);
        paneData.getLabelVit().setVisible(true);


    }

    private ImageView obtenerImageViewDelGrid(int indiceCaja) {
        for (Node node : gridCaja.getChildren()) {
            if (node instanceof ImageView && Integer.parseInt(node.getId()) == indiceCaja) {
                return (ImageView) node;
            }
        }
        return null;
    }

    private void actualizarDB(Pokemon pokemon, int caja0Equipo){
        String sqlCaja0Equipo = "";
        switch (caja0Equipo){
            case 0 -> sqlCaja0Equipo = "UPDATE POKEMON SET CAJA = 0 ";
            case 1 -> sqlCaja0Equipo = "UPDATE POKEMON SET CAJA = 1 ";
            default -> sqlCaja0Equipo = "";
        }

        sqlCaja0Equipo += "WHERE ID_POKEMON = ?";

        try(Connection connection = DBConnection.getConnection();
        PreparedStatement statementCaja0Equipo = connection.prepareStatement(sqlCaja0Equipo)){

            statementCaja0Equipo.setInt(1, pokemon.getId());
            statementCaja0Equipo.executeUpdate();

            statementCaja0Equipo.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void vovlerMenu(){
        boolean equipoVacio = false;
        for (int i = 0; i < usuario.getEquipoPokemon().length; i++) {
            if (usuario.getPokemon(i) != null){
                equipoVacio = false;
                break;
            }
            else equipoVacio = true;
        }

        if (equipoVacio){
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(3), event -> {
                        textoError.setVisible(false);
                    })
            );
            textoError.setText("Necesitas al menos 1 Pokemon en tu equipo");
            textoError.setVisible(true);
            timeline.play();
        }
        else {
            Stage stage = (Stage) botonVolverMenu.getScene().getWindow();
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
}
