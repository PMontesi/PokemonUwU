package es.cesur.progprojectpok.controllers;

import es.cesur.progprojectpok.HelloApplication;
import es.cesur.progprojectpok.SplashApplication;
import es.cesur.progprojectpok.clases.Entrenador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private Button menuSalir;
    @FXML
    private Button menuCerrarsesion;
    @FXML
    private Button menuCombate;
    @FXML
    private Button menuCrianza;
    @FXML
    private Button menuCentroPoke;
    @FXML
    private Button menuEntrenamiento;
    @FXML
    private Button menuEquipo;
    @FXML
    private Button menuCaptura;
    private Entrenador usuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUsuario(Entrenador usuario){
        this.usuario = usuario;
        for (int i = 0; i < usuario.getEquipoPokemon().length; i++) {
            if (usuario.getPokemon(i) != null){
                break;
            }
            else desabilitarBotones();
        }
    }

    public void desabilitarBotones(){
        menuCombate.setDisable(true);
        menuCrianza.setDisable(true);
        menuCentroPoke.setDisable(true);
        menuEntrenamiento.setDisable(true);
        menuEquipo.setDisable(true);
    }

    public void cerrarSesion(){
        Stage stage = (Stage) menuCerrarsesion.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(SplashApplication.class.getResource("view/login-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 480);
            stage.setTitle("Menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void abrirMenuEntrenamiento(){
        Stage stage = (Stage) menuEntrenamiento.getScene().getWindow();
        stage.close();


        FXMLLoader fxmlLoader = new FXMLLoader(SplashApplication.class.getResource("view/entrenamiento-view.fxml"));



        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 480);
            EntrenamientoController entrenamientoController = fxmlLoader.getController();
            entrenamientoController.setUsuario(usuario);
            stage.setTitle("Menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void abrirMenuCombate(){
        Stage stage = (Stage) menuCombate.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(SplashApplication.class.getResource("view/combate-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 480);
            stage.setTitle("Menu");
            stage.setScene(scene);
            stage.show();
            CombateController combateController = fxmlLoader.getController();
            combateController.setUsuario(usuario);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void abrirMenuCrianza(){
        Stage stage = (Stage) menuCrianza.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(SplashApplication.class.getResource("view/crianza-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 480);
            stage.setTitle("Menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void abrirMenuCentroPoke(){
        Stage stage = (Stage) menuCentroPoke.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(SplashApplication.class.getResource("view/centro-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 480);
            stage.setTitle("Menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void abrirMenuCaptura(){
        Stage stage = (Stage) menuCaptura.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(SplashApplication.class.getResource("view/captura-view.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 480);
            CapturaController capturaController = fxmlLoader.getController();
            capturaController.setUsuario(usuario);
            stage.setTitle("Menu");
            stage.setScene(scene);

            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void abrirMenuEquipo(){

        Stage stage = (Stage) menuEquipo.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(SplashApplication.class.getResource("view/equipo-view.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 480);
            stage.setTitle("Menu");
            stage.setScene(scene);
            EquipoController equipoController = fxmlLoader.getController();
            equipoController.setUsuario(usuario);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void salirPrograma (){
        Stage stage = (Stage) menuSalir.getScene().getWindow();
        stage.close();
    }

}
