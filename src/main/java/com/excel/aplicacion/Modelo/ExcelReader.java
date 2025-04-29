package com.excel.aplicacion.Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExcelReader {
    public static void leerArchivo(File file, TablaEmpleado tablaEmpleado) {
        ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();

        try (FileInputStream fis = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) {
                System.out.println("El archivo está vacío o no tiene encabezados.");
                return;
            }

            Row headerRow = rowIterator.next();
            Map<String, Integer> columnIndexes = new HashMap<>();

            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                String columnName = headerRow.getCell(i).getStringCellValue().trim().toLowerCase();
                columnIndexes.put(columnName, i);
            }

            if (!columnIndexes.containsKey("nombre") ||
                !columnIndexes.containsKey("fecha/hora")) {
                System.out.println("El archivo no contiene las columnas necesarias.");
                return;
            }

            Map<String, Map<LocalDate, List<LocalTime>>> agrupado = new HashMap<>();
            
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String nombre = row.getCell(columnIndexes.get("nombre")).getStringCellValue();
                Date fechaHoraRaw = row.getCell(columnIndexes.get("fecha/hora")).getDateCellValue();

                LocalDateTime fechaHora = fechaHoraRaw.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDate fecha = fechaHora.toLocalDate();
                LocalTime hora = fechaHora.toLocalTime();
                
                agrupado
                    .computeIfAbsent(nombre, k -> new HashMap<>())
                    .computeIfAbsent(fecha, k -> new ArrayList<>())
                    .add(hora);
            }

            DateTimeFormatter diaFormatter = DateTimeFormatter.ofPattern("EEEE d", Locale.forLanguageTag("es-ES"));

            for (var entryUsuario : agrupado.entrySet()) {
                String nombre = entryUsuario.getKey();
                for (var entryFecha : entryUsuario.getValue().entrySet()) {
                    LocalDate fecha = entryFecha.getKey();
                    List<LocalTime> horas = entryFecha.getValue();
                    horas.sort(Comparator.naturalOrder());

                    if (!horas.isEmpty()) {
                        String fechaFormateada = fecha.format(diaFormatter);
                        listaEmpleados.add(new Empleado(nombre, fechaFormateada, horas.get(0), horas.get(horas.size() - 1)));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error procesando el archivo: " + e.getMessage());
        }

        tablaEmpleado.actualizarDatos(listaEmpleados);
    }
}
