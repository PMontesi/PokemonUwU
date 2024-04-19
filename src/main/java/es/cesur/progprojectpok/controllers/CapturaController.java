package es.cesur.progprojectpok.controllers;

import es.cesur.progprojectpok.SplashApplication;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CapturaController implements Initializable {


    @FXML
    private Button botonCapturar;
    @FXML
    private Button botonMenuPrincipal;
    @FXML
    private ImageView pokemonSalvaje;
    @FXML
    private ImageView imagenEntrenador;
    @FXML
    private TextArea logCaptura;

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
        int numPokedex = 0;

        String sqlSelectTotalPokedex = "SELECT MAX(NUM_POKEDEX) FROM POKEDEX";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlSelectTotalPokedex)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numPokedex = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        int pokemonAleatorio = (int) (Math.random() * numPokedex + 1);
        System.out.println(pokemonAleatorio);

        String sqlSelectPokemon = "SELECT * FROM POKEDEX WHERE NUM_POKEDEX = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statementSelectPokemon = connection.prepareStatement(sqlSelectPokemon)) {
            statementSelectPokemon.setInt(1, pokemonAleatorio);
            ResultSet resultSetPokemon = statementSelectPokemon.executeQuery();
            while (resultSetPokemon.next()) {
                String nombre = resultSetPokemon.getString("NOM_POKEMON");
                String tipo1 = resultSetPokemon.getString("TIPO1");
                String tipo2 = resultSetPokemon.getString("TIPO2");
                System.out.println(tipo2);
                if (tipo2 == null){ tipo2 = "null";};
                Pokemon pokemon = new Pokemon(nombre, Pokemon.TipoStringToEnum(tipo1), Pokemon.TipoStringToEnum(tipo2));
                String imagenMacho = resultSetPokemon.getString("IMAGEN_DELANTE");
                String imagenHembra= resultSetPokemon.getString("IMAGEN_DELANTE_F");

                System.out.println(pokemon.toString());
                System.out.println(imagenMacho);
                System.out.println(imagenHembra);
            }
        } catch (SQLException e) {
            e.printStackTrace();


        }


        //SUSTITUCIÓN DE IMÁGEN. NO FUNCIONA.
        /*
        URL imageUrl = getClass().getResource("images/pokemons/arbok-front");
        System.out.println(imageUrl);
        Image imagenDeRelleno = new Image(imageUrl.toString());

        pokemonSalvaje.setImage(imagenDeRelleno);

        if (imagen != null && !imagen.isEmpty()) {
            Image imagenPokemonSalvaje = new Image(imagen);
            pokemonSalvaje.setImage(imagenPokemonSalvaje);
        } else {
            // Si la URL de la imagen es nula o vacía, puedes mostrar una imagen de relleno o dejar el ImageView vacío
            // Por ejemplo, para mostrar una imagen de relleno:
            Image imagenDeRelleno = new Image("/images/entren-espaldas.png");
            pokemonSalvaje.setImage(imagenDeRelleno);
        }

         */


    }
}
