package com.excel.aplicacion.Modelo;

import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TablaEmpleado {
     private TableView<Empleado> tabla;
    private TextField filtroNombre;
    private Button btnFiltrar;
    private ObservableList<Empleado> empleados;

    public TablaEmpleado() {
        tabla = new TableView<>();
        empleados = FXCollections.observableArrayList();  

        filtroNombre = new TextField();
        filtroNombre.setPromptText("Escriba un nombre...");
        btnFiltrar = new Button("Filtrar");

        TableColumn<Empleado, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));

        TableColumn<Empleado, String> columnaFecha = new TableColumn<>("Fecha");
        columnaFecha.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFechaFormateada()));

        TableColumn<Empleado, String> columnaIngreso = new TableColumn<>("Ingreso");
        columnaIngreso.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIngreso().toString()));

        TableColumn<Empleado, String> columnaEgreso = new TableColumn<>("Salida");
        columnaEgreso.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSalida().toString()));

        tabla.getColumns().addAll(columnaNombre, columnaFecha, columnaIngreso, columnaEgreso);
        tabla.setItems(empleados);

        btnFiltrar.setOnAction(e -> abrirTablaFiltrada());

    }

    public VBox getComponente() {
        return new VBox(10, filtroNombre, btnFiltrar, tabla);
    }

    public void actualizarDatos(List<Empleado> nuevosDatos) {
        empleados.setAll(nuevosDatos);
    }

    public TableView<Empleado> getTabla() {
        return tabla;
    }

    private void abrirTablaFiltrada() {
        String filtro = filtroNombre.getText().trim().toLowerCase();
        System.out.println("Aplicando filtro: " + filtro);

        if (filtro.isEmpty()) {
            return;
        }

        TablaFiltrada tablaFiltrada = new TablaFiltrada(empleados, filtro);
        tablaFiltrada.mostrar();
    }
}
