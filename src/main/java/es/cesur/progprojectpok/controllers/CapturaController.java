package es.cesur.progprojectpok.controllers;

import es.cesur.progprojectpok.SplashApplication;
import es.cesur.progprojectpok.clases.Entrenador;
import es.cesur.progprojectpok.clases.Pokemon;
import es.cesur.progprojectpok.clases.Tipos;
import es.cesur.progprojectpok.database.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class CapturaController implements Initializable {


    @FXML
    private Button botonCapturar;
    @FXML
    private Button botonMenuPrincipal;
    @FXML
    private ImageView pokemonSalvajeImagen;
    @FXML
    private ImageView imagenEntrenador;
    @FXML
    private TextArea logCaptura;
    private Pokemon pokemonSalvaje;

    public void volverMenu() {
        Stage stage = (Stage) botonMenuPrincipal.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(SplashApplication.class.getResource("view/mainMenu-view.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int numPokedexTotal = 0;
        String imagenURL = "";
        pokemonSalvaje = new Pokemon();

        //Primer select para que el rango del math.random funcione.
        String sqlSelectTotalPokedex = "SELECT MAX(NUM_POKEDEX) FROM POKEDEX";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlSelectTotalPokedex)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numPokedexTotal = resultSet.getInt(1);
            }
            System.out.println("Maxnumpokedex: " + numPokedexTotal);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        int pokemonAleatorio = (int) (Math.random() * numPokedexTotal + 1);
        System.out.println(pokemonAleatorio);

        //Segundo select para obtener el Pokemon con el número aleatorio generado.
        String sqlSelectPokemon = "SELECT * FROM POKEDEX WHERE NUM_POKEDEX = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statementSelectPokemon = connection.prepareStatement(sqlSelectPokemon)) {
            statementSelectPokemon.setInt(1, pokemonAleatorio);
            ResultSet resultSetPokemon = statementSelectPokemon.executeQuery();

            //Construcción del Pokemon en base a las columnas del segundo SELECT y obtención de la URL de la imagen.
            while (resultSetPokemon.next()) {
                String nombre = resultSetPokemon.getString("NOM_POKEMON");
                String tipo1 = resultSetPokemon.getString("TIPO1");
                String tipo2 = resultSetPokemon.getString("TIPO2");
                if (tipo2 == null){ tipo2 = "null";}
                int numPokedex = resultSetPokemon.getInt(("NUM_POKEDEX"));
                pokemonSalvaje = new Pokemon(nombre, numPokedex, Pokemon.TipoStringToEnum(tipo1), Pokemon.TipoStringToEnum(tipo2));
                if(pokemonSalvaje.getSexo() == 'H' && resultSetPokemon.getString("IMAGEN_DELANTE_F") != null){
                    imagenURL = resultSetPokemon.getString("IMAGEN_DELANTE_F");
                }else imagenURL = resultSetPokemon.getString("IMAGEN_DELANTE");


                //Para comprobar que todo funciona
                String imagenMacho = resultSetPokemon.getString("IMAGEN_DELANTE");
                String imagenHembra = resultSetPokemon.getString("IMAGEN_DELANTE_F");
                System.out.println(pokemonSalvaje.toString());
                System.out.println("Imagen macho: " + imagenMacho);
                System.out.println("Imagen hembra: " + imagenHembra);
                System.out.println("Imagen url: " + imagenURL);
            }
        } catch (SQLException e) {
            e.printStackTrace();


        }
        //Imágen del pokemon seleccionado. En teoría, funciona, pero la imagen no sale.
        File archivo = new File(imagenURL);
        System.out.println("URL File: " + archivo.toString());
        Image imagenPokemonGenerado = new Image(archivo.toURI().toString());
        System.out.println(imagenPokemonGenerado.getUrl());
        System.out.println("URL imagenPokemonGenerado: " + archivo.toURI().toString());
        pokemonSalvajeImagen.setImage(imagenPokemonGenerado);

        //Log de captura.
        logCaptura.setStyle("-fx-text-fill: blue");
        logCaptura.setText("Un " + pokemonSalvaje.getNombre() + " salvaje ha aparecido");

    }

    //Cuando se haga la mochila y la tienda, habrá que añadir la función de reducir la cantidad de pokéballs
    public void lanzarPokeball() {
        int probCaptura = (int) (Math.random()*100 +1);
        if (probCaptura > 33){
            logCaptura.setStyle("-fx-text-fill: green");
            logCaptura.setText(pokemonSalvaje.getNombre() + " salvaje ha sido capturado con éxito");
            Entrenador.capturarPokemon(pokemonSalvaje);
        }
        else {
            logCaptura.setStyle("-fx-text-fill: red");
            logCaptura.setText(pokemonSalvaje.getNombre() + " salvaje ha huído");
            pokemonSalvaje = null; //quizá haga falta desarrollar más esta parte.
        }
    }



}
