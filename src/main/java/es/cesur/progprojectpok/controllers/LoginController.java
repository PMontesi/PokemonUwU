package es.cesur.progprojectpok.controllers;

import es.cesur.progprojectpok.database.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


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
                // Usuario válido, puedes realizar acciones adicionales aquí
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/mainMenu-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 480);
                Stage menuStage = new Stage();
                menuStage.setTitle("Menu");
                menuStage.setScene(scene);
                menuStage.show();
            } else {
                System.out.println("No va");
            }
        } catch (SQLException |IOException e) {
            e.printStackTrace();
        }
    }


}
