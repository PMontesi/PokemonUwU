package es.cesur.progprojectpok.controllers;

import es.cesur.progprojectpok.SplashApplication;
import es.cesur.progprojectpok.database.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private Button aaa;
    @FXML
    private TextField loginUsuario;
    @FXML
    private TextField loginContra;
    @FXML
    private Button loginRegistro;
    @FXML
    private Button loginEntrar;
    @FXML
    private Button loginSalir;

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
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(SplashApplication.class.getResource("view/mainMenu-view.fxml"));
                Scene scene = null;
                    scene = new Scene(fxmlLoader.load(), 760, 750);
                    stage.setTitle("Menu");
                    stage.setScene(scene);
                    stage.show();

            } else {
                System.out.println("No va");
            }
        } catch (SQLException |IOException e) {
            e.printStackTrace();
        }
    }


}
