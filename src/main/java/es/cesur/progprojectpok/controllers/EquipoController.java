package es.cesur.progprojectpok.controllers;

import es.cesur.progprojectpok.clases.Entrenador;
import es.cesur.progprojectpok.clases.Pokemon;
import es.cesur.progprojectpok.database.DBConnection;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class EquipoController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Pane colecpanePoke1;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView pokemonPrueba;
    @FXML
    private GridPane gridCaja;


    private Entrenador usuario;
    //private Pokemon pokemon;
    private ArrayList<Pokemon> pokemonCaja = new ArrayList<>();

    public void setUsuario(Entrenador usuario){
        this.usuario = usuario;
        mostrarEquipo();
        mostrarCaja();

        /*
        File archivo = new File(usuario.getPokemon(0).getImagenUrl());
        String rutaAbsoluta = archivo.getAbsolutePath();
        if(System.getProperty("os.name").startsWith("Windows")){
            rutaAbsoluta = rutaAbsoluta.replace("/", "\\");
        }

        Image imagenPokemonGenerado = new Image(rutaAbsoluta);
        pokemonPrueba.setImage(imagenPokemonGenerado);

         */

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*
        Pane pane = new Pane();
        pane.setPrefSize(150, 50);
        pane.setVisible(true);
        pane.setStyle("-fx-accent: red;");
        pane.setStyle("-fx-background-color: red;");
        pane.toFront();


        AnchorPane.setTopAnchor(pane, 100.0);
        AnchorPane.setLeftAnchor(pane, 100.0);

        anchorPane.getChildren().add(pane);

         */

    }

    public void mostrarEquipo(){
        System.out.println(usuario.getPokemon(0).toString());

        double x = 35;
        double y = 98;
        for (int i = 0; i < usuario.getEquipoPokemon().length; i++) {
            pane = new Pane();
            pane.setPrefSize(200, 50);
            pane.setStyle("-fx-background-color: white;");
            createPanel(pane, i, -6, -6);

            AnchorPane.setTopAnchor(pane, y);
            AnchorPane.setLeftAnchor(pane, x);
            anchorPane.getChildren().add(pane);

            y+=60;
        }
    }

    //TIENE QUE METER LAS IMÁGENES Y DEMÁS EN EL GRID
    public void mostrarCaja(){

        meterArray();

        int rowIndex = 0;
        int colIndex = 0;


        for (Pokemon pokemon : pokemonCaja) {
            String imgUrl = Pokemon.imgRutaAbsouta(pokemon.getImagenUrl());
            System.out.println(imgUrl);
            ImageView imageView = new ImageView(new Image(imgUrl));
            imageView.setFitHeight(62);
            imageView.setFitWidth(68);

            imageView.setUserData(colIndex + "" + rowIndex);


            imageView.setOnMouseClicked((MouseEvent event) -> {
                System.out.println("Imagen");
            });


            gridCaja.add(imageView, colIndex, rowIndex);
            colIndex++;
            if (colIndex >= gridCaja.getColumnCount()) {
                colIndex = 0;
                rowIndex++;
            }
        }
    }

    //QUIZÁ HAYA QUE PASARLE LA ID COMO PARÁMETRO??
    public void cambiarPokemonCaja(){
        String sqlCambioCaja = "MODIFY VIEW POKEMON_EQUIPO SET CAJA = 1 WHERE ID_POKEMON = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statementCambioCaja = connection.prepareStatement(sqlCambioCaja)){
            statementCambioCaja.setInt(1, usuario.getPokemon(1).getId());
            statementCambioCaja.executeUpdate();




        }catch (SQLException e){

        }
    }

    public void cambiarPokemonEquipo(){
        String sqlCambioCaja = "MODIFY VIEW POKEMON_CAJA SET CAJA = 0 WHERE ID_POKEMON = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statementCambioCaja = connection.prepareStatement(sqlCambioCaja)){
            statementCambioCaja.setInt(1, usuario.getPokemon(1).getId());
            statementCambioCaja.executeUpdate();




        }catch (SQLException e){

        }
    }



    public void createPanel(Pane pane, int indicePokemon, double x, double y){

        ImageView imageView = new ImageView(new Image(Pokemon.imgRutaAbsouta(usuario.getPokemon(indicePokemon).getImagenUrl())));

        Label labelNom = new Label(usuario.getPokemon(indicePokemon).getMote());
        Label labelNvl = new Label("Nv." + usuario.getPokemon(indicePokemon).getNivel());
        Label labelVit = new Label(usuario.getPokemon(indicePokemon).getVitalidad() + "/" + usuario.getPokemon(indicePokemon).getVitMax());

        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(((double) usuario.getPokemon(indicePokemon).getVitalidad() /usuario.getPokemon(indicePokemon).getVitMax()));
        progressBar.setStyle("-fx-accent: green;");

        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
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

        pane.getChildren().addAll(imageView, labelNom, labelNvl, labelVit, progressBar);
    }

    //Méte todos los pokemons en un array.
    public void meterArray(){
        String sqlCajaPokemon = "SELECT * FROM POKEMON_CAJA WHERE ID_ENTRENADOR = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statementCajaPokemon = connection.prepareStatement(sqlCajaPokemon)){
            statementCajaPokemon.setInt(1, usuario.getIdUsuario());
            ResultSet resultSetEquipoPokemon = statementCajaPokemon.executeQuery();
            while (resultSetEquipoPokemon.next()){

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
                        resultSetEquipoPokemon.getString("TIPO1"),
                        resultSetEquipoPokemon.getString("TIPO2"),
                        resultSetEquipoPokemon.getString("ESTADO"),
                        null
                );
                pokemon.asignarMovimientos(pokemon.getId());
                pokemonCaja.add(pokemon);
            }
            resultSetEquipoPokemon.close();
            statementCajaPokemon.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
