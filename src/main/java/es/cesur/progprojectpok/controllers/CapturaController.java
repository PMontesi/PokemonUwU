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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class CapturaController implements Initializable {


    @FXML
    public Button botonBuscar;
    @FXML
    public Button moteSi;
    @FXML
    public Button moteNo;
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
    @FXML
    private TextField textoMote;
    private Pokemon pokemonSalvaje;
    private Entrenador usuario;
    private Random r = new Random();
    private String imagenDetrasPokSalvaje;
    private String imagenDelantePokSalvaje;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        moteSi.setVisible(false);
        moteNo.setVisible(false);
        textoMote.setVisible(false);
        pokemonNuevo();

    }
    public void setUsuario(Entrenador usuario){
        this.usuario = usuario;
    }


    //Cuando se haga la mochila y la tienda, habrá que añadir la función de reducir la cantidad de pokéballs
    public void lanzarPokeball() {
        int probCaptura = (r.nextInt(3) +1);
        if (probCaptura >= 2 && pokemonSalvaje != null){
            logCaptura.setStyle("-fx-text-fill: green");
            logCaptura.setText(pokemonSalvaje.getNombre() + " salvaje ha sido capturado con éxito");
            moteSi.setVisible(true);
            moteNo.setVisible(true);
            textoMote.setVisible(true);

        }
        else if (probCaptura == 1 && pokemonSalvaje != null) {
            logCaptura.setStyle("-fx-text-fill: red");
            logCaptura.setText(pokemonSalvaje.getNombre() + " salvaje ha huído");
            pokemonSalvajeImagen.setVisible(false);
            pokemonSalvaje = null;
        } else if (pokemonSalvaje == null) {
            logCaptura.setText("¡No hay ningún pokemon al que lanzarle una pokéball!");
        }
    }


    public void ponerMoteTrue(){

        if(textoMote.getText().isEmpty()){
            pokemonSalvaje.setMote(pokemonSalvaje.getNombre());
        }else pokemonSalvaje.setMote(textoMote.getText());

        usuario.capturarPokemon(pokemonSalvaje, imagenDetrasPokSalvaje, imagenDelantePokSalvaje);
        pokemonSalvajeImagen.setVisible(false);
        pokemonSalvaje = null;
        moteSi.setVisible(false);
        moteNo.setVisible(false);
        textoMote.setVisible(false);
    }
    public void ponerMoteFalse(){

        pokemonSalvaje.setMote(pokemonSalvaje.getNombre());
        usuario.capturarPokemon(pokemonSalvaje, imagenDetrasPokSalvaje, imagenDelantePokSalvaje);
        pokemonSalvajeImagen.setVisible(false);
        pokemonSalvaje = null;
        moteSi.setVisible(false);
        moteNo.setVisible(false);
        textoMote.setVisible(false);
    }

    public void pokemonNuevo(){

        pokemonSalvajeImagen.setVisible(true);
        pokemonSalvaje = new Pokemon();

        //Segundo select para obtener el Pokemon con el número aleatorio generado.
        String sqlSelectPokemon = "SELECT * FROM POKEDEX ORDER BY RAND() LIMIT 1";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statementSelectPokemon = connection.prepareStatement(sqlSelectPokemon)
        ) {
            ResultSet resultSetPokemon = statementSelectPokemon.executeQuery();

            //Construcción del Pokemon en base a las columnas del segundo SELECT y obtención de la URL de la imagen.
            while (resultSetPokemon.next()) {
                String nombre = resultSetPokemon.getString("NOM_POKEMON");
                int numPokedex = resultSetPokemon.getInt(("NUM_POKEDEX"));
                Tipos tipo1 = Pokemon.TipoStringToEnum(resultSetPokemon.getString("TIPO1"));
                Tipos tipo2 = Pokemon.TipoStringToEnum(resultSetPokemon.getString("TIPO2"));

                pokemonSalvaje = new Pokemon(nombre, numPokedex, tipo1, tipo2);
                if(pokemonSalvaje.getSexo() == 'H' && resultSetPokemon.getString("IMAGEN_DELANTE_F") != null){
                    imagenDelantePokSalvaje = resultSetPokemon.getString("IMAGEN_DELANTE_F");
                    imagenDetrasPokSalvaje = resultSetPokemon.getString("IMAGEN_DETRAS_F");
                }else {
                    imagenDelantePokSalvaje = resultSetPokemon.getString("IMAGEN_DELANTE");
                    imagenDetrasPokSalvaje = resultSetPokemon.getString("IMAGEN_DETRAS");
                }
            }
            resultSetPokemon.close();
            statementSelectPokemon.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Image imagenPokemonGenerado = new Image(Pokemon.imgRutaAbsouta(imagenDelantePokSalvaje));
        pokemonSalvajeImagen.setImage(imagenPokemonGenerado);

        //Log de captura.
        logCaptura.setStyle("-fx-text-fill: blue");
        logCaptura.setText("Un " + pokemonSalvaje.getNombre() + " salvaje ha aparecido");
    }

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
            MainMenuController mainMenuController = fxmlLoader.getController();
            mainMenuController.setUsuario(usuario);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

