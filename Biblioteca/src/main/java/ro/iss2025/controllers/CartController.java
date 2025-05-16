package ro.iss2025.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import ro.iss2025.domain.*;
import ro.iss2025.services.Service;
import ro.iss2025.utils.events.Event;
import ro.iss2025.utils.observer.Observer;

import java.time.ZonedDateTime;
import java.util.List;

public class CartController implements Observer<Event> {

    @FXML
    private VBox cartListBox;

    @FXML
    private Button borrowButton;

    private Service service;
    private User user;
    private List<Exemplar> cart;
    private Stage dialogStage;

    public void setService(Service service, User user, List<Exemplar> cart, Stage dialogStage) {
        this.service = service;
        this.user = user;
        this.cart = cart;
        this.dialogStage = dialogStage;
        service.addObserver(this);
        initView();
    }

    private void initView() {
        cartListBox.getChildren().clear();

        for (Exemplar ex : cart) {
            Book book = ex.getBook();

            VBox textBox = new VBox(
                    new Label("Titlu: " + book.getTitle()),
                    new Label("Autor: " + book.getAuthor()),
                    new Label("Editura: " + book.getPublishingHouse()),
                    new Label("Pagini: " + book.getNrPages()),
                    new Label("Coperta: " + book.getCoverType()),
                    new Label("Stare: " + ex.getCondition())
            );
            textBox.setSpacing(5);

            ImageView img = new ImageView();
            try {
                img.setImage(new Image(getClass().getResource(book.getPhoto()).toExternalForm()));
                img.setFitHeight(100);
                img.setFitWidth(70);
            } catch (Exception ignored) {}

            ImageView deleteIcon = new ImageView(new Image("/views/delete-icon.png"));
            deleteIcon.setPickOnBounds(true);
            Tooltip.install(deleteIcon, new Tooltip("Sterge din cos"));

            deleteIcon.setFitHeight(20);
            deleteIcon.setFitWidth(20);
            deleteIcon.setOnMouseClicked(e -> {
                cart.remove(ex);
                initView();
                showInfo("Carte eliminată din coș.");
            });

            StackPane imageWithDelete = new StackPane();
            imageWithDelete.getChildren().addAll(img, deleteIcon);
            StackPane.setAlignment(deleteIcon, Pos.TOP_RIGHT);

            HBox itemBox = new HBox(10, textBox, imageWithDelete);
            itemBox.setPadding(new Insets(5));
            itemBox.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-background-radius: 5;");
            cartListBox.getChildren().add(itemBox);
        }

        borrowButton.setOnAction(e -> {
            for (Exemplar ex : cart) {
                ZonedDateTime now = ZonedDateTime.now();
                BorrowId id = new BorrowId(user.getId(), ex.getId(), now);
                Borrow borrow = new Borrow(id, user, ex, StatusType.BORROWED);
                service.addBorrow(borrow);
                ex.setBooked(true);
                service.updateExemplar(ex);
            }
            cart.clear();
            dialogStage.close();
        });
    }

    @Override
    public void update(Event event) {
        initView();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
