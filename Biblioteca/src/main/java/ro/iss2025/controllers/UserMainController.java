package ro.iss2025.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.iss2025.domain.Book;
import ro.iss2025.domain.Exemplar;
import ro.iss2025.domain.User;
import ro.iss2025.services.Service;
import ro.iss2025.utils.events.Event;
import ro.iss2025.utils.observer.Observer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UserMainController implements Initializable, Observer<Event> {

    @FXML
    private TextField searchTitleField;
    @FXML
    private TextField searchAuthorField;
    @FXML
    private VBox booksPane;
    @FXML
    private ImageView cartIcon;

    private Service service;
    private User loggedInUser;
    private final List<Exemplar> cart = new ArrayList<>();

    public void setService(Service service, User user) {
        this.service = service;
        this.loggedInUser = user;
        service.addObserver(this);
        loadBooks();

        cartIcon.setOnMouseClicked(e -> openCartWindow());
    }

    private void openCartWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/cart.fxml"));
            VBox root = loader.load();

            CartController controller = loader.getController();
            Stage dialogStage = new Stage();
            controller.setService(service, loggedInUser, cart, dialogStage);

            Scene scene = new Scene(root);
            dialogStage.setTitle("Coș de împrumut");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);
            dialogStage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Node createBookItem(Exemplar ex) {
        Book book = ex.getBook();

        VBox infoBox = new VBox(5);
        infoBox.getChildren().addAll(
                new Label("Titlu: " + book.getTitle()),
                new Label("Autor: " + book.getAuthor()),
                new Label("Editura: " + book.getPublishingHouse()),
                new Label("Pagini: " + book.getNrPages()),
                new Label("Coperta: " + book.getCoverType()),
                new Label("Stare: " + ex.getCondition()),
                new Label("Disponibilitate: " + (ex.getBooked() ? "Deja împrumutat" : "În bibliotecă"))
        );

        ImageView imageView = new ImageView();
        try {
            imageView.setImage(new Image(getClass().getResource(book.getPhoto()).toExternalForm()));
            imageView.setFitHeight(100);
            imageView.setFitWidth(70);
        } catch (Exception ignored) {}

        ImageView cartBtn = new ImageView(new Image("/views/cart-icon.png"));
        cartBtn.setFitWidth(32);
        cartBtn.setFitHeight(32);
        cartBtn.setPickOnBounds(true);
        Tooltip.install(cartBtn, new Tooltip("Adaugă în coș"));
        cartBtn.setOnMouseClicked(e -> {
            if (!cart.contains(ex)) {
                cart.add(ex);
                showInfo("Carte adăugată în coș.");
            } else {
                showInfo("Cartea este deja în coș.");
            }
        });

        VBox rightBox = new VBox(10, imageView, cartBtn);
        rightBox.setPadding(new Insets(5));

        HBox itemBox = new HBox(20, infoBox, rightBox);
        itemBox.setPadding(new Insets(10));
        itemBox.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10;");

        return itemBox;
    }


    private void loadBooks() {
        booksPane.getChildren().clear();
        List<Exemplar> exemplars = service.getAllExemplars();
        for (Exemplar ex : exemplars) {
            booksPane.getChildren().add(createBookItem(ex));
        }
    }


    @FXML
    private void handleSearch() {
        String titleFilter = searchTitleField.getText().trim().toLowerCase();
        String authorFilter = searchAuthorField.getText().trim().toLowerCase();

        List<Exemplar> filtered = service.getAllExemplars().stream()
                .filter(ex -> ex.getBook().getTitle().toLowerCase().contains(titleFilter))
                .filter(ex -> ex.getBook().getAuthor().toLowerCase().contains(authorFilter))
                .collect(Collectors.toList());

        booksPane.getChildren().clear();
        for (Exemplar ex : filtered) {
            booksPane.getChildren().add(createBookItem(ex));
        }
    }


    @Override
    public void update(Event event) {
        loadBooks();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // setup optional
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
