package ro.iss2025.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import ro.iss2025.domain.*;
import ro.iss2025.services.Service;
import ro.iss2025.utils.events.Event;
import ro.iss2025.utils.observer.Observer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SystemManagerController implements Initializable, Observer<Event> {

    @FXML
    private TableView<Exemplar> exemplarTable;
    @FXML
    private TableColumn<Exemplar, Integer> exemplarIdColumn;
    @FXML
    private TableColumn<Exemplar, String> titleColumn;
    @FXML
    private TableColumn<Exemplar, String> authorColumn;
    @FXML
    private TableColumn<Exemplar, String> publishingHouseColumn;
    @FXML
    private TableColumn<Exemplar, Integer> pagesColumn;
    @FXML
    private TableColumn<Exemplar, CoverType> coverTypeColumn;
    @FXML
    private TableColumn<Exemplar, String> conditionColumn;
    @FXML
    private TableColumn<Exemplar, Boolean> bookedColumn;

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Long> userIdColumn;
    @FXML
    private TableColumn<User, String> userNameColumn;
    @FXML
    private TableColumn<User, String> userPhoneColumn;

    @FXML
    private TextField conditionField;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteExemplarButton;
    @FXML
    private Button addExemplarButton;

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField publishingHouseField;
    @FXML
    private TextField pagesField;
    @FXML
    private TextField coverTypeField;
    @FXML
    private Button addBookButton;

    @FXML
    private Button deleteUserButton;

    private Service service;
    private User admin;

    private final ObservableList<Exemplar> exemplars = FXCollections.observableArrayList();
    private final ObservableList<User> users = FXCollections.observableArrayList();

    public void setService(Service service, User adminUser) {
        this.service = service;
        this.admin = adminUser;
        service.addObserver(this);
        loadData();
    }

    private void loadData() {
        exemplars.setAll(service.getAllExemplars());
        users.setAll(service.getAllUsers());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exemplarIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getBook().getTitle()));
        authorColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getBook().getAuthor()));
        publishingHouseColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getBook().getPublishingHouse()));
        pagesColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getBook().getNrPages()));
        coverTypeColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getBook().getCoverType()));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        bookedColumn.setCellValueFactory(new PropertyValueFactory<>("booked"));

        exemplarTable.setItems(exemplars);

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        userPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        userTable.setItems(users);

        updateButton.setOnAction(e -> {
            Exemplar selected = exemplarTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.setCondition(conditionField.getText());
                selected.getBook().setTitle(titleField.getText());
                selected.getBook().setAuthor(authorField.getText());
                selected.getBook().setPublishingHouse(publishingHouseField.getText());
                selected.getBook().setNrPages(Integer.parseInt(pagesField.getText()));
                selected.getBook().setCoverType(CoverType.valueOf(coverTypeField.getText().toUpperCase()));
                service.updateBook(selected.getBook());
                service.updateExemplar(selected);
            }
        });

        deleteExemplarButton.setOnAction(e -> {
            Exemplar selected = exemplarTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                service.deleteExemplar(selected.getId());
            }
        });

        addExemplarButton.setOnAction(e -> {
            Exemplar selected = exemplarTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Book book = selected.getBook();
                service.addExemplar(new Exemplar(book, conditionField.getText(), false));
            }
        });

        addBookButton.setOnAction(e -> {
            Book book = new Book(
                    titleField.getText(),
                    "/views/book-placeholder.png",
                    CoverType.valueOf(coverTypeField.getText().toUpperCase()),
                    Integer.parseInt(pagesField.getText()),
                    publishingHouseField.getText(),
                    authorField.getText()
            );
            service.addBook(book);
        });

        deleteUserButton.setOnAction(e -> {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                boolean hasActiveBorrows = service.getAllBorrows().stream()
                        .anyMatch(b -> b.getUser().getId().equals(selectedUser.getId()) &&
                                (b.getStatus() == StatusType.BORROWED || b.getStatus() == StatusType.OVERDUE));
                if (!hasActiveBorrows) {
                    service.deleteUser(selectedUser.getId());
                } else {
                    showError("Userul are împrumuturi active (BOOKED/OVERDUE). Nu poate fi șters.");
                }
            }
        });
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void update(Event event) {
        loadData();
    }
}
