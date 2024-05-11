package es.cesur.progprojectpok.controllers;

import es.cesur.progprojectpok.SplashApplication;
import es.cesur.progprojectpok.clases.Entrenador;
import es.cesur.progprojectpok.clases.Objeto;
import es.cesur.progprojectpok.clases.Pokemon;
import es.cesur.progprojectpok.database.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private TextField loginUsuario;
    @FXML
    private TextField loginContra;
    @FXML
    private Text textoError;
    @FXML
    private Button loginRegistro;
    @FXML
    private Button loginEntrar;
    @FXML
    private Button loginSalir;
    private Entrenador usuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void entrarUsuario() throws SQLException {
        String username = loginUsuario.getText();
        String pass = loginContra.getText();
        String sql = "SELECT * FROM ENTRENADOR WHERE NOM_ENTRENADOR = ? AND PASS = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, pass);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                usuario = new Entrenador(
                        resultSet.getString("NOM_ENTRENADOR"),
                        resultSet.getInt("POKEDOLLARS"),
                        resultSet.getInt("ID_ENTRENADOR")
                );

                usuario.setPokedolares(usuario.getPokedolares());
                usuario.setIdUsuario(usuario.getIdUsuario());
                usuario.setNombreUsuario(usuario.getNombreUsuario());
                cargarEquipo();


                System.out.println(usuario.getIdUsuario());
                Stage stage = (Stage) loginUsuario.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(SplashApplication.class.getResource("view/mainMenu-view.fxml"));
                Scene scene = scene = new Scene(fxmlLoader.load(), 800, 480);
                    stage.setTitle("Menu");
                    stage.setScene(scene);
                    MainMenuController mainMenuController = fxmlLoader.getController();
                    mainMenuController.setUsuario(usuario);
                    stage.show();

                //Pasar el usuario al main menu

                //Estas dos líneas de arriba
            } else {
                textoError.setText("Sos un putiaso");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException | IOException e) {
            textoError.setText("La base de datos es una putisasa");
            e.printStackTrace();
        }

    }

    @FXML
    private void registrarUsuario() throws SQLException {
        String username = loginUsuario.getText();
        String pass = loginContra.getText();
        Random random = new Random();
        int pokedollares = random.nextInt(201)+800;
        String sql = "INSERT INTO ENTRENADOR (NOM_ENTRENADOR, PASS, POKEDOLLARS) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, pass);
            statement.setInt(3, pokedollares);
            statement.executeUpdate();

            /*
            if (resultSet.next()) {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(SplashApplication.class.getResource("view/mainMenu-view.fxml"));
                Scene scene = scene = new Scene(fxmlLoader.load(), 800, 480);
                stage.setTitle("Menu");
                stage.setScene(scene);
                stage.show();

            } else {
                System.out.println("Sos un puto");
            }

             */
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salirPrograma (){
        Stage stage = (Stage) loginSalir.getScene().getWindow();
        stage.close();
    }


    public void cargarEquipo(){
        String sqlEquipoPokemon = "SELECT * FROM POKEMON_EQUIPO WHERE ID_ENTRENADOR = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statementEquipoPokemon = connection.prepareStatement(sqlEquipoPokemon)){
            statementEquipoPokemon.setInt(1, usuario.getIdUsuario());
            ResultSet resultSetEquipoPokemon = statementEquipoPokemon.executeQuery();
            int indice = 0;

            while (resultSetEquipoPokemon.next()){
                //int idObjeto = resultSetEquipoPokemon.getInt("ID_OBJETO");
                Objeto objetoNulo = new Objeto();


                Pokemon pokemonUsuario = new Pokemon(
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
                        objetoNulo
                );
                pokemonUsuario.asignarMovimientos(pokemonUsuario.getId());
                usuario.setPokemon(pokemonUsuario, indice);
                indice++;
            }
            resultSetEquipoPokemon.close();
            statementEquipoPokemon.close();
            connection.close();

        }catch (SQLException e){

        }
    }


}
