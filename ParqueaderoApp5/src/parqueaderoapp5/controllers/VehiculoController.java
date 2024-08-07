/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parqueaderoapp5.controllers;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import parqueaderoapp5.models.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class VehiculoController {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public VehiculoController() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        database = client.getDatabase("parqueaderoDB");
        collection = database.getCollection("vehiculos");
    }

    public void addVehiculo(Vehiculo vehiculo) {
        Document doc = new Document("placa", vehiculo.getPlaca())
                .append("marca", vehiculo.getMarca())
                .append("modelo", vehiculo.getModelo())
                .append("color", vehiculo.getColor());
        collection.insertOne(doc);
    }

    public void updateVehiculo(Vehiculo vehiculo) {
    Document query = new Document("placa", vehiculo.getPlaca());
    Document update = new Document("$set", new Document("marca", vehiculo.getMarca())
            .append("modelo", vehiculo.getModelo())
            .append("color", vehiculo.getColor()));
    collection.updateOne(query, update);
}

    public void deleteVehiculo(String placa) {
        Document query = new Document("placa", placa);
        collection.deleteOne(query);
    }

    public Vehiculo getVehiculoByPlaca(String placa) {
        Document query = new Document("placa", placa);
        Document result = collection.find(query).first();
        if (result != null) {
            return new Vehiculo(result.getString("placa"),
                    result.getString("marca"),
                    result.getString("modelo"),
                    result.getString("color"));
        }
        return null;
    }

    public List<Vehiculo> getAllVehiculos() {
    List<Vehiculo> vehiculos = new ArrayList<>();
    for (Document doc : collection.find()) {
        vehiculos.add(new Vehiculo(doc.getString("placa"),
                doc.getString("marca"),
                doc.getString("modelo"),
                doc.getString("color")));
    }
    return vehiculos;
}
}