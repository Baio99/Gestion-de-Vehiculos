package parqueaderoapp5.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import parqueaderoapp5.models.Vehiculo;

public class MainController {

    @FXML
    private TableView<Vehiculo> vehiculosTable;
    @FXML
    private TableColumn<Vehiculo, String> placaColumn;
    @FXML
    private TableColumn<Vehiculo, String> marcaColumn;
    @FXML
    private TableColumn<Vehiculo, String> modeloColumn;
    @FXML
    private TableColumn<Vehiculo, String> colorColumn;
    @FXML
    private TextField placaField;
    @FXML
    private TextField marcaField;
    @FXML
    private TextField modeloField;
    @FXML
    private TextField colorField;
    @FXML
    private TextField searchField;
    @FXML
    private Label searchMessage;
    @FXML
    private Label errorMessage;

    private VehiculoController vehiculoController;
    private ObservableList<Vehiculo> vehiculosData;

    @FXML
    private void initialize() {
        vehiculoController = new VehiculoController();
        vehiculosData = FXCollections.observableArrayList(vehiculoController.getAllVehiculos());
        vehiculosTable.setItems(vehiculosData);

        placaColumn.setCellValueFactory(cellData -> cellData.getValue().placaProperty());
        marcaColumn.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
        modeloColumn.setCellValueFactory(cellData -> cellData.getValue().modeloProperty());
        colorColumn.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
    }

    private boolean validateFields() {
        if (placaField.getText().isEmpty() || marcaField.getText().isEmpty() ||
            modeloField.getText().isEmpty() || colorField.getText().isEmpty()) {
            errorMessage.setText("Por favor, llene todos los campos.");
            return false;
        }

        if (!validatePlaca(placaField.getText())) {
            errorMessage.setText("La placa debe tener el formato AAA-1234 y un máximo de 8 caracteres.");
            return false;
        }

        if (!validateColor(colorField.getText())) {
            errorMessage.setText("El campo de color solo debe contener letras.");
            return false;
        }

        errorMessage.setText(""); // Limpiar mensaje de error
        return true;
    }

    private boolean validatePlaca(String placa) {
        return placa.matches("^[A-Z]{3}-\\d{4}$");
    }

    private boolean validateColor(String color) {
        return color.matches("^[a-zA-Z]+$");
    }

    private boolean isPlacaUnique(String placa) {
        return vehiculosData.stream().noneMatch(vehiculo -> vehiculo.getPlaca().equals(placa));
    }

    @FXML
    private void handleAddVehiculo(ActionEvent event) {
        if (validateFields()) {
            if (isPlacaUnique(placaField.getText())) {
                Vehiculo vehiculo = new Vehiculo(placaField.getText(), marcaField.getText(), modeloField.getText(), colorField.getText());
                vehiculoController.addVehiculo(vehiculo);
                vehiculosData.add(vehiculo);
                clearFields();
            } else {
                errorMessage.setText("El vehículo con esta placa ya está registrado.");
            }
        }
    }

    @FXML
    private void handleDeleteVehiculo(ActionEvent event) {
        Vehiculo selectedVehiculo = vehiculosTable.getSelectionModel().getSelectedItem();
        if (selectedVehiculo != null) {
            vehiculoController.deleteVehiculo(selectedVehiculo.getPlaca());
            vehiculosData.remove(selectedVehiculo);
            clearFields(); // Limpiar campos
            vehiculosTable.getSelectionModel().clearSelection(); // Deseleccionar tabla
        }
    }

    @FXML
    private void handleUpdateVehiculo(ActionEvent event) {
        if (validateFields()) {
            Vehiculo selectedVehiculo = vehiculosTable.getSelectionModel().getSelectedItem();
            if (selectedVehiculo != null) {
                String newPlaca = placaField.getText();
                String originalPlaca = selectedVehiculo.getPlaca();

                if (!originalPlaca.equals(newPlaca) && !isPlacaUnique(newPlaca)) {
                    errorMessage.setText("El vehículo con esta placa ya está registrado.");
                    return;
                }

                selectedVehiculo.setPlaca(placaField.getText());
                selectedVehiculo.setMarca(marcaField.getText());
                selectedVehiculo.setModelo(modeloField.getText());
                selectedVehiculo.setColor(colorField.getText());

                if (!originalPlaca.equals(newPlaca)) {
                    vehiculoController.deleteVehiculo(originalPlaca);
                    vehiculoController.addVehiculo(selectedVehiculo);
                } else {
                    vehiculoController.updateVehiculo(selectedVehiculo);
                }
                vehiculosTable.refresh();
                clearFields(); // Limpiar campos
                vehiculosTable.getSelectionModel().clearSelection(); // Deseleccionar tabla
            }
        }
    }

    @FXML
    private void handleTableClick(MouseEvent event) {
        Vehiculo selectedVehiculo = vehiculosTable.getSelectionModel().getSelectedItem();
        if (selectedVehiculo != null) {
            placaField.setText(selectedVehiculo.getPlaca());
            marcaField.setText(selectedVehiculo.getMarca());
            modeloField.setText(selectedVehiculo.getModelo());
            colorField.setText(selectedVehiculo.getColor());
        }
    }

    @FXML
    private void handleSearchVehiculo(ActionEvent event) {
        String placa = searchField.getText();
        Vehiculo vehiculo = vehiculoController.getVehiculoByPlaca(placa);
        if (vehiculo != null) {
            vehiculosData.clear();
            vehiculosData.add(vehiculo);
            searchMessage.setText("");
        } else {
            vehiculosData.clear();
            vehiculosData.addAll(vehiculoController.getAllVehiculos());
            searchMessage.setText("Vehículo no encontrado.");
        }
    }

    @FXML
    private void handleClearSearch(ActionEvent event) {
        searchField.setText("");
        searchMessage.setText("");
        vehiculosData.clear();
        vehiculosData.addAll(vehiculoController.getAllVehiculos());
    }

    private void clearFields() {
        placaField.setText("");
        marcaField.setText("");
        modeloField.setText("");
        colorField.setText("");
    }
}
