package com.example.aplicacionmunicipiodeolavarria;


import java.util.Objects;

public class Terminales {
    private String entidad;
    private String nombre; //ubicacion en el xml
    private String calle;
    private String alturaCalle;
    private String observacion;
    private String localidad;
    private String  latitud;
    private String longitud;

    public Terminales(){}

    public Terminales(String entidad, String nombre, String calle, String alturaCalle,
                      String observacion, String localidad, String latitud, String longitud) {
        this.entidad = entidad;
        this.nombre = nombre;
        this.calle = calle;
        this.alturaCalle = alturaCalle;
        this.observacion = observacion;
        this.localidad = localidad;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getEntidad() {
        return this.entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return this.calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getAlturaCalle() {
        return this.alturaCalle;
    }

    public void setAlturaCalle(String alturaCalle) {
        this.alturaCalle = alturaCalle;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getLocalidad() {
        return this.localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getLatitud() {
        return this.latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return this.longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Terminales{" +
                "entidad='" + this.entidad + '\'' +
                ", nombre='" + this.nombre + '\'' +
                ", calle='" + this.calle + '\'' +
                ", alturaCalle='" + this.alturaCalle + '\'' +
                ", observacion='" + this.observacion + '\'' +
                ", localidad='" + this.localidad + '\'' +
                ", latitud=" + this.latitud +
                ", longitud=" + this.longitud +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terminales that = (Terminales) o;
        return Objects.equals(latitud, that.latitud) &&
                Objects.equals(longitud, that.longitud);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitud, longitud);
    }
}
