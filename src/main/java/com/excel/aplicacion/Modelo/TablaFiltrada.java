package com.excel.aplicacion.Modelo;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TablaFiltrada {
    private ObservableList<Empleado> empleadosFiltrados;
    private TableView<Empleado> tablaFiltrada;
    private String filtroName;

    public TablaFiltrada(ObservableList<Empleado> empleados, String filtro) {
        this.filtroName = filtro.toLowerCase();

        if (empleados.isEmpty()) {
            System.out.println("⚠ No hay empleados cargados en la lista.");
        }

        empleadosFiltrados = FXCollections.observableArrayList();
        for (Empleado empleado : empleados) {
            if (empleado.getNombre().toLowerCase().contains(filtroName)) {
                empleadosFiltrados.add(empleado);
            }
        }

        System.out.println("✅ Empleados encontrados: " + empleadosFiltrados.size());

        tablaFiltrada = new TableView<>(empleadosFiltrados);

        // Definir columnas
        TableColumn<Empleado, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));

        TableColumn<Empleado, String> columnaFecha = new TableColumn<>("Fecha");
        columnaFecha.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFechaFormateada()));

        TableColumn<Empleado, String> columnaIngreso = new TableColumn<>("Ingreso");
        columnaIngreso.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIngreso().toString()));

        TableColumn<Empleado, String> columnaEgreso = new TableColumn<>("Salida");
        columnaEgreso.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSalida().toString()));

        tablaFiltrada.getColumns().addAll(columnaNombre, columnaFecha, columnaIngreso, columnaEgreso);
        tablaFiltrada.setItems(empleadosFiltrados);
    }

    public void mostrar() {
        VBox layout = new VBox(10, new Label("Resultados filtrados:"), tablaFiltrada);
        Scene escena = new Scene(layout, 400, 300);

        Stage ventanaFiltrada = new Stage();
        ventanaFiltrada.setTitle("Empleados Filtrados");
        ventanaFiltrada.setScene(escena);
        ventanaFiltrada.show();
    }
}
