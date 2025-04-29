package com.excel.aplicacion.Modelo;

import java.time.LocalTime;

public class Empleado {
    private String nombre;
    private String fechaFormateada;
    private LocalTime ingreso;
    private LocalTime salida;

    public Empleado(String nombre, String fechaFormateada, LocalTime ingreso, LocalTime salida) {
        this.nombre = nombre;
        this.fechaFormateada = fechaFormateada;
        this.ingreso = ingreso;
        this.salida = salida;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaFormateada() {
        return fechaFormateada;
    }

    public LocalTime getIngreso() {
        return ingreso;
    }

    public LocalTime getSalida() {
        return salida;
    }
}
