package ro.iss2025.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ro.iss2025.domain.User;
import ro.iss2025.services.Service;

public class RegisterController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField cnpField;
    @FXML
    private PasswordField passwordField;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    private void handleRegister() {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        String cnp = cnpField.getText().trim();
        String password = passwordField.getText().trim();

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || cnp.isEmpty() || password.isEmpty()) {
            showError("Toate câmpurile sunt obligatorii!");
            return;
        }

        Integer id = service.registerUser(name, address, phone, cnp, password);
        if (id != null) {
            User user = service.findUserById(id);
            showInfo("Înregistrare cu succes! Username-ul tău este ID-ul: " + id);
            openUserMainWindow(user);
            ((Stage) nameField.getScene().getWindow()).close();
        } else {
            showError("Eroare la înregistrare.");
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

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
