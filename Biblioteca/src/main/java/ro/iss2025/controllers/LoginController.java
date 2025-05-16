// Updated LoginController
package ro.iss2025.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.iss2025.domain.User;
import ro.iss2025.services.Service;

public class LoginController {

    @FXML
    private TextField idField;

    @FXML
    private PasswordField passwordField;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    private void handleLogin() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String password = passwordField.getText().trim();

            User user = service.findUserById(id);
            if (user != null && user.getPassword().equals(password)) {
                if (user.getAdmin()) {
                    openAdminMainWindow(user);
                } else {
                    openUserMainWindow(user);
                }
            } else {
                showError("ID sau parolă incorectă.");
            }
        } catch (NumberFormatException e) {
            showError("ID-ul trebuie să fie un număr.");
        }
    }

    @FXML
    private void handleOpenRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/register.fxml"));
            Scene scene = new Scene(loader.load());

            RegisterController controller = loader.getController();
            controller.setService(service);

            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Eroare la deschiderea ferestrei de înregistrare.");
        }
    }

    private void openUserMainWindow(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/user-main.fxml"));
            Scene scene = new Scene(loader.load());

            UserMainController controller = loader.getController();
            controller.setService(service, user);

            Stage stage = new Stage();
            stage.setTitle("Bine ai venit, " + user.getName());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Eroare la deschiderea ferestrei principale.");
        }
    }

    private void openAdminMainWindow(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin-main.fxml"));
            Scene scene = new Scene(loader.load());

            AdminMainController controller = loader.getController();
            controller.setService(service, user);

            Stage stage = new Stage();
            stage.setTitle("Panou Administrator");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Eroare la deschiderea panoului administratorului.");
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
