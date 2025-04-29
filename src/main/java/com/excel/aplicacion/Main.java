package com.excel.aplicacion;

import java.io.File;

import com.excel.aplicacion.Modelo.ExcelReader;
import com.excel.aplicacion.Modelo.TablaEmpleado;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lector Excel");

        Label label = new Label("Seleccionar un archivo Excel:");
        Button botonSeleccionar = new Button("Abrir archivo");

        TablaEmpleado tablaEmpleado = new TablaEmpleado(); // Componente de la tabla

        botonSeleccionar.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Excel", "*.xlsx", "*.xls"));
            File file = fileChooser.showOpenDialog(primaryStage);

            if (file != null) {
                ExcelReader.leerArchivo(file, tablaEmpleado);
            }
        });

        VBox layout = new VBox(10, label, botonSeleccionar, tablaEmpleado.getComponente());
        Scene scene = new Scene(layout, 600, 450);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}