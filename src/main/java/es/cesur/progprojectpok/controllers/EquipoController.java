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
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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


    private Entrenador usuario;
    private Pokemon pokemon;

    public void setUsuario(Entrenador usuario){
        this.usuario = usuario;
        mostrarEquipo();

        File archivo = new File(usuario.getPokemon(0).getImagenUrl());
        String rutaAbsoluta = archivo.getAbsolutePath();
        if(System.getProperty("os.name").startsWith("Windows")){
            rutaAbsoluta = rutaAbsoluta.replace("/", "\\");
        }

        Image imagenPokemonGenerado = new Image(rutaAbsoluta);
        pokemonPrueba.setImage(imagenPokemonGenerado);

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

    //TIENE QUE METER LAS IMAGENES Y DEMÁS DATOS DENTRO DE LOS PANE.
    //QUIZÁ SE PUEDA AHORRAR ESTE PASO GUARDANDO EL EQUIPO POKEMON AL HACER LOGIN.
    public void mostrarEquipo(){
        System.out.println(usuario.getPokemon(0).toString());
        pane = new Pane();
        pane.setPrefSize(200, 50);
        pane.setStyle("-fx-background-color: red;");

        createPanel(pane, 0, -6, -6);




        AnchorPane.setTopAnchor(pane, 300.0);
        AnchorPane.setLeftAnchor(pane, 10.0);



        anchorPane.getChildren().add(pane);

        //for (int i = 0; i < usuario.getEquipoPokemon().length; i++) {
        //
        //}

    }

    //TIENE QUE METER LAS IMÁGENES Y DEMÁS EN EL GRID
    public void mostrarCaja(){
        String sqlCajaPokemon = "SELECT * FROM POKEMON_CAJA WHERE ID_ENTRENADOR = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statementCajaPokemon = connection.prepareStatement(sqlCajaPokemon)){
            statementCajaPokemon.setInt(1, usuario.getIdUsuario());
            ResultSet resultSetEquipoPokemon = statementCajaPokemon.executeQuery();
            while (resultSetEquipoPokemon.next()){
                //Método para poner imágene y tal
            }



        }catch (SQLException e){

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


    //AMPLIAR MÉTODO PARA RECREAR CADA PANE CON SUS MOVIDAS
    public void createPanel(Pane pane, int indicePokemon, double x, double y){
        File archivo = new File(usuario.getPokemon(0).getImagenUrl());
        String rutaAbsoluta = archivo.getAbsolutePath();
        if(System.getProperty("os.name").startsWith("Windows")){
            rutaAbsoluta = rutaAbsoluta.replace("/", "\\");
        }

        Image imagenPokemonGenerado = new Image(rutaAbsoluta);
        ImageView imageView = new ImageView(imagenPokemonGenerado);

        Label labelNom = new Label(usuario.getPokemon(0).getMote());

        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(62);
        imageView.setFitHeight(68);

        labelNom.setLayoutX(46);
        labelNom.setLayoutY(3);

        pane.getChildren().addAll(imageView, labelNom);
    }
}
