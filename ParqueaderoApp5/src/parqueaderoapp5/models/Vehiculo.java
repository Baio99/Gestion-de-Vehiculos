package parqueaderoapp5.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vehiculo {
    private final StringProperty placa;
    private final StringProperty marca;
    private final StringProperty modelo;
    private final StringProperty color;

    public Vehiculo(String placa, String marca, String modelo, String color) {
        this.placa = new SimpleStringProperty(placa);
        this.marca = new SimpleStringProperty(marca);
        this.modelo = new SimpleStringProperty(modelo);
        this.color = new SimpleStringProperty(color);
    }

    public String getPlaca() {
        return placa.get();
    }

    public void setPlaca(String placa) {
        this.placa.set(placa);
    }

    public StringProperty placaProperty() {
        return placa;
    }

    public String getMarca() {
        return marca.get();
    }

    public void setMarca(String marca) {
        this.marca.set(marca);
    }

    public StringProperty marcaProperty() {
        return marca;
    }

    public String getModelo() {
        return modelo.get();
    }

    public void setModelo(String modelo) {
        this.modelo.set(modelo);
    }

    public StringProperty modeloProperty() {
        return modelo;
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public StringProperty colorProperty() {
        return color;
    }
}
