// === Main.java ===
package ro.iss2025;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.iss2025.controllers.LoginController;
import ro.iss2025.repository.hibernate.*;
import ro.iss2025.services.Service;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        var bookRepo = new BookRepositoryHibernate();
        var userRepo = new UserRepositoryHibernate();
        var exemplarRepo = new ExemplarRepositoryHibernate();
        var borrowRepo = new BorrowRepositoryHibernate();

        Service service = new Service(bookRepo, userRepo, exemplarRepo, borrowRepo);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(loader.load());

        LoginController controller = loader.getController();
        controller.setService(service);

        stage.setTitle("Bine ati venit la Biblioteca");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
