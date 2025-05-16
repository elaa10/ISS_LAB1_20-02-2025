package ro.iss2025.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ro.iss2025.domain.Borrow;
import ro.iss2025.domain.User;
import ro.iss2025.services.Service;
import ro.iss2025.utils.events.Event;
import ro.iss2025.utils.observer.Observer;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UserBorrowsController implements Initializable, Observer<Event> {

    @FXML
    private VBox borrowsPane;

    private Service service;
    private User user;

    public void setService(Service service, User user) {
        this.service = service;
        this.user = user;
        service.addObserver(this);
        loadBorrows();
    }

    private void loadBorrows() {
        borrowsPane.getChildren().clear();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Borrow> borrows = service.getAllBorrows().stream()
                .filter(b -> b.getUser().getId() == user.getId())
                .collect(Collectors.toList());

        for (Borrow b : borrows) {
            var ex = b.getExemplar();
            var book = ex.getBook();

            VBox info = new VBox(5);
            info.getChildren().addAll(
                    new Label("Titlu: " + book.getTitle()),
                    new Label("Autor: " + book.getAuthor()),
                    new Label("Editura: " + book.getPublishingHouse()),
                    new Label("Pagini: " + book.getNrPages()),
                    new Label("Coperta: " + book.getCoverType()),
                    new Label("Stare: " + ex.getCondition()),
                    new Label("Dată împrumut: " + b.getId().getDateOfBorrow().format(fmt)),
                    new Label("Dată returnare: " + b.getId().getDateOfBorrow().plusWeeks(2).format(fmt)),
                    new Label("Status: " + b.getStatus())
            );

            ImageView img = new ImageView();
            try {
                img.setImage(new Image(getClass().getResource(book.getPhoto()).toExternalForm()));
                img.setFitWidth(70);
                img.setFitHeight(100);
            } catch (Exception ignored) {}

            HBox box = new HBox(20, info, img);
            box.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10;");
            box.setPadding(new javafx.geometry.Insets(10));

            borrowsPane.getChildren().add(box);
        }
    }

    @Override
    public void update(Event event) {
        loadBorrows();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
