package ro.iss2025.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ro.iss2025.domain.Borrow;
import ro.iss2025.domain.Exemplar;
import ro.iss2025.domain.StatusType;
import ro.iss2025.domain.User;
import ro.iss2025.services.Service;
import ro.iss2025.utils.events.Event;
import ro.iss2025.utils.observer.Observer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReturnBookController implements Initializable, Observer<Event> {

    @FXML
    private TableView<Borrow> borrowTable;

    @FXML
    private TableColumn<Borrow, Integer> colUserId;

    @FXML
    private TableColumn<Borrow, String> colUserName;

    @FXML
    private TableColumn<Borrow, Integer> colExemplarId;

    @FXML
    private TableColumn<Borrow, String> colTitle;

    @FXML
    private TableColumn<Borrow, String> colBorrowDate;

    @FXML
    private TableColumn<Borrow, String> colReturnDate;

    @FXML
    private TableColumn<Borrow, String> colStatus;

    @FXML
    private Button returnButton;

    private Service service;
    private User admin;
    private final ObservableList<Borrow> model = FXCollections.observableArrayList();

    public void setService(Service service, User admin) {
        this.service = service;
        this.admin = admin;
        service.addObserver(this);
        loadBorrows();
    }

    private void loadBorrows() {
        List<Borrow> borrows = service.getAllBorrows().stream()
                .filter(b -> b.getStatus() == StatusType.BORROWED)
                .collect(Collectors.toList());
        model.setAll(borrows);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getUser().getId()));
        colUserName.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getUser().getName()));
        colExemplarId.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getExemplar().getId()));
        colTitle.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getExemplar().getBook().getTitle()));
        colBorrowDate.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId().getDateOfBorrow().toString()));
        colReturnDate.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId().getDateOfBorrow().plusWeeks(2).toString()));
        colStatus.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getStatus().toString()));

        borrowTable.setItems(model);

        returnButton.setOnAction(e -> handleReturn());
    }

    private void handleReturn() {
        Borrow selected = borrowTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setStatus(StatusType.RETURNED);
            service.updateBorrow(selected);

            Exemplar ex = selected.getExemplar();
            ex.setBooked(false);
            service.updateExemplar(ex);

            showInfo("Carte returnată cu succes.");
        } else {
            showInfo("Selectează o carte pentru returnare.");
        }
    }

    @Override
    public void update(Event event) {
        loadBorrows();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
