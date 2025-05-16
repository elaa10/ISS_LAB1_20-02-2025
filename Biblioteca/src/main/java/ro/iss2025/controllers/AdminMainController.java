package ro.iss2025.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.iss2025.domain.User;
import ro.iss2025.services.Service;
import ro.iss2025.utils.events.Event;
import ro.iss2025.utils.observer.Observer;

public class AdminMainController implements Observer<Event> {

    @FXML
    private Button returnBooksButton;

    @FXML
    private Button systemManagerButton;

    private Service service;
    private User adminUser;

    public void setService(Service service, User adminUser) {
        this.service = service;
        this.adminUser = adminUser;
        service.addObserver(this);
    }

    @FXML
    private void handleReturnBooks() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/return-books.fxml"));

            BorderPane root = loader.load();

            ReturnBookController controller = loader.getController();
            controller.setService(service, adminUser);

            Stage stage = new Stage();
            stage.setTitle("Returnează cărți");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleSystemManager() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/system-manager.fxml"));
            Scene scene = new Scene(loader.load());

            SystemManagerController controller = loader.getController();
            controller.setService(service, adminUser);

            Stage stage = new Stage();
            stage.setTitle("System Manager");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Event event) {
    }
}
